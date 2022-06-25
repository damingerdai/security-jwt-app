package com.damingerdai.securityjwtapp.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gming001
 * @version 2022-06-25 18:06
 */
@RestController
public class PingController {

    @RequestMapping(path = "ping")
    public String ping() {
        return "pong";
    }
}
