package com.example.middleware.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {
    @Autowired
    private Connection connection;
    @Autowired
    private Channel channel;


}
