package com.example.receptionservice.Kafka;

import com.example.receptionservice.Model.PatientsRepository;
import com.example.receptionservice.Model.Pazienti;
import com.example.receptionservice.ReceptionServiceApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class RicoveroListener {
    @Autowired
    PatientsRepository patientsRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value="${KAFKA_DOCTOR_TOPIC}")
    private String ricoveriTopic;

    @KafkaListener(topics="${KAFKA_DOCTOR_TOPIC}",groupId = "${KAFKA_GROUP_ID}")
    public void listenRicoveri(String message) {
        System.out.println("Received message " + message);

        String[] messageParts = message.split("\\|");

        if(messageParts[0].equals("Ricovero")){
            if(patientsRepository.countByRicovero(true)< ReceptionServiceApplication.maxRicoveri){
                Integer idPaziente=Integer.valueOf(messageParts[1]);
                Pazienti p=patientsRepository.findById(idPaziente).get();
                p.setRicovero(true);
                patientsRepository.save(p);
                kafkaTemplate.send(ricoveriTopic, "SuccessoRicovero");

            } else  kafkaTemplate.send(ricoveriTopic, "InsuccessoRicovero"+"|"+Integer.valueOf(messageParts[1]));
        }
        if(messageParts[0].equals("Dimissione")){
            Integer idPaziente=Integer.valueOf(messageParts[1]);
            Pazienti p=patientsRepository.findById(idPaziente).get();
            p.setRicovero(false);
            patientsRepository.save(p);
        }


    }
}
