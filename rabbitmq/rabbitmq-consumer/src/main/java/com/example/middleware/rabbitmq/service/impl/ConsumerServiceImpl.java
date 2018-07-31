package com.example.middleware.rabbitmq.service.impl;

import com.example.middleware.rabbitmq.service.ConsumerService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.QueueingConsumer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class ConsumerServiceImpl implements ConsumerService {
    @Autowired
    private Channel channel;

    private final String QUEUE_NAME = "hello";

    @Override
    public void getShow() throws IOException, InterruptedException {

        // 声明队列(如果你已经明确的知道有这个队列,那么下面这句代码可以注释掉,如果不注释掉的话,也可以理解为消费者必须监听一个队列,如果没有就创建一个)
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        // 定义队列的消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);
        /*
         * 监听队列
         * 参数1:队列名称
         * 参数2：是否发送ack包，不发送ack消息会持续在服务端保存，直到收到ack。  可以通过channel.basicAck手动回复ack
         * 参数3：消费者
         */
        channel.basicConsume(QUEUE_NAME, true, consumer);

        // 获取消息
        while (true) {
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String message = new String(delivery.getBody());
            System.out.println(" [消费者] Received '" + message + "'");
        }


    }
}
