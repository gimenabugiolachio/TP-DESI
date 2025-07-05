package com.desi.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class RecetaIngredientesDTO {
	   @NotNull(message = "Debe seleccionar un ingrediente")
	    private Long ingredienteId;

	    @NotNull(message = "Debe indicar una cantidad")
	    @Positive(message = "La cantidad debe ser mayor que cero")
	    private Double cantidadKg;

	    @NotNull(message = "Debe indicar las calorías")
	    @Positive(message = "Las calorías deben ser mayores que cero")
	    @Max(value = 10000, message = "Las calorías no pueden superar las 10.000")
	    private Integer calorias;
	    
	    public Long getIngredienteId() {
	        return ingredienteId;
	    }

	    public void setIngredienteId(Long ingredienteId) {
	        this.ingredienteId = ingredienteId;
	    }

	    public Double getCantidadKg() {
	        return cantidadKg;
	    }

	    public void setCantidadKg(Double cantidadKg) {
	        this.cantidadKg = cantidadKg;
	    }

		public Integer getCalorias() {
			return calorias;
		}

		public void setCalorias(Integer calorias) {
			this.calorias = calorias;
		}
}
