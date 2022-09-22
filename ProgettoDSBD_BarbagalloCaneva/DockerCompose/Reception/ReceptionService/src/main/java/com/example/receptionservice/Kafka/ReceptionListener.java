package com.example.receptionservice.Kafka;

import com.example.receptionservice.Model.DoctorsRepository;
import com.example.receptionservice.Model.Dottori;
import com.example.receptionservice.Model.PatientsRepository;
import com.example.receptionservice.Model.Pazienti;
import com.example.receptionservice.ReceptionServiceApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReceptionListener {


    @Autowired
    DoctorsRepository doctorsRepository;


    @KafkaListener(topics="${KAFKA_MAIN_TOPIC}",groupId = "${KAFKA_GROUP_ID}")
    public void listen(String message) {
        System.out.println("Received message " + message);

        String[] messageParts = message.split("\\|");
        String type=messageParts[2];

        if (messageParts[0].equals("UserLogged") || messageParts[0].equals("UserRegistered")) {


            if(type.equals("r")){
                String myId = messageParts[1];
                ReceptionServiceApplication.sessionId= Integer.valueOf(myId);
                ReceptionServiceApplication.nameLogged=messageParts[3];
                System.out.println("Sessione attivata con ID = "+ ReceptionServiceApplication.sessionId.toString());
            }

        }
        if (messageParts[0].equals("UserRegistered") && type.equals("d")){
            Dottori doctor= new Dottori();
            doctor.setIdMedico(Integer.valueOf(messageParts[1])).setNome(messageParts[3]).setCognome(messageParts[4]);
            doctorsRepository.save(doctor);

        }
    }



}
