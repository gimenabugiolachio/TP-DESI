package com.desi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desi.model.Integrante;

public interface IntegranteRepository extends JpaRepository<Integrante, Long> {
	 List<Integrante> findByActivoTrue();
	 List<Integrante> findByActivoFalse();
	 long countByFamiliaIdAndActivoTrue(Long familiaId);
}
