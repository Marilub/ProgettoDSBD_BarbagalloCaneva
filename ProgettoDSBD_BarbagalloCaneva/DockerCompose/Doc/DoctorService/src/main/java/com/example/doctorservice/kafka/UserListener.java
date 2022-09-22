package com.example.doctorservice.kafka;

import com.example.doctorservice.DoctorServiceApplication;
import com.example.doctorservice.Events.EventoRicovero;
import com.example.doctorservice.Model.PatientsRepository;
import com.example.doctorservice.Model.Pazienti;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserListener {




    @Autowired
    private ApplicationEventPublisher triggerOfEvent;
    @Autowired
    PatientsRepository patientsRepository;

    @KafkaListener(topics="${KAFKA_MAIN_TOPIC}",groupId = "${KAFKA_GROUP_ID}")
    public void listenSession(String message) {
        System.out.println("Received message " + message);

        String[] messageParts = message.split("\\|");

        if (messageParts[0].equals("UserLogged") || messageParts[0].equals("UserRegistered")) {

            String type=messageParts[2];
            if(type.equals("d")){
                String myId = messageParts[1];
                DoctorServiceApplication.sessionId= Integer.valueOf(myId);
                System.out.println("Sessione attivata con ID = "+ DoctorServiceApplication.sessionId.toString());
            }
        }
    }

    @KafkaListener(topics="${KAFKA_PAZIENTE_TOPIC}",groupId = "${KAFKA_GROUP_ID}")
    public void listenNewPatient(String message){
        System.out.println("Received message " + message);
        String[] messageParts = message.split("\\|");
        if (messageParts[0].equals("Paziente")){
            if(!patientsRepository.findByIdMedicoAndIdPaziente(Integer.valueOf(messageParts[6]),Integer.valueOf(messageParts[5])).isPresent()) {
                Pazienti p= new Pazienti();
                p.setNome(messageParts[1]);
                p.setCognome(messageParts[2]);
                p.setEta(Integer.valueOf(messageParts[3]));
                p.setGenere(messageParts[4]);
                p.setIdPaziente(Integer.valueOf(messageParts[5]));
                p.setIdMedico(Integer.valueOf(messageParts[6]));
                patientsRepository.save(p);
            }
        }
    }

    @KafkaListener(topics="${KAFKA_DOCTOR_TOPIC}",groupId = "${KAFKA_GROUP_ID}")
    public void listenRicovero(String message){
        System.out.println("Received message " + message);
        String[] messageParts = message.split("\\|");
        if (messageParts[0].equals("InsuccessoRicovero")){
            Integer idPaziente=Integer.valueOf(messageParts[1]);
            Optional<Pazienti> p=patientsRepository.findByIdMedicoAndIdPaziente(DoctorServiceApplication.sessionId,idPaziente);
            if(p.isPresent() && p.get().getRicovero()==true){
                p.get().setRicovero(false);
                patientsRepository.save(p.get());
                System.out.println("sto per pubblicare l'esito");
                publishEvent(false);
            }
        } else if (messageParts[0].equals("SuccessoRicovero")){
            System.out.println("sto per pubblicare l'esito");
            publishEvent(true);
        }

    }

    public void publishEvent(Boolean successoRicovero){
        EventoRicovero e= new EventoRicovero(this,successoRicovero);
        triggerOfEvent.publishEvent(e);
    }

}
