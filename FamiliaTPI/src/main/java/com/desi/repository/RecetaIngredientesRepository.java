package com.desi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desi.model.RecetaIngredientes;

public interface RecetaIngredientesRepository extends JpaRepository<RecetaIngredientes, Long> {
	 List<RecetaIngredientes> findByRecetaIdAndActivoTrue(Long recetaId);

	    List<RecetaIngredientes> findByRecetaId(Long recetaId);
}
