package com.example.middleware.rocketmq.service;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.remoting.exception.RemotingException;

/**
 * Created by zhongjing on 2017/12/26.
 */
public interface SendMsgService {

    /**
     * say
     * @param name
     * @return
     */
    String send(String name) throws InterruptedException, RemotingException, MQClientException, MQBrokerException;


    String sendTask(int i) throws InterruptedException, RemotingException, MQClientException, MQBrokerException;
}
