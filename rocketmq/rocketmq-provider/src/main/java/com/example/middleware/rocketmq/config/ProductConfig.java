package com.example.middleware.rocketmq.config;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.hook.SendMessageContext;
import com.alibaba.rocketmq.client.hook.SendMessageHook;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhongjing on 2017/12/26.
 */
@Configuration
public class ProductConfig {
    
    @Value("${spring.rockermq.producerGroup}")
    private String producerGroup;
    @Value("${spring.rockermq.namesrvAddr}")
    private String namesrvAddr;
    
    @Bean
    public DefaultMQProducer getDefaultMQProducer() throws MQClientException {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer(new ZbRPCHook());
        defaultMQProducer.getDefaultMQProducerImpl().registerSendMessageHook(new ZbSendMessageHook());
        defaultMQProducer.setProducerGroup(producerGroup);
        defaultMQProducer.setNamesrvAddr(namesrvAddr);
        defaultMQProducer.start();
        return defaultMQProducer;
    }
}
