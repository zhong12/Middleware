package com.example.dubbo.services;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import org.springframework.stereotype.Service;

/**
 * Created by zhongjing on 2017/03/24.
 */
@Service
public class HelloServiceImpl implements IHelloService {
    @Override
    public String sayHello(String name, Integer age) {
        Transaction transaction = Cat.newTransaction("Service","sayHello");
        try{
            Cat.logEvent("Service.abc.client","192.168.227.78");
            Cat.logEvent("Service.abc.app","cbA");
        }catch(Exception e){
            Cat.logError(e);
            transaction.setStatus(e);
        }finally {
            transaction.setStatus(Transaction.SUCCESS);
            transaction.complete();
        }
        return "我用的是dubbo协议，我叫" + name + "，今年" + age + "岁";
    }

    @Override
    public String show(String name) {
        return "我用的是rmi协议，我叫" + name;
    }
}
