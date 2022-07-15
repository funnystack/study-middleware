package com.funny.study.kafka;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

public class KafkaSender {
    private static final Logger logger = LoggerFactory.getLogger(KafkaSender.class);


    public static void sendSync(String topic, String message) {
        try {
            ListenableFuture<SendResult<String, String>> send = KafkaConfig.kafkaTemplate.send(topic, message);
            SendResult<String, String> result = send.get();
            RecordMetadata metadata = result.getRecordMetadata();
            logger.info("send kafka end,topic={},partition={},offset={}", topic, metadata.partition(), metadata.offset());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void sendAsync(String topic, String message) {
        ListenableFuture<SendResult<String, String>> future = KafkaConfig.kafkaTemplate.send(topic, message);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            //发送消息成功回调
            @Override
            public void onSuccess(SendResult<String, String> result) {
                RecordMetadata metadata = result.getRecordMetadata();
                logger.info("send kafka end,topic={},partition={},offset={}", topic, metadata.partition(), metadata.offset());
            }

            //发送消息失败回调
            @Override
            public void onFailure(Throwable ex) {
                ex.printStackTrace();
                logger.error("send kafka error,topic={},message={}", topic, message, ex);
            }
        });

    }

}
