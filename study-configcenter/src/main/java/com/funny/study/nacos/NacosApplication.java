package com.funny.study.nacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动程序
 * @date  2018/12/10 下午2:30
 */
@SpringBootApplication(scanBasePackages = {"com.funny.study.nacos"})
public class NacosApplication {

    public static void main(String[] args) {
        SpringApplication.run(NacosApplication.class, args);
    }

}
