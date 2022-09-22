package com.example.receptionservice.Model;

import com.example.receptionservice.Model.Pazienti;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PatientsRepository extends CrudRepository<Pazienti,Integer> {
    public Optional<Pazienti> findByEmail(String email);
    public Integer countByRicovero(Boolean ricovero);
}
