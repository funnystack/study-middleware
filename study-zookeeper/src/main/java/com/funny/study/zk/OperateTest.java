package com.funny.study.zk;

public class OperateTest {
    public static void main(String[] args) throws InterruptedException {
        final String CONNECT_ADDR = "127.0.0.1:2181";
        final int SESSION_OUTTIME = 5000;// ms
        final String PARENT_PATH = "/testWatch";
        final String CHILDREN_PATH_1 = "/testWatch/children1";
        final String CHILDREN_PATH_2 = "/testWatch/children2";


        CuratorBase curatorBase = new CuratorBase();
        curatorBase.conn(CONNECT_ADDR, SESSION_OUTTIME);
//        curatorBase.createNode("/test/3","2".getBytes());
//        curatorBase.updateNode("/test/2","2".getBytes());
//        curatorBase.updateNode("/test/1","1".getBytes());
        curatorBase.deleteNode("/test/3");
//        curatorBase.deleteNode("/testadd");
//        String before = curatorBase.readNode(PARENT_PATH);
//        System.out.println(PARENT_PATH + ",data=" + before);
////
//        for(int i=0;i<3_000_000;i++){
//            curatorBase.createNode("/testadd/"+i,null);
//            System.out.println("created:" +i);
//        }
//
//        String after = curatorBase.readNode(PARENT_PATH);
//        System.out.println(PARENT_PATH + ",data=" + after);

//
//        curatorBase.createTempNode(CHILDREN_PATH_1,CHILDREN_PATH_1.getBytes());
//        curatorBase.createTempNode(CHILDREN_PATH_2,CHILDREN_PATH_2.getBytes());

//        Thread.sleep(2000);

    }

}
