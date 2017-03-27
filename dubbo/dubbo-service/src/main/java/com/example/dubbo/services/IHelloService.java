package com.example.dubbo.services;

/**
 * Created by zhongjing on 2017/03/24.
 */
public interface IHelloService {
    /**
     * 打招呼
     * @param name
     * @param age
     * @return
     */
    public String sayHello(String name, Integer age);


    /**
     * 打招呼
     * @param name
     * @return
     */
    public String show(String name);
}
