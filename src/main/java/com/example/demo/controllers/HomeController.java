package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @RequestMapping("/")
    public String index()
    {
        return "forward:/index.html";
    } 

    // @RequestMapping("/")
    // public @ResponseBody String greetings()
    // {
    //     return "Hello, World";
    // } 

}
