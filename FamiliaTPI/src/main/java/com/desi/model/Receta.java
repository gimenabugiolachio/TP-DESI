package com.desi.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Receta {
	  @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(unique = true, nullable = false)
	    private String nombre;

	    @Column(length = 2000)
	    private String descripcion;

	    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<RecetaIngredientes> ingredientes;

	    private boolean activo = true;

	    // Getters y Setters
	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }
	    
	    public String getNombre() { return nombre; }
	    public void setNombre(String nombre) { this.nombre = nombre; }

	    public String getDescripcion() { return descripcion; }
	    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

	    public List<RecetaIngredientes> getIngredientes() { return ingredientes; }
	    public void setIngredientes(List<RecetaIngredientes> ingredientes) { this.ingredientes = ingredientes; }

	    public boolean isActivo() { return activo; }
	    public void setActivo(boolean activo) { this.activo = activo; }
	    
	    @Transient
	    public Double getCaloriasTotales() {
	        if (ingredientes == null || ingredientes.isEmpty()) return 0.0;

	        return ingredientes.stream()
	            .filter(ri -> ri.getCalorias() != null)
	            .mapToDouble(ri -> ri.getCalorias())
	            .sum();
	    }
}
