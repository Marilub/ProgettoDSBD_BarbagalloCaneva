package com.example.doctorservice.KafkaConfig;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TopicConfiguration {

    @Value(value = "${KAFKA_ADDRESS}")
    private String bootstrapAddress;

    @Value(value = "${KAFKA_MAIN_TOPIC}")
    private String kafkaMainTopic;

    @Value(value= "${KAFKA_PAZIENTE_TOPIC}")
    private String kafkaPazienteTopic;

    @Value(value= "${KAFKA_DOCTOR_TOPIC}")
    private String kafkaDoctorTopic;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic topic1() {
        return new NewTopic(kafkaMainTopic, 10, (short) 1);
    }

    @Bean
    public NewTopic topic2() {
        return new NewTopic(kafkaPazienteTopic, 10, (short) 1);
    }

    @Bean
    public NewTopic topic3() {
        return new NewTopic(kafkaDoctorTopic, 10, (short) 1);
    }
}