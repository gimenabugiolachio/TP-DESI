package com.desi.service;

import java.util.List;

import com.desi.model.Integrante;

public interface IntegranteService {
	 Integrante crearIntegrante(Integrante integrante);
	    List<Integrante> listarActivos();
	    Integrante buscarPorId(Long id);
	    Integrante actualizarIntegrante(Long id, Integrante integrante);
	    void eliminarIntegrante(Long id);
	    void guardar(Integrante integrante);
	    Integrante actualizar(Long id, Integrante integrante);
	    void eliminar(Long id);
	    List<Integrante> listarInactivos();
}
