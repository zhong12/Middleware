package com.example.dubbo.controller;

import com.example.dubbo.services.IDemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by zhongjing on 2017/03/21.
 */
@RestController
@RequestMapping(value = "/test")
public class DemeController {
    @Autowired
    private IDemoService demoService;

    @RequestMapping(value = "/say", method = RequestMethod.GET)
    public String say(@RequestParam("name") String name) {
        String msg = demoService.sayHello(name);
        return msg;
    }
}
