package com.desi.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.desi.model.Ingrediente;
import com.desi.model.Receta;
import com.desi.model.RecetaIngredientes;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class RecetaDTO {
    private Long id;

    @NotBlank(message = "El nombre no puede estar vacío")
    private String nombre;

    @Size(max = 2000, message = "La descripción es demasiado larga")
    private String descripcion;

    @Valid
    @NotEmpty(message = "Debe agregar al menos un ingrediente")
    private List<RecetaIngredientesDTO> ingredientes = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public List<RecetaIngredientesDTO> getIngredientes() { return ingredientes; }
    public void setIngredientes(List<RecetaIngredientesDTO> ingredientes) { this.ingredientes = ingredientes; }

    public Receta toEntity() {
        Receta receta = new Receta();
        receta.setId(this.id);
        receta.setNombre(this.nombre);
        receta.setDescripcion(this.descripcion);
        receta.setActivo(true);

        if (ingredientes != null && !ingredientes.isEmpty()) {
            List<RecetaIngredientes> lista = ingredientes.stream()
                .filter(dto ->
                    dto.getIngredienteId() != null &&
                    dto.getCantidadKg() != null && dto.getCantidadKg() > 0
                )
                .map(dto -> {
                    RecetaIngredientes ri = new RecetaIngredientes();
                    Ingrediente ing = new Ingrediente();
                    ing.setId(dto.getIngredienteId());

                    ri.setIngrediente(ing);
                    ri.setCantidadKg(dto.getCantidadKg());
                    ri.setCalorias(dto.getCalorias()); // 👈 Se carga lo que ponga el usuario
                    ri.setActivo(true);
                    ri.setReceta(receta);

                    return ri;
                })
                .collect(Collectors.toList());

            receta.setIngredientes(lista);
        }

        return receta;
    }

    public static RecetaDTO fromEntity(Receta receta) {
        RecetaDTO dto = new RecetaDTO();
        dto.setId(receta.getId());
        dto.setNombre(receta.getNombre());
        dto.setDescripcion(receta.getDescripcion());

        if (receta.getIngredientes() != null) {
            dto.setIngredientes(
                receta.getIngredientes().stream()
                    .filter(RecetaIngredientes::isActivo)
                    .map(ri -> {
                        RecetaIngredientesDTO ing = new RecetaIngredientesDTO();
                        ing.setIngredienteId(ri.getIngrediente().getId());
                        ing.setCantidadKg(ri.getCantidadKg());
                        ing.setCalorias(ri.getCalorias()); 
                        return ing;
                    })

                    .collect(Collectors.toList())
            );
        }

        return dto;
    }
}
