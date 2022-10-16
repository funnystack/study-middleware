package com.funny.study.storage.minio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动程序
 * @date  2018/12/10 下午2:30
 */
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.funny.study.kafka"})
public class KafkaApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaApplication.class, args);
    }

}
