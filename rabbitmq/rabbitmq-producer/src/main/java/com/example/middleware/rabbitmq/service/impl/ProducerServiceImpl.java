package com.example.middleware.rabbitmq.service.impl;

import com.example.middleware.rabbitmq.service.ProducerService;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@Service
public class ProducerServiceImpl implements ProducerService {
    @Autowired
    private Connection connection;
    @Autowired
    private Channel channel;

    private final String QUEUE_NAME = "hello";

    @Override
    public String showHello(String name) {
        // 消息内容
        String message = name + "Hello World!";
        try {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            /*
             * 向server发布一条消息
             * 参数1：exchange名字，若为空则使用默认的exchange
             * 参数2：routing key
             * 参数3：其他的属性
             * 参数4：消息体
             * RabbitMQ默认有一个exchange，叫default exchange，它用一个空字符串表示，它是direct exchange类型，
             * 任何发往这个exchange的消息都会被路由到routing key的名字对应的队列上，如果没有对应的队列，则消息会被丢弃
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [生产者] Sent '" + message + "'");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭通道和连接
                channel.close();
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        return message;
    }
}
