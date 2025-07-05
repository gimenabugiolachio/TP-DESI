package com.desi.service;

import java.util.List;

import com.desi.dto.RecetaDTO;
import com.desi.model.Receta;

public interface RecetaService {
	Receta guardar(Receta receta);
	Receta actualizar(Long id, RecetaDTO recetaDTO);
    void eliminar(Long id);
    Receta buscarPorId(Long id);
    List<Receta> listarActivas();
    List<Receta> buscarPorNombreOCalorias(String nombre, Integer minCalorias, Integer maxCalorias);
    boolean existeNombre(String nombre);
    List<Receta> listarInactivas();

}