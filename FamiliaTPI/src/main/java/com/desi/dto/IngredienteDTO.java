package com.desi.dto;

import com.desi.model.Ingrediente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class IngredienteDTO {
	 private Long id;

	    @NotBlank(message = "El nombre no puede estar vacío")
	    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
	    private String nombre;

	    // Getters y Setters
	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }

	    public String getNombre() { return nombre; }
	    public void setNombre(String nombre) { this.nombre = nombre; }

	   
	    public Ingrediente toEntity() {
	    	Ingrediente ingrediente = new Ingrediente();
	    	ingrediente.setNombre(this.nombre);
	        ingrediente.setId(this.id);
	        ingrediente.setNombre(this.nombre);
	        ingrediente.setActivo(true);
	        return ingrediente;
	    }
	    
	    public static IngredienteDTO fromEntity(Ingrediente ingrediente) {
	        IngredienteDTO dto = new IngredienteDTO();
	        dto.setId(ingrediente.getId());
	        dto.setNombre(ingrediente.getNombre());
	        return dto;
	    }
}
