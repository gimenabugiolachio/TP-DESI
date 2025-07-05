package com.desi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.desi.model.Asistido;

public interface AsistidoRepository extends JpaRepository<Asistido, Integer> {
    // Podés agregar métodos como estos si necesitás:
    // List<Asistido> findByFamiliaId(Long familiaId);
    // List<Asistido> findByNombreContainingIgnoreCase(String nombre);
}
