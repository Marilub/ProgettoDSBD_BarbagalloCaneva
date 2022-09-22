package com.example.doctorservice.Model;

import java.io.Serializable;
import java.util.Objects;

public class PazienteId implements Serializable {
    private Integer idPaziente;
    private Integer idMedico;

    public PazienteId() {

    }

    public PazienteId(Integer idPaziente, Integer idMedico) {
        this.idPaziente = idPaziente;
        this.idMedico = idMedico;
    }

    public Integer getIdPaziente() {
        return idPaziente;
    }

    public PazienteId setIdPaziente(Integer idPaziente) {
        this.idPaziente = idPaziente;
        return this;
    }

    public Integer getIdMedico() {
        return idMedico;
    }

    public PazienteId setIdMedico(Integer idMedico) {
        this.idMedico = idMedico;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PazienteId paziente = (PazienteId) o;
        return idPaziente==paziente.idPaziente &&
                idMedico==paziente.idMedico;
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPaziente, idMedico);
    }

}
