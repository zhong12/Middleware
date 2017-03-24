package com.example.dubbo.services;

/**
 * Created by zhongjing on 2017/03/21.
 */
public class DemoServiceImpl implements IDemoService {
    @Override
    public String sayHello(String name) {
        return "hello，我叫" + name;
    }
}
