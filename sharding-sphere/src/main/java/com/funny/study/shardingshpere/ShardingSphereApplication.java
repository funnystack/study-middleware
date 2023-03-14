package com.funny.study.shardingshpere;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@MapperScan("com.funny.study.shardingshpere.dao")
@EnableTransactionManagement(proxyTargetClass = true)   //开启事物管理功能
public class ShardingSphereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingSphereApplication.class, args);
    }

}
