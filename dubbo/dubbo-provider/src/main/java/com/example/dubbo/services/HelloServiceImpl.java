package com.example.dubbo.services;

import org.springframework.stereotype.Service;

/**
 * Created by zhongjing on 2017/03/24.
 */
@Service
public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHello(String name, Integer age) {
        return "我用的是dubbo协议，我叫" + name + "，今年" + age + "岁";
    }

    @Override
    public String show(String name) {
        return "我用的是rmi协议，我叫" + name;
    }
}
