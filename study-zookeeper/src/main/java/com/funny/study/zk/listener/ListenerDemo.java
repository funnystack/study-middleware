package com.funny.study.zk.listener;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.curator.utils.ZKPaths;

import java.util.concurrent.Executors;

public class ListenerDemo {

    public static void main(String[] args) throws Exception {
        final String CONNECT_ADDR = "127.0.0.1:2181";
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(CONNECT_ADDR, 60000, 15000, retryPolicy);
        client.start();
        addTreeCacheListener(client, "/test");
        Thread.sleep(10000000);
    }




    private static void addTreeCacheListener(CuratorFramework curatorFramework, String zkDataPath) throws Exception {
        final TreeCache todeCache = new TreeCache(curatorFramework, zkDataPath);
        todeCache.getListenable().addListener(
                new TreeCacheListener() {
                    @Override
                    public void childEvent(CuratorFramework curatorFramework, TreeCacheEvent treeCacheEvent) throws Exception {
                        if (treeCacheEvent.getData() != null) {
                            String path = ZKPaths.getNodeFromPath(treeCacheEvent.getData().getPath());
                            byte[] jsonString = treeCacheEvent.getData().getData();
                            switch (treeCacheEvent.getType()) {
                                case NODE_ADDED:
                                    // 子节点创建
                                    System.out.println("新增节点事件" + path + jsonString);
                                    // 新增节点，可以是父节点或者子节点
                                    // 父节点创建不需要处理，只有子节点才有数据
                                    String parentPath = treeCacheEvent.getData().getPath();
                                    if (!parentPath.equals(zkDataPath)) {

                                    }
                                    break;
                                case NODE_REMOVED:
                                    // 删除节点，删除后需要同时删掉内存中的流程信息
                                    System.out.println("删除节点事件" + path + jsonString);
                                    break;
                                case NODE_UPDATED:
                                    // 删除节点，删除后需要同时删掉内存中的流程信息
                                    System.out.println("修改节点事件" + path + jsonString);
                                    break;
                            }
                        }
                    }
                },
                Executors.newSingleThreadExecutor()
        );
        todeCache.start();
    }

}
