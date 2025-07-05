package com.desi.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.desi.dto.IntegranteDTO;
import com.desi.model.Familia;
import com.desi.model.Integrante;
import com.desi.repository.FamiliaRepository;
import com.desi.repository.IntegranteRepository;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/integrantes")
public class IntegranteController {
	 private final IntegranteRepository integranteRepository;
	    private final FamiliaRepository familiaRepository;

	    public IntegranteController(IntegranteRepository integranteRepository, FamiliaRepository familiaRepository) {
	        this.integranteRepository = integranteRepository;
	        this.familiaRepository = familiaRepository;
	    }

	    @PostMapping
	    public ResponseEntity<?> crearIntegrante(@RequestBody @Valid IntegranteDTO dto) {
	        Optional<Familia> familiaOptional = familiaRepository.findById(dto.getFamiliaId());
	        if (familiaOptional.isPresent()) {
	            Integrante nuevo = dto.toEntity(familiaOptional.get());
	            return ResponseEntity.ok(integranteRepository.save(nuevo));
	        } else {
	            return ResponseEntity.badRequest().body("Familia no encontrada con ID: " + dto.getFamiliaId());
	        }
	    }

	    @GetMapping
	    public List<Integrante> listar() {
	        return integranteRepository.findAll();
	    }

	    @GetMapping("/{id}")
	    public ResponseEntity<Integrante> obtenerPorId(@PathVariable Long id) {
	        return integranteRepository.findById(id)
	                .map(ResponseEntity::ok)
	                .orElse(ResponseEntity.notFound().build());
	    }

	    @PutMapping("/{id}")
	    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @Valid IntegranteDTO dto) {
	        Optional<Integrante> integranteOptional = integranteRepository.findById(id);
	        Optional<Familia> familiaOptional = familiaRepository.findById(dto.getFamiliaId());

	        if (integranteOptional.isPresent() && familiaOptional.isPresent()) {
	            Integrante integrante = integranteOptional.get();
	            integrante.setNombre(dto.getNombre());
	            integrante.setApellido(dto.getApellido());
	            integrante.setFamilia(familiaOptional.get());
	            return ResponseEntity.ok(integranteRepository.save(integrante));
	        } else {
	            return ResponseEntity.badRequest().body("Integrante o familia no encontrados.");
	        }
	    }

	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
	        if (integranteRepository.existsById(id)) {
	            integranteRepository.deleteById(id);
	            return ResponseEntity.noContent().build();
	        } else {
	            return ResponseEntity.notFound().build();
	        }
    }
}
