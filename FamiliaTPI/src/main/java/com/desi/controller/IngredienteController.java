package com.desi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desi.dto.IngredienteDTO;
import com.desi.model.Ingrediente;
import com.desi.service.IngredienteService;

@RestController
@RequestMapping("/api/ingredientes")
public class IngredienteController {
	 @Autowired
	    private IngredienteService ingredienteService;

	    @GetMapping
	    public List<IngredienteDTO> listar() {
	        return ingredienteService.obtenerTodos().stream()
	                .map(IngredienteDTO::fromEntity)
	                .collect(Collectors.toList());
	    }

	    @PostMapping
	    public IngredienteDTO crear(@RequestBody IngredienteDTO dto) {
	        Ingrediente ingrediente = ingredienteService.guardar(dto.toEntity());
	        return IngredienteDTO.fromEntity(ingrediente);
	    }

	    @PutMapping("/{id}")
	    public IngredienteDTO actualizar(@PathVariable Long id, @RequestBody IngredienteDTO dto) {
	        dto.setId(id);
	        Ingrediente actualizado = ingredienteService.guardar(dto.toEntity());
	        return IngredienteDTO.fromEntity(actualizado);
	    }

	    @DeleteMapping("/{id}")
	    public void eliminar(@PathVariable Long id) {
	        ingredienteService.eliminar(id);
	    }
}
