package com.funny.study.kafka.test;

import com.funny.study.kafka.kafka.KafkaSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@SpringBootTest
@RunWith(SpringRunner.class)
public class KafkaTest {

    public static final String topic = "user_address";


    @Resource
    private KafkaSender kafkaSender;

    @Test
    public void writeKafkaSync() {
        long start = System.currentTimeMillis();
        for (int j = 0; j < 1_000; j++) {
            kafkaSender.sendSync(topic, j + "");
        }
        System.out.println("writeKafkaSync used:" + (System.currentTimeMillis() - start));
    }

    @Test
    public void writeKafkaAsync() throws InterruptedException {
        long start = System.currentTimeMillis();
        for (int j = 0; j < 1_000; j++) {
            kafkaSender.sendAsync(topic, j + "");
        }
        System.out.println("writeKafkaAsync used:" + (System.currentTimeMillis() - start));
        Thread.sleep(200000);
    }


}
