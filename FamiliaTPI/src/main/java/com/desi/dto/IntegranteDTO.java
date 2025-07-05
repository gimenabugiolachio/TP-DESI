package com.desi.dto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.format.annotation.DateTimeFormat;

import com.desi.model.Familia;
import com.desi.model.Integrante;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class IntegranteDTO {
	@NotBlank(message = "El DNI no puede estar vacío")
    @Pattern(regexp = "\\d{7,8}", message = "El DNI debe tener entre 7 y 8 dígitos")
    private String dni;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 30, message = "El nombre debe tener entre 2 y 30 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido no puede estar vacío")
    @Size(min = 2, max = 30, message = "El apellido debe tener entre 2 y 30 caracteres")
    private String apellido;

    @NotBlank(message = "Debe seleccionar una ocupación")
    private String ocupacion;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser pasada")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    @NotNull(message = "Debe seleccionar una familia")
    private Long familiaId;
    private Long id;
    private boolean activo = true;

    private String fechaNacimientoFormateada;

    public void generarFechaFormateada() {
        if (fechaNacimiento != null) {
            this.fechaNacimientoFormateada = fechaNacimiento.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }
    }

    public String getFechaNacimientoFormateada() {
        return fechaNacimientoFormateada;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Long getFamiliaId() {
        return familiaId;
    }

    public void setFamiliaId(Long familiaId) {
        this.familiaId = familiaId;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public Integrante toEntity(Familia familia) {
    	Integrante integrante = new Integrante();
        integrante.setId(this.id); 
        integrante.setDni(this.dni);
        integrante.setNombre(this.nombre);
        integrante.setApellido(this.apellido);
        integrante.setOcupacion(this.ocupacion);
        integrante.setFechaNacimiento(this.fechaNacimiento);
        integrante.setFamilia(familia);
        integrante.setActivo(this.activo);
        return integrante;
    }
}
