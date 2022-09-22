package com.example.receptionservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ReceptionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReceptionServiceApplication.class, args);
    }
    public static Integer sessionId;
    public static String nameLogged;

    public static Integer maxRicoveri=5;

}
