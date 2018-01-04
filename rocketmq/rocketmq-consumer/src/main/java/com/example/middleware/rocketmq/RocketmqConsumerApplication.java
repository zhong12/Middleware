package com.example.middleware.rocketmq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.middleware.rocketmq")
@ServletComponentScan
public class RocketmqConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(RocketmqConsumerApplication.class, args);
	}
}
