package com.funny.study.test;

import com.funny.study.kafka.KafkaSender;
import org.junit.Test;


public class KafkaTest {


    public static final String topic = "disruptor_test";


    @Test
    public void writeKafkaSync() {
        long start = System.currentTimeMillis();
        for (int j = 0; j < 1_000; j++) {
            KafkaSender.sendSync(topic, j + "");
        }
        System.out.println("writeKafkaSync used:" + (System.currentTimeMillis() - start));
    }

    @Test
    public void writeKafkaAsync() throws InterruptedException {
        long start = System.currentTimeMillis();
        for (int j = 0; j < 1_000; j++) {
            KafkaSender.sendAsync(topic, j + "");
        }
        System.out.println("writeKafkaAsync used:" + (System.currentTimeMillis() - start));
        Thread.sleep(200000);
    }


}
