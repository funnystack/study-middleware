package com.funny.study.kafka.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class UserAddressListener  {

    private static final Logger logger = LoggerFactory.getLogger(UserAddressListener.class);


    @KafkaListener(topics = "#{'${kafka.topic.user_address}'}", groupId = "syncUserAddress")
    public void updateUserAddress(ConsumerRecord<Integer, String> record, Acknowledgment ack) {
        String orderId = "";
        try {
            logger.info("updateUserAddress, topic = {}, msg = {}", record.topic(), record.value());
            if (record.value() == null ) {
                ack.acknowledge();
                return;
            }

            ack.acknowledge();
        } catch (Exception e) {
            ack.acknowledge();
            logger.error("updateUserAddress异常,orderId={}", orderId, e);
        }
    }

}
