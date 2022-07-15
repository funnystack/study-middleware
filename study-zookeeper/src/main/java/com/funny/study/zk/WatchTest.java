package com.funny.study.zk;

public class WatchTest {
    public static void main(String[] args) throws Exception {
        final String CONNECT_ADDR = "127.0.0.1:2181";
        final int SESSION_OUTTIME = 5000;// ms
        final String PARENT_PATH = "/testWatch";
        CuratorBase curatorBase = new CuratorBase();
        // 1.连接服务端
        curatorBase.conn(CONNECT_ADDR, SESSION_OUTTIME);

        curatorBase.addNodeDataWatcher(PARENT_PATH);


        curatorBase.addChildWatcher(PARENT_PATH);
        Thread.sleep(4000000);

    }

}
