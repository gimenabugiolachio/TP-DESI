package com.desi.service;

import java.util.List;
import com.desi.dto.PreparacionDTO;
import com.desi.model.Preparacion;

public interface PreparacionService {

    Preparacion guardarDesdeVista(PreparacionDTO dto);

    Preparacion actualizarDesdeVista(Long id, PreparacionDTO dto);

    List<Preparacion> listarActivas();

    List<Preparacion> listarInactivas();

    Preparacion buscarPorId(Long id);

    void eliminar(Long id);

    PreparacionDTO buscarDTOPorId(Long id);
}
