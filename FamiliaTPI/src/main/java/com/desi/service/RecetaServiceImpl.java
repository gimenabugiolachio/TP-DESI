package com.desi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desi.dto.RecetaDTO;
import com.desi.dto.RecetaIngredientesDTO;
import com.desi.model.Ingrediente;
import com.desi.model.Receta;
import com.desi.model.RecetaIngredientes;
import com.desi.repository.IngredienteRepository;
import com.desi.repository.RecetaRepository;

@Service
public class RecetaServiceImpl implements RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;
    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Override
    public Receta guardar(Receta receta) {
        if (receta.getId() == null && recetaRepository.existsByNombreIgnoreCase(receta.getNombre())) {
            throw new IllegalArgumentException("Ya existe una receta con ese nombre");
        }

        if (receta.getIngredientes() != null) {
            for (RecetaIngredientes ri : receta.getIngredientes()) {
                Ingrediente ingredienteCompleto = ingredienteRepository.findById(ri.getIngrediente().getId())
                        .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));
                ri.setIngrediente(ingredienteCompleto);
                ri.setReceta(receta);
            }
        }
            
        return recetaRepository.save(receta);
    }

    @Override
    public Receta actualizar(Long id, RecetaDTO recetaDTO) {
        Receta recetaExistente = recetaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        recetaExistente.setDescripcion(recetaDTO.getDescripcion());
        recetaExistente.setNombre(recetaDTO.getNombre());
        recetaExistente.getIngredientes().clear();
  
        for (RecetaIngredientesDTO dto : recetaDTO.getIngredientes()) {
            RecetaIngredientes ri = new RecetaIngredientes();
            Ingrediente ingrediente = ingredienteRepository.findById(dto.getIngredienteId())
                    .orElseThrow(() -> new RuntimeException("Ingrediente no encontrado"));
            ri.setIngrediente(ingrediente);
            ri.setCantidadKg(dto.getCantidadKg());
            ri.setCalorias(dto.getCalorias());
            ri.setReceta(recetaExistente);
            ri.setActivo(true);

            recetaExistente.getIngredientes().add(ri);
        }

        return recetaRepository.save(recetaExistente);
    }


    @Override
    public void eliminar(Long id) {
        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));
        receta.setActivo(false);
        recetaRepository.save(receta);
    }

    @Override
    public Receta buscarPorId(Long id) {
        return recetaRepository.findById(id).orElse(null);
    }

    @Override
    public List<Receta> listarActivas() {
        return recetaRepository.findByActivoTrue();
    }

    @Override
    public List<Receta> buscarPorNombreOCalorias(String nombre, Integer minCalorias, Integer maxCalorias) {
        List<Receta> recetas = recetaRepository.buscarConFiltros(nombre);
        return recetas.stream()
            .filter(r -> (minCalorias == null || r.getCaloriasTotales() >= minCalorias)
                      && (maxCalorias == null || r.getCaloriasTotales() <= maxCalorias))
            .collect(Collectors.toList());
    }

    @Override
    public boolean existeNombre(String nombre) {
        return recetaRepository.existsByNombreIgnoreCase(nombre);
    }

    @Override
    public List<Receta> listarInactivas() {
        return recetaRepository.findByActivoFalse();
    }
}
