package com.example.middleware.rocketmq.config;

import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.common.consumer.ConsumeFromWhere;
import com.example.middleware.rocketmq.listener.ZbMessageListenerConcurrently;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhongjing on 2017/12/26.
 */
@Configuration
public class ConsumerConfig {
    
    @Value("${spring.rockermq.consumerGroup}")
    private String consumerGroup;
    @Value("${spring.rockermq.namesrvAddr}")
    private String namesrvAddr;

    @Bean
    public DefaultMQPushConsumer getDefaultMQProducer() throws MQClientException {
        DefaultMQPushConsumer defaultMQPushConsumer = new DefaultMQPushConsumer();
        defaultMQPushConsumer.setConsumerGroup(consumerGroup);
        defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);
        defaultMQPushConsumer.subscribe("abc111","abc123");
        defaultMQPushConsumer.setMessageListener(new ZbMessageListenerConcurrently());
        defaultMQPushConsumer.getDefaultMQPushConsumerImpl().registerConsumeMessageHook(new ZbConsumeMessageHook());
        defaultMQPushConsumer.start();
        /**
         * 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
         * 如果非第一次启动，那么按照上次消费的位置继续消费
         */
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        return defaultMQPushConsumer;
    }
}
