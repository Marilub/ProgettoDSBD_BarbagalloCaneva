package com.example.doctorservice.Model;


import net.bytebuddy.implementation.bind.annotation.Default;
import reactor.core.publisher.Mono;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@IdClass(PazienteId.class)
public class Pazienti {
    @Id
    private Integer idPaziente;
    @Id
    private Integer idMedico;

    @NotNull(message="name cannot be null")
    private String nome;

    @NotNull(message="surname cannot be null")
    private String cognome;

    @NotNull(message="Age cannot be null")
    private Integer eta;

    @NotNull(message="Gender cannot be null")
    private String genere;


    private String diagnosi;

    private String cura;

    private Boolean ricovero= false;




    public Integer getIdPaziente() {
        return idPaziente;
    }

    public Pazienti setIdPaziente(Integer idPaziente) {
        this.idPaziente = idPaziente;
        return this;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public Pazienti setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
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

    public String getDiagnosi() {
        return diagnosi;
    }

    public Pazienti setDiagnosi(String diagnosi) {
        this.diagnosi = diagnosi;
        return this;
    }

    public String getCura() {
        return cura;
    }

    public Pazienti setCura(String cura) {
        this.cura = cura;
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
