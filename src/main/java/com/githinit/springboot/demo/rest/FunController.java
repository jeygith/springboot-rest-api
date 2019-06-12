package com.githinit.springboot.demo.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FunController {
    @Value("${server.port}")
    private String port;


    @GetMapping("/")
    public String sayHello() {
        return "Port: " + port;
    }

    @GetMapping("/workout")
    public String getDailyWorkout() {
        return "Run a hard 5k and sprint fas!!";
    }


    //hello worl
}


