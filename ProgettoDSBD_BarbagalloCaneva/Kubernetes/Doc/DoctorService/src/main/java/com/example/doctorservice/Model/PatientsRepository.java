package com.example.doctorservice.Model;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface PatientsRepository extends CrudRepository<Pazienti,Integer> {
    public List<Pazienti> findByNomeAndCognomeAndIdMedico(String nome, String cognome, Integer idMedico);
    public Optional<Pazienti> findByIdMedicoAndIdPaziente(Integer idMedico,Integer idPaziente);

}
