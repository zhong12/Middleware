package com.example.middleware.rocketmq.service;

import com.alibaba.rocketmq.client.exception.MQClientException;

/**
 * Created by zhongjing on 2017/12/26.
 */
public interface ReceivedMsgService {
    /**
     * 
     * @return
     */
    String received() throws MQClientException;
}
