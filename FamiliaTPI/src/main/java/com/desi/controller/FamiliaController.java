package com.desi.controller;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desi.dto.FamiliaDTO;
import com.desi.model.Familia;
import com.desi.repository.FamiliaRepository;

@RestController
@RequestMapping("/api/familias")
public class FamiliaController {
	   private final FamiliaRepository familiaRepository;

	    public FamiliaController(FamiliaRepository familiaRepository) {
	        this.familiaRepository = familiaRepository;
	    }

	    @PostMapping
	    public ResponseEntity<Familia> crearFamilia(@RequestBody @Valid FamiliaDTO form) {
	        Familia nueva = form.toEntity();
	        Familia guardada = familiaRepository.save(nueva);
	        return ResponseEntity.ok(guardada);
	    }

	    @GetMapping
	    public List<Familia> listarFamilias() {
	        return familiaRepository.findByActivoTrue();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Familia> obtenerPorId(@PathVariable Long id) {
	        Optional<Familia> familia = familiaRepository.findById(id);
	        return familia.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<Familia> actualizar(@PathVariable Long id, @RequestBody @Valid FamiliaDTO form) {
	        return familiaRepository.findById(id).map(familia -> {
	            familia.setNombreFamilia(form.getNombreFamilia());
	            familia.setDireccion(form.getDireccion());
	            familia.setFechaAlta(form.getFechaAlta());
	            return ResponseEntity.ok(familiaRepository.save(familia));
	        }).orElse(ResponseEntity.notFound().build());
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
	        Optional<Familia> familia = familiaRepository.findById(id);
	        if (familia.isPresent()) {
	            Familia f = familia.get();
	            f.setActivo(false);
	            familiaRepository.save(f);
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
}

