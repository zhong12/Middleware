package com.example.middleware.rocketmq.controller;

import com.alibaba.rocketmq.client.exception.MQBrokerException;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.remoting.exception.RemotingException;
import com.example.middleware.rocketmq.service.SendMsgService;
import com.zb.cloud.logcenter.dto.LogCenterThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhongjing on 2017/12/26.
 */
@RestController
@RequestMapping(value = "/")
public class SendMsgController {
    @Autowired
    private SendMsgService sendMsgService;

    /**
     * say
     * @param name
     * @return
     */
    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public String queryCustomTag(@RequestParam(value = "name", required = false) String name) {
        try {
            return sendMsgService.send(name);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        }
        return null;
    }
}
