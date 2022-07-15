package com.funny.study.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.framework.recipes.cache.PathChildrenCache.StartMode;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs.Ids;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CuratorBase {

    private CuratorFramework client = null;

    public CuratorBase() {

    }

    /**
     * 连接服务端
     *
     * @param addr
     * @param sessionOuttime
     */
    public void conn(String addr, int sessionOuttime) {
        // 1 重试策略：初试时间为1s 重试10次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        // 2 通过工厂创建连接
        client = CuratorFrameworkFactory.builder().connectString(addr)
                .sessionTimeoutMs(sessionOuttime).retryPolicy(retryPolicy)
                .build();
        // 3 开启连接
        client.start();
    }

    /**
     * 关闭客户端
     */
    public void closeClient() {
        if (client != null)
            this.client.close();
    }

    /**
     * 节点的创建，支持递归创建节点
     *
     * @param path 节点的路径
     * @param data 节点的数据
     */
    public void createNode(String path, byte[] data) {
        try {
            client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .withACL(Ids.OPEN_ACL_UNSAFE).forPath(path, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 节点的创建，支持递归创建节点
     *
     * @param path 节点的路径
     * @param data 节点的数据
     */
    public void createTempNode(String path, byte[] data) {
        try {
            client.create().creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .withACL(Ids.OPEN_ACL_UNSAFE).forPath(path, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除节点
     *
     * @param path
     * @param version
     */
    public void deleteNode(String path, int version) {
        try {
            client.delete().guaranteed().deletingChildrenIfNeeded()
                    .withVersion(version).forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除节点
     *
     * @param path
     */
    public void deleteNode(String path) {
        try {
            client.delete().guaranteed().deletingChildrenIfNeeded().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 带有回调函数的删除节点
     *
     * @param path
     * @param version
     */
    public void deleteNodeInBack(String path, int version) {

        try {
            client.delete().guaranteed().deletingChildrenIfNeeded()
                    .withVersion(version).inBackground(new DeleteCallBack())
                    .forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 进行数据信息的读取
     *
     * @param path 节点的路径
     * @return 数据的字节数组格式
     */
    public String readNode(String path) {
        byte[] data = new byte[10];
        try {
            data = client.getData().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new String(data, StandardCharsets.UTF_8);
    }

    /**
     * 节点信息的更新
     *
     * @param path
     * @param data
     */
    public void updateNode(String path, byte[] data) {
        try {
            client.setData()
//					.withVersion(version)
                    .forPath(path, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 子节点的读取
     *
     * @param path
     * @return
     */
    public List<String> getChildren(String path) {
        List<String> childrens = new ArrayList<String>();
        try {
            childrens = client.getChildren().forPath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return childrens;
    }

    /**
     * 增加节点的Watcher，用于监听节点信息的变化
     *
     * @param path
     */
    public void addNodeDataWatcher(String path) {
        try {
            final NodeCache nodeCache = new NodeCache(client, path);
            nodeCache.start(true);
            nodeCache.getListenable().addListener(new NodeCacheListener() {
                public void nodeChanged() throws Exception {
                    String data = new String(nodeCache.getCurrentData()
                            .getData());
                    System.out.println("path="
                            + nodeCache.getCurrentData().getPath() + ":data="
                            + data);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 增加孩子节点的监听，用于监听孩子节点的信息变化
     *
     * @param path
     * @throws Exception
     */
    public void addChildWatcher(String path) throws Exception {
        final PathChildrenCache cache = new PathChildrenCache(this.client,
                path, true);
        // 在初始化的时候就进行缓存监听
        cache.start(StartMode.POST_INITIALIZED_EVENT);
        System.out.println("=========PathChildrenCache========");
        System.out.println("刚开始的缓存数据" + cache.getCurrentData().size());
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            public void childEvent(CuratorFramework client,
                                   PathChildrenCacheEvent event) throws Exception {

                switch (event.getType()) {
                    case INITIALIZED:
                        System.out.println("客户端子节点cache初始化数据完成");
                        System.out.println("size=" + cache.getCurrentData().size());
                        break;
                    case CHILD_ADDED:
                        System.out.println("添加子节点的路径:" + event.getData().getPath());
                        System.out.println("添加子节点的数据:"
                                + new String(event.getData().getData()));
                        break;
                    case CHILD_UPDATED:
                        System.out.println("修改子节点路径:" + event.getData().getPath());
                        System.out.println("修改子节点数据:"
                                + new String(event.getData().getData()));
                        break;
                    case CHILD_REMOVED:
                        System.out.println("删除子节点的路径:" + event.getData().getPath());
                        System.out.println("删除子节点的数据:"
                                + new String(event.getData().getData()));
                        break;
                    default:
                        break;
                }
                System.out.println("=========PathChildrenCache========");
            }
        });
    }

    public static void main(String[] args) throws Exception {
        final String CONNECT_ADDR = "127.0.0.1:2181";
        final int SESSION_OUTTIME = 5000;// ms
        final String PARENT_PATH = "/testWatch";
        final String CHILDREN_PATH_1 = "/testWatch/children1";
        final String CHILDREN_PATH_2 = "/testWatch/children2";
        CuratorBase curatorBase = null;
        try {
            curatorBase = new CuratorBase();
            // 1.连接服务端
            curatorBase.conn(CONNECT_ADDR, SESSION_OUTTIME);
            // 1.1 PathChildrenCache
            curatorBase.createNode(PARENT_PATH, "parent".getBytes());
            curatorBase.addChildWatcher(PARENT_PATH);

            // 2.节点的创建(支持递归的创建)
            // 2.1 NodeCache
            curatorBase.addNodeDataWatcher(CHILDREN_PATH_1);
            curatorBase.createNode(CHILDREN_PATH_1, "children1".getBytes());
            curatorBase.createNode(CHILDREN_PATH_2, "children2".getBytes());
            Thread.sleep(2000);
            // 3.获取节点的数据
            // Stat stat = new Stat();
            // byte[] readNode = curatorBase.readNode(CHILDREN_PATH_1, stat);
            // System.out.println(new String(readNode));
            // System.out.println(stat);
            // 4.获取孩子节点信息
            List<String> childrens = curatorBase.getChildren(PARENT_PATH);
            System.out.println("子节点的列表为" + childrens);
            // 5.更新节点信息
            curatorBase.updateNode(CHILDREN_PATH_1, "haha".getBytes());
            // 6.删除节点
            curatorBase.deleteNode(CHILDREN_PATH_2, -1);

            Thread.sleep(5000);
            // 删除节点信息
            curatorBase.deleteNode(PARENT_PATH, -1);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            curatorBase.closeClient();
        }

    }
}
