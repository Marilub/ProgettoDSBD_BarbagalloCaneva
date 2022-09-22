package com.example.reglogservice.Controller;

import com.example.reglogservice.Model.Users;
import com.example.reglogservice.Model.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;



@Controller
@RequestMapping(path="/registration")
public class RegistrationController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value="${KAFKA_MAIN_TOPIC}")
    private String maintopic;


    @PostMapping(path="/", consumes={"application/JSON"}, produces={"application/JSON"})
    public @ResponseBody Users register(@RequestBody Users user){
        System.out.println("Registrazione partita");
        Users u= usersRepository.save(user);
        System.out.println("UserRegistered|" + u.getId()+"|"+ u.getType()+"|"+u.getName()+"|"+u.getSurname());


        if(user.getType().equals("r")) {
            String nomeLogged=user.getName()+" "+user.getSurname();
            System.out.println("Type registration  r");
            kafkaTemplate.send(maintopic, "UserRegistered|" + u.getId().toString()+"|"+ u.getType()+"|"+nomeLogged);
        }
        else if(user.getType().equals("d")){
            System.out.println("Type registration d");
            kafkaTemplate.send(maintopic, "UserRegistered|" + u.getId().toString()+"|"+ u.getType()+"|"+u.getName()+"|"+u.getSurname());

        }
        return u;
    }


    @GetMapping(path="/verificaUsername/{username}")
    public @ResponseBody String verifica(@PathVariable("username") String username){
        System.out.println("verifica partita");
        if(usersRepository.findByUsername(username).isPresent()) {
            return "Presente";
        } else return "Non presente";
    }
}
