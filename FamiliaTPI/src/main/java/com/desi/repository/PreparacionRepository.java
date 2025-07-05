package com.desi.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.desi.model.Preparacion;

public interface PreparacionRepository extends JpaRepository<Preparacion, Long> {
	  List<Preparacion> findByActivoTrue();
	    List<Preparacion> findByActivoFalse();
}
