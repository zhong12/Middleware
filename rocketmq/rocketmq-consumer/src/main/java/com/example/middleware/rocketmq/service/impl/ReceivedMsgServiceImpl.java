package com.example.middleware.rocketmq.service.impl;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.example.middleware.rocketmq.service.ReceivedMsgService;
import org.springframework.stereotype.Service;

/**
 * Created by zhongjing on 2017/12/26.
 */
@Service
public class ReceivedMsgServiceImpl implements ReceivedMsgService {
    
    @Override
    public String received() throws MQClientException {
        return null;
    }
}
