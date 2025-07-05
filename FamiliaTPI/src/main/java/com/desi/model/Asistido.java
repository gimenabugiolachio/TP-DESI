package com.desi.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Asistido extends Persona {

    @Id
    private Integer dni; 

    private LocalDate fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "familia_id")
    private Familia familia;

    // Getters y setters
    public LocalDate getFechaRegistro() { return fechaRegistro; }
    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Familia getFamilia() { return familia; }
    public void setFamilia(Familia familia) { this.familia = familia; }
}
