package com.funny.study.kafka.kafka;

import com.funny.study.kafka.util.SpringUtils;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@Component
public class KafkaSender {
    private static final Logger logger = LoggerFactory.getLogger(KafkaSender.class);
    public void sendSync(String topic, String message) {
        try {
            KafkaTemplate kafkaTemplate = SpringUtils.getBean(KafkaTemplate.class);
            ListenableFuture<SendResult<String, String>> send = kafkaTemplate.send(topic, message);
            SendResult<String, String> result = send.get();
            RecordMetadata metadata = result.getRecordMetadata();
            logger.info("send kafka end,topic={},partition={},offset={}", topic, metadata.partition(), metadata.offset());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void sendAsync(String topic, String message) {
        KafkaTemplate kafkaTemplate = SpringUtils.getBean(KafkaTemplate.class);
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
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
