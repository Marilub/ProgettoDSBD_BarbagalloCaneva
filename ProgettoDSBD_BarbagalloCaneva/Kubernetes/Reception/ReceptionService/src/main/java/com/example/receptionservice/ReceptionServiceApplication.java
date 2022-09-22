package com.example.receptionservice;

import io.micrometer.core.aop.TimedAspect;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class ReceptionServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReceptionServiceApplication.class, args);
    }
    public static Integer sessionId;
    public static String nameLogged;

    public static Integer maxRicoveri=5;

    @Bean
    public TimedAspect timedAspect(MeterRegistry registry){
        return new TimedAspect(registry);
    }

}
