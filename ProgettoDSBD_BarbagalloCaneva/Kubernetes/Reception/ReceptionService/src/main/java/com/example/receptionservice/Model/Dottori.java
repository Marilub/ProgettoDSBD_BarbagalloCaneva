package com.example.receptionservice.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Dottori {
    @Id
    private Integer idMedico;

    @NotNull(message = "name cannot be null")
    private String nome;

    @NotNull(message = "surname cannot be null")
    private String cognome;

    public Integer getIdMedico() {
        return idMedico;
    }

    public Dottori setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Dottori setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getCognome() {
        return cognome;
    }

    public Dottori setCognome(String cognome) {
        this.cognome = cognome;
        return this;
    }
}
