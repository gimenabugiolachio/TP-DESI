package com.desi.model;

import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
public class Preparacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Receta receta;

    private String descripcion;
    private LocalDate fechaCoccion;
    private Integer totalRacionesPreparadas;
    private Integer stockRacionesRestantes;
    private boolean activo = true;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Receta getReceta() { return receta; }
    public void setReceta(Receta receta) { this.receta = receta; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaCoccion() { return fechaCoccion; }
    public void setFechaCoccion(LocalDate fechaCoccion) { this.fechaCoccion = fechaCoccion; }

    public Integer getTotalRacionesPreparadas() { return totalRacionesPreparadas; }
    public void setTotalRacionesPreparadas(Integer totalRacionesPreparadas) { this.totalRacionesPreparadas = totalRacionesPreparadas; }

    public Integer getStockRacionesRestantes() { return stockRacionesRestantes; }
    public void setStockRacionesRestantes(Integer stockRacionesRestantes) { this.stockRacionesRestantes = stockRacionesRestantes; }

    public boolean isActivo() { return activo; }
    public void setActivo(boolean activo) { this.activo = activo; }
}
