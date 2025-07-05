package com.desi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desi.model.Ingrediente;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long>{
	   List<Ingrediente> findByActivoTrue();
}
