package com.desi.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.desi.model.Familia;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class FamiliaDTO {
	private Long id;
	@NotBlank(message = "El nombre de la familia no puede estar vacío")
	@Size(min = 3, max = 30, message = "Debe tener entre 3 y 30 caracteres")
	@Pattern(regexp = "^[A-Za-zÁÉÍÓÚÑáéíóúñüÜ ]+$", message = "Solo se permiten letras y espacios")
    private String nombreFamilia;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Size(min = 5, max = 50, message = "Debe tener entre 5 y 50 caracteres")
    private String direccion;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd") 
    @PastOrPresent(message = "La fecha no puede ser futura")
    private LocalDate fechaAlta;
    private boolean activo;
    private String fechaFormateada;
    private LocalDate fechaUltimaAsistencia;
    private Integer cantidadIntegrantes;
    private int cantidadIntegrantesActivos;
    private List<String> nombresIntegrantesActivos;
  
    public FamiliaDTO() {
        this.fechaAlta = LocalDate.now();
    }

    public String getNombreFamilia() {
        return nombreFamilia;
    }
    
	public Long getId() {
	    return id;
	}

	public void setId(Long id) {
	    this.id = id;
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
    
    public LocalDate getFechaUltimaAsistencia() {
        return fechaUltimaAsistencia;
    }

    public void setFechaUltimaAsistencia(LocalDate fechaUltimaAsistencia) {
        this.fechaUltimaAsistencia = fechaUltimaAsistencia;
    }
    
    public Integer getCantidadIntegrantes() {
        return cantidadIntegrantes;
    }

    public void setCantidadIntegrantes(Integer cantidadIntegrantes) {
        this.cantidadIntegrantes = cantidadIntegrantes;
    }


    public void generarFechaFormateada() {
        if (fechaAlta != null) {
            this.fechaFormateada = fechaAlta.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
    }

    public String getFechaFormateada() {
        return fechaFormateada;
    }
    
    public int getCantidadIntegrantesActivos() {
		return cantidadIntegrantesActivos;
	}

	public void setCantidadIntegrantesActivos(int cantidadIntegrantesActivos) {
		this.cantidadIntegrantesActivos = cantidadIntegrantesActivos;
	}
	
	 public List<String> getNombresIntegrantesActivos() {
	        return nombresIntegrantesActivos;
	    }

	    public void setNombresIntegrantesActivos(List<String> nombresIntegrantesActivos) {
	        this.nombresIntegrantesActivos = nombresIntegrantesActivos;
	    }

	public Familia toEntity() {
        Familia familia = new Familia();
        familia.setId(this.id); 
        familia.setNombreFamilia(this.nombreFamilia);
        familia.setDireccion(this.direccion);
        familia.setFechaAlta(this.fechaAlta);
        familia.setActivo(true); 
        return familia;
    }
}
