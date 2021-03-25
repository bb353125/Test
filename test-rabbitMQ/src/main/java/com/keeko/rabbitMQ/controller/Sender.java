package com.keeko.rabbitMQ.controller;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 发送者
 */
public class Sender {
    private final static String QUEUE_NAME = "helloQueue";

    public static void main(String[] args) throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.91.209.189");
        factory.setPort(5672);
        factory.setUsername("user08");
        factory.setPassword("useer08sgds20f");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        for (int i = 0; i < 30; i++) {
            String message = "RabbitMQ Demo Test:" + System.currentTimeMillis();
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
            Thread.sleep(50);
        }

        channel.close();
        connection.close();
    }


}