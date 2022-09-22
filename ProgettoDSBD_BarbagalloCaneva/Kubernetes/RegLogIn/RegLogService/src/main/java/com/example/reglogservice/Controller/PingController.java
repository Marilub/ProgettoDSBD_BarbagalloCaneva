package com.example.reglogservice.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PingController {
    @RequestMapping(path="/ping")
    public String ping(){
        return "pong";
    }

}
