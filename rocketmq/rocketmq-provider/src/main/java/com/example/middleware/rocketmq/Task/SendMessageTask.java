package com.example.middleware.rocketmq.Task;

import com.example.middleware.rocketmq.service.SendMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by zhongjing on 2018/01/03.
 */
@Component("sendMessageTask")
@Scope("prototype")
public class SendMessageTask extends Thread {
    @Autowired
    private SendMsgService sendMsgService;
    
    @Override
    public void run() {
        int i = 0;
        while (i < 5) {
            try {
                sendMsgService.sendTask(i);
                i++;
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
