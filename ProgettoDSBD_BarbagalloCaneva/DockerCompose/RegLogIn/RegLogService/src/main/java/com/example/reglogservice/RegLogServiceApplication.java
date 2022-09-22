package com.example.reglogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
public class RegLogServiceApplication {

    public static void main(String[] args) {
        System.out.println("applicazione partita");
        SpringApplication.run(RegLogServiceApplication.class, args);
    }


}
