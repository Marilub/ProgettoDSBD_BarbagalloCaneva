package com.example.receptionservice.Controller;


import com.example.receptionservice.Model.DoctorsRepository;
import com.example.receptionservice.Model.Dottori;
import com.example.receptionservice.Model.PatientsRepository;
import com.example.receptionservice.Model.Pazienti;
import com.example.receptionservice.ReceptionServiceApplication;
import com.google.gson.Gson;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(path="/reception")
public class ReceptionController {

    @Autowired
    PatientsRepository patientsRepository;

    @Autowired
    DoctorsRepository doctorsRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value="${KAFKA_PAZIENTE_TOPIC}")
    private String insertPazienteTopic;

    @Timed(value="counterInsertPatient", description="number of insert patient requests")
    @PostMapping(path="/insertPaziente/{idMedico}", consumes={"application/JSON"}, produces={"application/JSON"})
    public @ResponseBody ResponseEntity<Optional<Pazienti>> insertPaziente(@PathVariable("idMedico") Integer idMedico, @RequestBody Pazienti paziente){
        System.out.println("inserimento paziente in corso");
        Optional<Pazienti> p=patientsRepository.findByEmail(paziente.getEmail());
        String Message=new String();
        if(p.isPresent()) {
            Message = "Paziente|"+ paziente.getNome()+"|"+paziente.getCognome()+"|"+paziente.getEta()+"|"+paziente.getGenere()+"|"+p.get().getIdPaziente().toString()+"|"+idMedico.toString();
        }

        if(!p.isPresent()){
            Pazienti np=patientsRepository.save(paziente);
            Message= "Paziente|"+ paziente.getNome()+"|"+paziente.getCognome()+"|"+paziente.getEta()+"|"+paziente.getGenere()+"|"+np.getIdPaziente().toString()+"|"+idMedico.toString();
        }
        System.out.println("Message: "+Message);
        kafkaTemplate.send(insertPazienteTopic, Message);
        return new ResponseEntity<>(p, HttpStatus.OK);
    }


    @GetMapping(path="/listaMedici", produces={"application/JSON"})
    public @ResponseBody ResponseEntity<String> listaMedici(){
        Gson gson= new Gson();
        List<Dottori> dottori= new ArrayList<Dottori>();
        dottori= (List<Dottori>) doctorsRepository.findAll();
        if(dottori.size()>0) {
            return new ResponseEntity<>(gson.toJson(dottori), HttpStatus.OK);
        } else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    @GetMapping(path="/receptionistName")
    public @ResponseBody String nameOfLogged(){
        return ReceptionServiceApplication.nameLogged;

    }

}
