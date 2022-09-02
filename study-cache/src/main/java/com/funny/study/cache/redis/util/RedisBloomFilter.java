package com.funny.study.cache.redis.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.redisson.Redisson;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.redisson.config.Config;
import org.redisson.config.TransportMode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedisBloomFilter {

    private static RedissonClient createClient() {
        Config config = new Config();
        // EPOLL 只能在 Linux 下使用
        config.setTransportMode(TransportMode.NIO);
        config.useSingleServer()
                .setAddress("redis://localhost:6379")
                .setDatabase(0)
                .setClientName("redisson-test")
                .setConnectionPoolSize(128)
                .setConnectionMinimumIdleSize(128)
                .setIdleConnectionTimeout(60000)
                .setConnectTimeout(1000)
                .setTimeout(3000)
                .setRetryAttempts(1);
        config.setCodec(new JsonJacksonCodec(new ObjectMapper()));
        config.setNettyThreads(128).setThreads(128);
        return Redisson.create(config);
    }


    public static void main(String[] args) {

        RedissonClient client = createClient();
        RBloomFilter<Object> bloomFilter = client.getBloomFilter("bloomFilter");
        // 初始化布隆过滤器，预计元素为1_000_000,误判率为1%
        bloomFilter.tryInit(1_000_000, 0.01);

        Map<String, String> map = new HashMap<>();
        map.put("aa", "bb");

        List<Integer> list = Arrays.asList(1, 2, 3, 4);
        bloomFilter.add(map);
        bloomFilter.add(list);
        bloomFilter.add(true);
        bloomFilter.add("bloomFilter");

        System.out.println("contains true=" + bloomFilter.contains(true));
        System.out.println("contains false=" + bloomFilter.contains(false));
        System.out.println("contains map=" + bloomFilter.contains(map));
        System.out.println("contains list=" + bloomFilter.contains(list));

        System.out.println("size=" + bloomFilter.getSize());
        System.out.println("count=" + bloomFilter.count());
    }
}
