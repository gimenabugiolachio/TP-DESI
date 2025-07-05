package com.desi.repository;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import com.desi.model.Familia;

public interface FamiliaRepository extends JpaRepository<Familia, Long> {
	 List<Familia> findByActivoTrue();
	 List<Familia> findByActivoFalse();
	 List<Familia> findByNombreFamiliaContainingIgnoreCase(String nombre);
	 List<Familia> findByNombreFamiliaContainingIgnoreCaseOrId(String nombre, Long id);
}
