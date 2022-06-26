package com.damingerdai.securityjwtapp.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gming001
 * @version 2022-06-26 21:23
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/list")
    public String list(){
        return "1111";
    }
}
