package com.example.reglogservice;

import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import io.micrometer.core.aop.TimedAspect;

@SpringBootApplication
public class RegLogServiceApplication {

    public static void main(String[] args) {
        System.out.println("applicazione partita");
        SpringApplication.run(RegLogServiceApplication.class, args);
    }

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry){
    return new TimedAspect(registry);
    }
}

