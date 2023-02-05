package com.pranav.springsecurityclient.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@Slf4j
public class HelloController {

    @Autowired
    private WebClient webClient;

    @GetMapping("/hello")
    public String sayHelloFromTheClient(){
        return "Hello from the spring security client application.";
    }


}
