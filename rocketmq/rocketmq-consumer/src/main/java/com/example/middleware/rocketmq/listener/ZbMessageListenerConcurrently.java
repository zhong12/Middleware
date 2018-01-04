package com.example.middleware.rocketmq.listener;

import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import com.alibaba.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import com.alibaba.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import com.alibaba.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhongjing on 2017/12/26.
 */
@Service
public class ZbMessageListenerConcurrently implements MessageListenerConcurrently {
    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        for (MessageExt messageExt : list) {
            System.out.println(new String(messageExt.getBody()));
        }
//        System.out.println("getDelayLevelWhenNextConsume=" + consumeConcurrentlyContext.getDelayLevelWhenNextConsume() + ", getMessageQueue=" + consumeConcurrentlyContext.getMessageQueue().toString() + ",  getDelayLevelWhenNextConsume=" + consumeConcurrentlyContext.getDelayLevelWhenNextConsume());
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
