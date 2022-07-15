package com.funny.study.zk;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class WatchNotifyTest {
    final static String CONNECT_ADDR = "127.0.0.1:2181";
    final static int SESSION_OUTTIME = 5000;// ms
    final static String PARENT_PATH = "/testWatch";

    public static void main(String[] args) throws Exception {
        Object object = new Object();
        // 1 重试策略：初试时间为1s 重试10次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 10);
        // 2 通过工厂创建连接
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(CONNECT_ADDR)
                .sessionTimeoutMs(SESSION_OUTTIME).retryPolicy(retryPolicy)
                .build();
        // 3 开启连接
        client.start();
        addNodeDataWatcher(PARENT_PATH, client, object);
        Executors.newFixedThreadPool(2).submit(()->{
            while (true){
                synchronized (object) {
                    System.out.println(Thread.currentThread().getName() + " object wait");
                    object.wait();
                    System.out.println(Thread.currentThread().getName() + " object work start ");
                    Thread.sleep(2000);
                    System.out.println(Thread.currentThread().getName() + " object work end");
                }
            }
        });
        Thread.sleep(450000);

    }

    /**
     * 增加节点的Watcher，用于监听节点信息的变化
     *
     * @param path
     */
    public static void addNodeDataWatcher(String path, CuratorFramework client, Object lock) {
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
                    synchronized (lock) {
                        lock.notifyAll();
                        System.out.println(Thread.currentThread().getName() + " object notifyAll");
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
