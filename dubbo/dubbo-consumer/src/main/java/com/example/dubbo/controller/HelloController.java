package com.example.dubbo.controller;

import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;
import com.example.dubbo.services.DubboCatContext;
import com.example.dubbo.services.IHelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhongjing on 2017/03/24.
 */
@RestController
@RequestMapping(value = "/hello")
public class HelloController {

    @Autowired
    private IHelloService helloService;

    @RequestMapping(value = "/say", method = RequestMethod.GET)
    public String say(@RequestParam("name") String name, @RequestParam("age") Integer age) {
        Transaction transaction = Cat.newTransaction("Call", "sayHello");
        String msg = null;
        try {
            Cat.logEvent("Call.abc.server", "192.168.227.78");
            Cat.logEvent("Call.abc.app", "cbA");
            Cat.logEvent("Call.port", "2181");
            msg = helloService.sayHello(name, age);
        } catch (Exception e) {
            Cat.logError(e);
            transaction.setStatus(e);
        } finally {
            transaction.setStatus(Transaction.SUCCESS);
            transaction.complete();
        }
        return msg;
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(@RequestParam("name") String name) {
        String msg = helloService.show(name);
        return msg;
    }
}
