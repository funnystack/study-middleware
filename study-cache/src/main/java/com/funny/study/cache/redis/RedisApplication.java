package com.funny.study.cache.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 启动程序
 * @date  2018/12/10 下午2:30
 */
@EnableTransactionManagement
@SpringBootApplication(scanBasePackages = {"com.funny.study.cache.redis"})
public class RedisApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisApplication.class, args);
    }

}
