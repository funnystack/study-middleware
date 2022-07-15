package com.funny.study.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

public class KafkaConfig {
    private static String kafkaServers ="kafka1.com:9092,kafka2.com:9092,kafka3.com:9092";
    private static int kafkaRetries = 0;
    private static int kafkaBatchSize = 16384;
    private static int kafkaBufferMemory = 33554432;

    public static KafkaTemplate<String, String> kafkaTemplate = null;

    static {
        kafkaTemplate = new KafkaTemplate<>(producerFactory());
    }



    private static Map<String, Object> kafkaConfigs() {
        return getStringObjectMap(kafkaServers, kafkaRetries, kafkaBatchSize, kafkaBufferMemory);
    }

    private static Map<String, Object> getStringObjectMap(String stockServers, int stockRetries, int stockBatchSize, int stockBufferMemory) {
        Map<String, Object> props = new HashMap<>(7);
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, stockServers);
        props.put(ProducerConfig.RETRIES_CONFIG, stockRetries);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, stockBatchSize);
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, stockBufferMemory);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
    }

    private static ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(kafkaConfigs());
    }
}
