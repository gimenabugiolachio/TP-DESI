package com.desi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desi.dto.PreparacionDTO;
import com.desi.model.Preparacion;
import com.desi.model.Receta;
import com.desi.repository.PreparacionRepository;
import com.desi.repository.RecetaRepository;

@Service
public class PreparacionServiceImpl implements PreparacionService {

    @Autowired
    private PreparacionRepository preparacionRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    @Override
    public Preparacion guardarDesdeVista(PreparacionDTO dto) {
        Receta receta = recetaRepository.findById(dto.getRecetaId())
            .orElseThrow(() -> new IllegalArgumentException("Receta no encontrada con ID: " + dto.getRecetaId()));

        Preparacion preparacion = new Preparacion();
        preparacion.setReceta(receta);
        preparacion.setDescripcion(dto.getDescripcion());
        preparacion.setFechaCoccion(dto.getFechaCoccion());
        preparacion.setTotalRacionesPreparadas(dto.getTotalRacionesPreparadas());
        preparacion.setStockRacionesRestantes(dto.getStockRacionesRestantes());
        preparacion.setActivo(true);

        return preparacionRepository.save(preparacion);
    }

    @Override
    public Preparacion actualizarDesdeVista(Long id, PreparacionDTO dto) {
        Preparacion existente = preparacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Preparación no encontrada"));

        Receta receta = recetaRepository.findById(dto.getRecetaId())
            .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        existente.setDescripcion(dto.getDescripcion());
        existente.setFechaCoccion(dto.getFechaCoccion());
        existente.setTotalRacionesPreparadas(dto.getTotalRacionesPreparadas());
        existente.setStockRacionesRestantes(dto.getStockRacionesRestantes());
        existente.setReceta(receta);

        return preparacionRepository.save(existente);
    }

    @Override
    public List<Preparacion> listarActivas() {
        return preparacionRepository.findByActivoTrue();
    }

    @Override
    public List<Preparacion> listarInactivas() {
        return preparacionRepository.findByActivoFalse();
    }

    @Override
    public Preparacion buscarPorId(Long id) {
        return preparacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Preparación no encontrada"));
    }

    @Override
    public void eliminar(Long id) {
        Preparacion preparacion = buscarPorId(id);
        preparacion.setActivo(false);
        preparacionRepository.save(preparacion);
    }

    @Override
    public PreparacionDTO buscarDTOPorId(Long id) {
        Preparacion preparacion = preparacionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Preparación no encontrada"));

        return PreparacionDTO.fromEntity(preparacion);
    }
}
