package com.example.middleware.rabbitmq.controller;

import com.example.middleware.rabbitmq.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProducerController {

    @Autowired
    private ProducerService producerService;

    /**
     * show
     * @return
     */
    @RequestMapping(value = "/show", method = RequestMethod.GET, produces = "application/json")
    public String setPassword(@RequestParam("name") String name){
        return producerService.showHello(name);
    }
}
