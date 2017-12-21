package com.example.middleware.rabbitmq;

import com.example.middleware.rabbitmq.producer.Producer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;

@SpringBootApplication
public class RabbitmqProducerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RabbitmqProducerApplication.class, args);
	}


	public RabbitmqProducerApplication() throws Exception {

		QueueConsumer consumer = new QueueConsumer("VH-QUEUE");
		Thread consumerThread = new Thread(consumer);
		consumerThread.start();

		Producer producer = new Producer("VH-QUEUE");

		for (int i = 0; i < 10; i++) {
			HashMap message = new HashMap();
			message.put("message number", i);
			producer.sendMessage(message);
			System.out.println("Message Number " + i + " sent.");
		}
	}
}
