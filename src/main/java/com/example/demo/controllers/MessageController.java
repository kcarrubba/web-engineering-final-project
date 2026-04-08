package com.example.demo.controllers;

import org.springframework.web.bind.annotation.RestController;
import com.example.demo.entities.Message;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class MessageController {

    @RequestMapping("/hello")
    public String sayHello()
    {
        return "Hello World!";
    }

    @RequestMapping("/hello2")
    public Message sayHelloWorld()
    {
        return new Message("Hello World!");
    }
}
