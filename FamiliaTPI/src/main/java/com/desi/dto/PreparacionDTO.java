package com.desi.dto;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class PreparacionDTO {

    private Long id;

    @NotNull(message = "Debe seleccionar una receta")
    private Long recetaId;

    @NotBlank(message = "Debe ingresar una descripción")
    private String descripcion;

    @NotNull(message = "Debe indicar la fecha de cocción")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaCoccion;

    @NotNull(message = "Debe indicar la cantidad de raciones")
    @Min(value = 1, message = "Debe ser al menos 1")
    private Integer totalRacionesPreparadas;

    @NotNull(message = "Debe indicar el stock restante")
    @Min(value = 0, message = "No puede ser negativo")
    private Integer stockRacionesRestantes;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getRecetaId() { return recetaId; }
    public void setRecetaId(Long recetaId) { this.recetaId = recetaId; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public LocalDate getFechaCoccion() { return fechaCoccion; }
    public void setFechaCoccion(LocalDate fechaCoccion) { this.fechaCoccion = fechaCoccion; }

    public Integer getTotalRacionesPreparadas() { return totalRacionesPreparadas; }
    public void setTotalRacionesPreparadas(Integer totalRacionesPreparadas) { this.totalRacionesPreparadas = totalRacionesPreparadas; }

    public Integer getStockRacionesRestantes() { return stockRacionesRestantes; }
    public void setStockRacionesRestantes(Integer stockRacionesRestantes) { this.stockRacionesRestantes = stockRacionesRestantes; }

    public static PreparacionDTO fromEntity(com.desi.model.Preparacion p) {
        PreparacionDTO dto = new PreparacionDTO();
        dto.setId(p.getId());
        dto.setDescripcion(p.getDescripcion());
        dto.setFechaCoccion(p.getFechaCoccion());
        dto.setTotalRacionesPreparadas(p.getTotalRacionesPreparadas());
        dto.setStockRacionesRestantes(p.getStockRacionesRestantes());
        dto.setRecetaId(p.getReceta().getId());
        return dto;
    }
}
