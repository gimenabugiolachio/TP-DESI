package com.desi.service;

import java.util.List;

import com.desi.model.Familia;

public interface FamiliaService {
	Familia crearFamilia(Familia familia);
    List<Familia> listarFamilias();
    Familia buscarPorId(Long id);
    Familia actualizarFamilia(Long id, Familia familia);
    void eliminarFamilia(Long id);
    void guardar(Familia familia);
    List<Familia> listarActivas(); 
    List<Familia> listarInactivas();
    List<com.desi.dto.FamiliaDTO> listarFiltrado(String filtroNombre, Long filtroId);
    List<Familia> buscarPorNombreOId(String nombre, Long id);
}
