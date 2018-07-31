package com.example.middleware.rabbitmq.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

@ComponentScan
public class ConsumerConfig {

    /**
     * 创建连接connectionFactory
     */
    private ConnectionFactory connectionFactory = null;
    /**
     * 连接connection
     */
    private Connection connection = null;
    /**
     * 发送channel
     */
    private Channel channel = null;

    @Value("${spring.rabbit.host}")
    private String host;
    @Value("${spring.rabbit.port:0}")
    private String port;
    @Value("${spring.rabbit.userName}")
    private String userName;
    @Value("${spring.rabbit.passWord}")
    private String passWord;
    @Value("${spring.rabbit.virtualHost}")
    private String virtualHost;


    @Bean
    public ConnectionFactory getFactory() {
        if (null == connectionFactory) {
            connectionFactory = new ConnectionFactory();
        }
        connectionFactory.setHost(host);
        connectionFactory.setPort(Integer.parseInt(port));
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(passWord);
        connectionFactory.setVirtualHost(virtualHost);
        return connectionFactory;
    }


    @Bean
    public Connection getConnection() throws IOException, TimeoutException {
        if (null == connection) {
            connection = connectionFactory.newConnection();
        }
        return connection;
    }

    @Bean
    public Channel getChannel() {
        if (null == channel) {
            try {
                channel = connection.createChannel();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("channel 出现错误");
            } finally {
                try {
                    if (null != channel) {
                        channel.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TimeoutException e) {
                    e.printStackTrace();
                }
            }
        }
        return channel;
    }
}
