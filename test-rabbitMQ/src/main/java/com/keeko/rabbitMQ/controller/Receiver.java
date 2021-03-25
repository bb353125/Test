package com.keeko.rabbitMQ.controller;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/**
 * 接收者
 */
public class Receiver {

    private String name;
    private int time;
    private final static String QUEUE_NAME = "helloQueue";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Receiver(String name, int time) {
        super();
        this.name = name;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void work() throws Exception {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("47.91.209.189");
        factory.setPort(5672);
        factory.setUsername("user08");
        factory.setPassword("useer08sgds20f");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        /**
         * 	durable：true、false true：在服务器重启时，能够存活
         exclusive ：是否为当前连接的专用队列，在连接断开后，会自动删除该队列，生产环境中应该很少用到吧。
         autodelete：当没有任何消费者使用时，自动删除该队列。this means that the queue will be deleted when there are no more processes consuming messages from it.
         */
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        channel.basicQos(1);
        System.out.println(name + " 在等待消息...");

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(name + " 获取到消息：" + message + "'");
                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(name + " 正在处理消息： '" + message + "'");
                //通知已处理完成
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        channel.basicConsume(QUEUE_NAME, false, consumer);
    }

}
