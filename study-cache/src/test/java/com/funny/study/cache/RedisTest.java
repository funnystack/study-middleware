package com.funny.study.cache;


import com.funny.study.cache.redis.RedisUtil;
import org.junit.Test;

import javax.annotation.Resource;


public class RedisTest {

    @Resource
    private RedisUtil redisUtil;

    @Test
    public void writeKafkaSync() {
        boolean limit = redisUtil.accessRateLimit("limit:123456", 6, 1);
        System.out.println(limit);
    }

}
