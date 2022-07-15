package com.funny.study.zk.watcher;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.TreeCache;
import org.apache.curator.framework.recipes.cache.TreeCacheEvent;
import org.apache.curator.framework.recipes.cache.TreeCacheListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

/**
 *
 */
public class ZookeeperWatcher {

    private static final Logger logger = LoggerFactory.getLogger(ZookeeperWatcher.class);
    /**
     * 根路径
     */
    public static final String zkDataPath = "/test";

    private CuratorFramework curatorFramework;

    /**
     * zk 数据监听
     *
     * @date 2021/6/25 10:25
     */
    public void watch(String watchPath) throws Exception {
        String cachePath = zkDataPath + watchPath;
        // 1、watch路径是否存在，不存在生成一个
        Stat statFather = curatorFramework.checkExists().forPath(cachePath);
        if (statFather == null) {
            // 创建持久节点
            curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath(cachePath);
        }

        final TreeCache todeCache = new TreeCache(curatorFramework, cachePath);
        try {
            TreeCacheListener treeCacheListener = new TreeCacheListener() {
                @Override
                public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                    ChildData data = event.getData();
                    if (data == null) {
                        logger.info("[TreeCache]节点数据为空");
                        return;
                    }
                    String eventPath = Objects.isNull(data.getPath()) ? "" : data.getPath();
                    String jsonString = Objects.isNull(data.getData()) ? "" : new String(data.getData());
                    switch (event.getType()) {
                        case NODE_ADDED:
                            logger.info("[TreeCache]节点增加, path={}, data={}", eventPath, jsonString);
                            createEventHandler(watchPath, eventPath, jsonString);
                            break;
                        case NODE_UPDATED:
                            logger.info("[TreeCache]节点更新, path={}, data={}", eventPath, jsonString);
                            updateEventHandler(watchPath, eventPath, jsonString);
                            break;
                        case NODE_REMOVED:
                            logger.info("[TreeCache]节点删除, path={}, data={}", eventPath, jsonString);
                            deleteEventHandler(watchPath, eventPath, jsonString);
                            break;
                        default:
                            break;
                    }
                }
            };
            todeCache.getListenable().addListener(treeCacheListener);
            todeCache.start();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("创建TreeCache监听失败, path={}", zkDataPath);
        }
    }


    private void createEventHandler(String watchPath, String eventPath, String jsonString) {

    }

    private void updateEventHandler(String watchPath, String eventPath, String jsonString) {

    }

    private void deleteEventHandler(String watchPath, String eventPath, String jsonString) {

    }
}
