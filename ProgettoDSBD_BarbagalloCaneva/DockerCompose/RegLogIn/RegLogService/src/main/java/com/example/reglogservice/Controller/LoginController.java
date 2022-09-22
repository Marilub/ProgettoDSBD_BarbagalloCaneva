package com.example.reglogservice.Controller;

import com.example.reglogservice.Model.Users;
import com.example.reglogservice.Model.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping(path="/login")
public class LoginController {

    @Autowired
    UsersRepository usersRepository;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Value(value="${KAFKA_MAIN_TOPIC}")
    private String maintopic;

   
    @GetMapping(path="/access/{username}/{pass}")
    public @ResponseBody ResponseEntity<Optional<Users>> login(@PathVariable("username") String username, @PathVariable("pass") String pass){
        System.out.println("credenziali: "+username+" "+pass);
        Optional<Users> user=usersRepository.findByUsernameAndPass(username,pass);

        if (user.isPresent()) {
            String nomeLogged=user.get().getName()+" "+user.get().getSurname();
            kafkaTemplate.send(maintopic, "UserLogged|"+ user.get().getId().toString()+"|"+ user.get().getType() + "|"+nomeLogged);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


}
