package com.desi.model;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Familia {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "nombre_familia")
    private String nombreFamilia;

    private String direccion;
    @Column(name = "fecha_alta")
    private LocalDate fechaAlta = LocalDate.now(); 
    @Column(nullable = false)
    private boolean activo = true;

    @OneToMany(mappedBy = "familia", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Integrante> integrantes = new ArrayList<>();

    @Column(name = "fecha_ultima_asistencia")
    private LocalDate fechaUltimaAsistencia;
    

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreFamilia() {
        return nombreFamilia;
    }

    public void setNombreFamilia(String nombreFamilia) {
        this.nombreFamilia = nombreFamilia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public List<Integrante> getIntegrantes() {
        return integrantes;
    }

    public void setIntegrantes(List<Integrante> integrantes) {
        this.integrantes = integrantes;
    }
    public LocalDate getFechaUltimaAsistencia() {
        return fechaUltimaAsistencia;
    }

    public void setFechaUltimaAsistencia(LocalDate fechaUltimaAsistencia) {
        this.fechaUltimaAsistencia = fechaUltimaAsistencia;
    }

}
