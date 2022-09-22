package com.example.reglogservice.Model;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<Users,Integer> {
    public Optional<Users> findByUsernameAndPass(String username,String pass);
    public Optional<Users> findByUsername(String username);
}
