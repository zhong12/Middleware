package com.example.middleware.rabbitmq.service;

import java.io.IOException;

public interface ConsumerService {
    /**
     * 获取消息
     */
    public void getShow() throws IOException, InterruptedException;
}
