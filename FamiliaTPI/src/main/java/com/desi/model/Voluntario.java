package com.desi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Voluntario extends Persona {

    @Id
    private Integer dni;
    private String nroSeguimiento;

    public String getNroSeguimiento() { return nroSeguimiento; }
    public void setNroSeguimiento(String nroSeguimiento) {
        this.nroSeguimiento = nroSeguimiento;
    }
}
