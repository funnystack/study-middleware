package com.funny.study.test;

import com.funny.study.rabbitmq.MQReceiver;
import com.funny.study.rabbitmq.MQSender;

public class RabbitMQTest {

    public static void main(String[] args) throws Exception {
        MQReceiver MQReceiver = new MQReceiver();
        MQReceiver.start();

        MQSender.sendMessage("fangli");
    }
}
