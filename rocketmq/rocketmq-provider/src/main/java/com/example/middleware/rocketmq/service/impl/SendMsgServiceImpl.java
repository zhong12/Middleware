package com.example.middleware.rocketmq.service.impl;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.producer.DefaultMQProducer;
import com.alibaba.rocketmq.client.producer.SendResult;
import com.alibaba.rocketmq.common.message.Message;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.example.middleware.rocketmq.service.SendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhongjing on 2017/12/26.
 */
@Service
public class SendMsgServiceImpl implements SendMsgService {
    @Autowired
    private DefaultMQProducer defaultMQProducer;
    
    @Override
    public String send(String name) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        for (int i = 0; i < 1; i++) {
            String body = "Hello," + name + "第" + i + "次";
            Message msg = new Message("abc111", "abc123", body.getBytes());
            SendResult result = defaultMQProducer.send(msg);
            System.out.println("id:" + result.getMsgId() + " result:" + result.getSendStatus());
        }
        return null;
    }


    @Override
    public String sendTask(int i) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        String body = "task 任务" + i;
        Message msg = new Message("abc111", "abc123", body.getBytes());
        SendResult result = defaultMQProducer.send(msg);
        System.out.println("id:" + result.getMsgId() + " result:" + result.getSendStatus());
        return null;
    }
}
