//package com.example.middleware.rocketmq.config;
//
//import com.example.middleware.rocketmq.Task.SendMessageTask;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//
///**
// * Created by zhongjing on 2018/01/03.
// */
//@Component
//public class TaskConfig {
//
//    //定义在构造方法完毕后，执行这个初始化方法
//    @PostConstruct
//    public void init(){
//        SendMessageTask sendMessageTask= ApplicationContextProvider.getBean("sendMessageTask", SendMessageTask.class);
//        sendMessageTask.start();
//    }
//}
