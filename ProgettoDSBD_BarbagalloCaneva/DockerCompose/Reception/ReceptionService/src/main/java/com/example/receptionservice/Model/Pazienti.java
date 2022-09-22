package com.example.receptionservice.Model;


import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Pazienti {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idPaziente;

    @NotNull(message = "name cannot be null")
    private String nome;

    @NotNull(message = "surname cannot be null")
    private String cognome;

    @NotNull(message = "Age cannot be null")
    private Integer eta;

    @NotNull(message = "Date cannot be null")
    private String dataNascita;

    @NotNull(message = "Place cannot be null")
    private String luogoNascita;


    @NotNull(message = "Gender cannot be null")
    private String genere;

    @Column(unique = true)
    @NotNull(message = "Email cannot be null")
    private String email;

    private Boolean ricovero = false;

    public String getDataNascita() {
        return dataNascita;
    }

    public Pazienti setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
        return this;
    }

    public String getLuogoNascita() {
        return luogoNascita;
    }

    public Pazienti setLuogoNascita(String luogoNascita) {
        this.luogoNascita = luogoNascita;
        return this;
    }

    public Integer getIdPaziente() {
        return idPaziente;
    }

    public Pazienti setIdPaziente(Integer idPaziente) {
        this.idPaziente = idPaziente;
        return this;
    }

      public String getNome() {
        return nome;
    }

    public Pazienti setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getCognome() {
        return cognome;
    }

    public Pazienti setCognome(String cognome) {
        this.cognome = cognome;
        return this;
    }

    public Integer getEta() {
        return eta;
    }

    public Pazienti setEta(Integer eta) {
        this.eta = eta;
        return this;
    }

    public String getGenere() {
        return genere;
    }

    public Pazienti setGenere(String genere) {
        this.genere = genere;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Pazienti setEmail(String email) {
        this.email = email;
        return this;
    }

    public Boolean getRicovero() {
        return ricovero;
    }

    public Pazienti setRicovero(Boolean ricovero) {
        this.ricovero = ricovero;
        return this;
    }
}