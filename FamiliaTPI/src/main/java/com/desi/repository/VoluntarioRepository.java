package com.desi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.desi.model.Voluntario;

public interface VoluntarioRepository extends JpaRepository<Voluntario, Integer> {
    // Ejemplo extra si querés buscar por número de seguimiento:
    // Optional<Voluntario> findByNroSeguimiento(String nroSeguimiento);
}
