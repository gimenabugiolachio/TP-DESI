package com.desi.service;

import com.desi.dto.FamiliaDTO;
import com.desi.model.Familia;
import com.desi.repository.FamiliaRepository;
import com.desi.repository.IntegranteRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FamiliaServiceImpl implements FamiliaService {

    @Autowired
    private FamiliaRepository familiaRepository;

    @Autowired
    private IntegranteRepository integranteRepository;

    @Override
    public void guardar(Familia familia) {
        familiaRepository.save(familia);
    }

    @Override
    public void eliminarFamilia(Long id) {
        Familia familia = buscarPorId(id);
        familia.setActivo(false);
        familiaRepository.save(familia);
    }

    @Override
    public Familia actualizarFamilia(Long id, Familia actualizada) {
        Familia original = buscarPorId(id);
        original.setNombreFamilia(actualizada.getNombreFamilia());
        original.setDireccion(actualizada.getDireccion());
        original.setFechaAlta(actualizada.getFechaAlta());
        original.setFechaUltimaAsistencia(actualizada.getFechaUltimaAsistencia());
        return familiaRepository.save(original);
    }


    @Override
    public Familia buscarPorId(Long id) {
        return familiaRepository.findById(id).orElseThrow(() -> new RuntimeException("Familia no encontrada"));
    }

    @Override
    public List<Familia> listarInactivas() {
        return familiaRepository.findByActivoFalse();
    }

    @Override
    public List<Familia> listarActivas() {
        return familiaRepository.findByActivoTrue();
    }

    @Override
    public List<FamiliaDTO> listarFiltrado(String filtroNombre, Long filtroId) {
        List<Familia> familias = familiaRepository.findByActivoTrue();

        return familias.stream()
                .filter(f -> {
                    boolean coincideNombre = filtroNombre == null || filtroNombre.isBlank()
                            || f.getNombreFamilia().toLowerCase().contains(filtroNombre.toLowerCase());

                    boolean coincideId = filtroId == null || f.getId().equals(filtroId);

                    return coincideNombre && coincideId;
                })
                .map(f -> {
                    FamiliaDTO dto = new FamiliaDTO();
                    dto.setId(f.getId());
                    dto.setNombreFamilia(f.getNombreFamilia());
                    dto.setFechaAlta(f.getFechaAlta());
                    dto.setFechaUltimaAsistencia(f.getFechaUltimaAsistencia());
                    dto.setCantidadIntegrantes((int) integranteRepository.countByFamiliaIdAndActivoTrue(f.getId()));
                    return dto;
                })
                .toList();
    }
    
    @Override
    public Familia crearFamilia(Familia familia) {
        return familiaRepository.save(familia);
    }

    @Override
    public List<Familia> listarFamilias() {
        return familiaRepository.findAll();
    }
    
    public List<Familia> buscarPorNombreOId(String nombre, Long id) {
        if (nombre != null && !nombre.isBlank() && id != null) {
            return familiaRepository.findByNombreFamiliaContainingIgnoreCaseOrId(nombre, id);
        } else if (nombre != null && !nombre.isBlank()) {
            return familiaRepository.findByNombreFamiliaContainingIgnoreCase(nombre);
        } else if (id != null) {
            return familiaRepository.findById(id).map(List::of).orElse(List.of());
        } else {
            return listarActivas();
        }
    }

}
