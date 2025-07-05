package com.desi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desi.exception.RecursoNoEncontradoException;
import com.desi.model.Integrante;
import com.desi.repository.IntegranteRepository;
@Service
public class IntegranteServiceImpl implements IntegranteService{
	@Autowired
    private IntegranteRepository integranteRepository;
	
	@Autowired
	private FamiliaService familiaService;

    @Override
    public void guardar(Integrante integrante) {
        integranteRepository.save(integrante);
    }
	 
    @Override
    public Integrante crearIntegrante(Integrante integrante) {
        if (integrante.getFamilia() == null || integrante.getFamilia().getId() == null) {
            throw new RuntimeException("Debe asignarse una familia al integrante.");
        }

       
        var familia = familiaService.buscarPorId(integrante.getFamilia().getId());
        integrante.setFamilia(familia);
        integrante.setActivo(true); 
        return integranteRepository.save(integrante);
    }

	    @Override
	    public List<Integrante> listarActivos() {
	        return integranteRepository.findByActivoTrue();
	    }

	    @Override
	    public Integrante buscarPorId(Long id) {
	        return integranteRepository.findById(id).orElse(null);
	    }

	    @Override
	    public Integrante actualizarIntegrante(Long id, Integrante actualizado) {
	        Integrante existente = buscarPorId(id);
	        if (existente != null) {
	            existente.setNombre(actualizado.getNombre());
	            existente.setApellido(actualizado.getApellido());
	            existente.setDni(actualizado.getDni());
	            existente.setFechaNacimiento(actualizado.getFechaNacimiento());
	            existente.setOcupacion(actualizado.getOcupacion());
	            existente.setFamilia(actualizado.getFamilia());
	            existente.setActivo(actualizado.isActivo());
	            return integranteRepository.save(existente);
	        }
	        return null;
	    }

	    @Override
	    public void eliminarIntegrante(Long id) {
	        Integrante integrante = buscarPorId(id);
	        if (integrante != null) {
	            integrante.setActivo(false);
	            integranteRepository.save(integrante);
	        }
	    }
	    @Override
	    public Integrante actualizar(Long id, Integrante integrante) {
	        Integrante existente = integranteRepository.findById(id).orElseThrow(() -> new RuntimeException("Integrante no encontrado"));
	        
	        existente.setNombre(integrante.getNombre());
	        existente.setApellido(integrante.getApellido());
	        existente.setDni(integrante.getDni());
	        existente.setFechaNacimiento(integrante.getFechaNacimiento());
	        existente.setOcupacion(integrante.getOcupacion());
	        existente.setFamilia(integrante.getFamilia());
	        
	        return integranteRepository.save(existente);
}
	    @Override
	    public void eliminar(Long id) {
	        Integrante integrante = integranteRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Integrante no encontrado"));
	        integrante.setActivo(false); 
	        integranteRepository.save(integrante);
	    }
	    
	    public void eliminarLogicamente(Long id) {
	        Integrante integrante = integranteRepository.findById(id)
	            .orElseThrow(() -> new RecursoNoEncontradoException("Integrante no encontrado"));
	        integrante.setActivo(false);
	        integranteRepository.save(integrante);
	    }
	    
	    @Override
	    public List<Integrante> listarInactivos() {
	        return integranteRepository.findByActivoFalse();
	    }
}
