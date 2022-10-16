package com.funny.study.storage.minio.controller;

import com.funny.study.storage.minio.kafka.KafkaSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @date 2019/2/19 下午4:50
 * @description
 */
@RestController
public class MonitorController {

    @Resource
    private KafkaSender kafkaSender;

    @RequestMapping(value = "/health/status")
    public String health() {
        return "ok";
    }


    @RequestMapping(value = "/send")
    public String send(String msg) {
        kafkaSender.sendSync("user_address", msg);
        return "ok";
    }

}
