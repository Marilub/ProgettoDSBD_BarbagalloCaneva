package com.example.doctorservice.Controller;

import com.example.doctorservice.DoctorServiceApplication;
import com.example.doctorservice.Events.EventoRicovero;
import com.example.doctorservice.Model.PatientsRepository;
import com.example.doctorservice.Model.Pazienti;
import com.google.gson.Gson;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/doctor")
public class DoctorController implements ApplicationListener<EventoRicovero>{

    private Boolean successoRicovero=new Boolean(false);
    private Boolean eventoTerminato=new Boolean(false);
    @Autowired
    PatientsRepository patientsRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value="${KAFKA_DOCTOR_TOPIC}")
    private String ricoveriTopic;


    @GetMapping(path="/search/{nome}/{cognome}", produces={"application/JSON"})
    //rispondiamo con uno o più pazienti (omonimia)
    public @ResponseBody ResponseEntity<String> cerca(@PathVariable("nome") String nome, @PathVariable("cognome") String cognome){
        System.out.println("ricerca iniziata");
        Gson gson= new Gson();
        List<Pazienti> pazienti= new ArrayList<Pazienti>();
        pazienti= patientsRepository.findByNomeAndCognomeAndIdMedico(nome,cognome, DoctorServiceApplication.sessionId);
        if(pazienti.size()>0) {
          return new ResponseEntity<>(gson.toJson(pazienti), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @PostMapping(path="/insertDiagnosis/{idPaziente}", consumes={"application/JSON"}, produces={"application/JSON"})
    public @ResponseBody ResponseEntity<Optional<Pazienti>> insertDiagnosis(@PathVariable("idPaziente") Integer idPaziente, @RequestBody String diagnosi){
        System.out.println("inserimento diagnosi in corso");
        Optional<Pazienti> p=patientsRepository.findByIdMedicoAndIdPaziente(DoctorServiceApplication.sessionId,idPaziente);
        if(p.isPresent()){
            p.get().setDiagnosi(diagnosi);
            patientsRepository.save(p.get());
            return new ResponseEntity<>(p, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping(path="/editDiagnosis/{idPaziente}", consumes={"application/JSON"}, produces={"application/JSON"})
    public @ResponseBody ResponseEntity<Optional<Pazienti>> editDiagnosis(@PathVariable("idPaziente") Integer idPaziente, @RequestBody String diagnosi) {
        System.out.println("modifica diagnosi in corso");
        Optional<Pazienti> p=patientsRepository.findByIdMedicoAndIdPaziente(DoctorServiceApplication.sessionId,idPaziente);
        if(p.isPresent() && p.get().getDiagnosi()!=null){
            p.get().setDiagnosi(diagnosi);
            patientsRepository.save(p.get());
            return new ResponseEntity<>(p, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping(path="/deleteDiagnosis/{idPaziente}")
    public @ResponseBody ResponseEntity<HttpStatus>  deleteDiagnosis(@PathVariable("idPaziente") Integer idPaziente){
        System.out.println("rimozione diagnosi in corso");
        Optional<Pazienti> p=patientsRepository.findByIdMedicoAndIdPaziente(DoctorServiceApplication.sessionId,idPaziente);
        if(p.isPresent() && p.get().getDiagnosi()!=null){
            p.get().setDiagnosi(null);
            p.get().setCura(null);
            patientsRepository.save(p.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PostMapping(path="/insertCure/{idPaziente}", consumes={"application/JSON"}, produces={"application/JSON"})
    public @ResponseBody ResponseEntity<Optional<Pazienti>> insertCure(@PathVariable("idPaziente") Integer idPaziente, @RequestBody String cura){
        System.out.println("inserimento cura in corso");
        Optional<Pazienti> p=patientsRepository.findByIdMedicoAndIdPaziente(DoctorServiceApplication.sessionId,idPaziente);
        if(p.isPresent() && p.get().getDiagnosi()!=null){
            p.get().setCura(cura);
            patientsRepository.save(p.get());
            return new ResponseEntity<>(p, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @PutMapping(path="/editCure/{idPaziente}", consumes={"application/JSON"}, produces={"application/JSON"})
    public @ResponseBody ResponseEntity<Optional<Pazienti>> editCure(@PathVariable("idPaziente") Integer idPaziente, @RequestBody String cura) {
        System.out.println("modifica cura in corso");
        Optional<Pazienti> p=patientsRepository.findByIdMedicoAndIdPaziente(DoctorServiceApplication.sessionId,idPaziente);
        if(p.isPresent() && p.get().getCura()!=null){
            p.get().setCura(cura);
            patientsRepository.save(p.get());
            return new ResponseEntity<>(p, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping(path="/deleteCure/{idPaziente}")
    public @ResponseBody ResponseEntity<HttpStatus>  deleteCure(@PathVariable("idPaziente") Integer idPaziente){
        System.out.println("rimozione cura in corso");
        Optional<Pazienti> p=patientsRepository.findByIdMedicoAndIdPaziente(DoctorServiceApplication.sessionId,idPaziente);
        if(p.isPresent() && p.get().getCura()!=null){
            p.get().setCura(null);
            patientsRepository.save(p.get());
            return new ResponseEntity<>(HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @Timed(value="timeToHospitalize", description="time spent to hospitalize a patient (transaction SAGA)")
    @PutMapping(path="/ricovera/{idPaziente}", consumes={"application/JSON"}, produces={"application/JSON"})
    public @ResponseBody ResponseEntity<Optional<Pazienti>>  ricovera(@PathVariable("idPaziente") Integer idPaziente) throws InterruptedException {
        Optional<Pazienti> p=patientsRepository.findByIdMedicoAndIdPaziente(DoctorServiceApplication.sessionId,idPaziente);
        if(p.isPresent() && p.get().getRicovero()==false){
            p.get().setRicovero(true);
            patientsRepository.save(p.get());
            kafkaTemplate.send(ricoveriTopic, "Ricovero|"+ idPaziente+"|"+DoctorServiceApplication.sessionId);
            System.out.println("sto andando in wait...");
            while (this.eventoTerminato==false){
                System.out.println("attendo fine evento");
            }
            System.out.println("attesa terminata");
            if(this.successoRicovero==true){
                this.eventoTerminato=false;
                return new ResponseEntity<>(p,HttpStatus.OK);
            }
            else{
                this.eventoTerminato=false;
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }


    @PutMapping(path="/dimetti/{idPaziente}", consumes={"application/JSON"}, produces={"application/JSON"})
    public @ResponseBody ResponseEntity<Optional<Pazienti>>  dimetti(@PathVariable("idPaziente") Integer idPaziente) {
        Optional<Pazienti> p=patientsRepository.findByIdMedicoAndIdPaziente(DoctorServiceApplication.sessionId,idPaziente);
        if(p.isPresent() && p.get().getRicovero()==true){
            p.get().setRicovero(false);
            patientsRepository.save(p.get());
            kafkaTemplate.send(ricoveriTopic, "Dimissione|"+ idPaziente+"|"+DoctorServiceApplication.sessionId);
            return new ResponseEntity<>(p,HttpStatus.OK);

        } else return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }



    @Override
    public void onApplicationEvent(EventoRicovero e){

            System.out.println("Evento partito");
            if (e.getSuccessoRicovero() == true) {
                this.successoRicovero = true;
            } else this.successoRicovero = false;
            System.out.println("esito successo " + this.successoRicovero );
            this.eventoTerminato=true;


    }

}


