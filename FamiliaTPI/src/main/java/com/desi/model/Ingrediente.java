package com.desi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ingrediente {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(unique = true, nullable = false)
	    private String nombre;

	    private boolean activo = true;
	    
	    @Column(nullable = false)
	    private Double calorias;

	    // Getters y Setters
	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }

	    public String getNombre() { return nombre; }
	    public void setNombre(String nombre) { this.nombre = nombre; }

	    public boolean isActivo() { return activo; }
	    public void setActivo(boolean activo) { this.activo = activo; }
	    
	    public Double getCalorias() {
	        return calorias;
	    }
	    public void setCalorias(Double calorias) {
	        this.calorias = calorias;
	    }
}
