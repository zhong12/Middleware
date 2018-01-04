package com.example.middleware.rocketmq.controller;

import com.alibaba.rocketmq.client.exception.MQClientException;
import com.example.middleware.rocketmq.service.ReceivedMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhongjing on 2017/12/26.
 */
@RestController
@RequestMapping(value = "/")
public class ReceivedMsgController {
    @Autowired
    private ReceivedMsgService receivedMsgService;
    
    @RequestMapping(value = "/received", method = RequestMethod.GET)
    public String queryCustomTag() {
        try {
            receivedMsgService.received();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        return null;
    }
}
