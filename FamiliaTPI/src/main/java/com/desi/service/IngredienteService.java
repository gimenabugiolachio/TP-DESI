package com.desi.service;

import com.desi.model.Ingrediente;
import java.util.List;

public interface IngredienteService {
	 List<Ingrediente> listarActivos();
	  List<Ingrediente> obtenerTodos();
	    Ingrediente guardar(Ingrediente ingrediente);
	    void eliminar(Long id);
	    Ingrediente obtenerPorId(Long id);
	    
}
