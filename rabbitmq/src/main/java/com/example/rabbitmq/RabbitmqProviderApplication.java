package com.example.rabbitmq;

import com.example.rabbitmq.consumer.QueueConsumer;
import com.example.rabbitmq.producer.Producer;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@SpringBootApplication
public class RabbitmqProviderApplication {

    public static void main(String[] args) throws Exception {
    //		SpringApplication.run(RabbitmqProviderApplication.class, args);
        new RabbitmqProviderApplication();
    }

    public RabbitmqProviderApplication() throws Exception {
    
        QueueConsumer consumer = new QueueConsumer("queue");
        Thread consumerThread = new Thread(consumer);
        consumerThread.start();
    
        Producer producer = new Producer("queue");
    
        for (int i = 0; i < 100000; i++) {
            HashMap message = new HashMap();
            message.put("message number", i);
            producer.sendMessage(message);
            System.out.println("Message Number " + i + " sent.");
        }
    }

}
