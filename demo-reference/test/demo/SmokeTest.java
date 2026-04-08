package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.controllers.HomeController;

@SpringBootTest
public class SmokeTest {

    @Autowired
    private HomeController controller;

    @Test
    void contextLoads() throws Exception
    {
    
        assertThat(controller).isNotNull();
    }

    // @Test
    // void checkGreetings()
    // {
    //     String message = controller.greetings();
    //     assertEquals(message, "Hello, World");

    // }
   

}
