package com.desi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.desi.model.Receta;

public interface RecetaRepository extends JpaRepository<Receta, Long>{
	List<Receta> findByActivoTrue();
	List<Receta> findByActivoFalse();

	@Query("SELECT r FROM Receta r WHERE r.activo = true AND (:nombre IS NULL OR LOWER(r.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
	List<Receta> buscarConFiltros(@Param("nombre") String nombre);
	  Optional<Receta> findByNombre(String nombre);
      List<Receta> findByNombreContainingIgnoreCaseAndActivoTrue(String nombre);
      boolean existsByNombreIgnoreCase(String nombre);
      boolean existsByNombre(String nombre);

    	}

