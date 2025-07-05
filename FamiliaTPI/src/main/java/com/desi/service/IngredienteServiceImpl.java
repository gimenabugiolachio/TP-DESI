package com.desi.service;

	import com.desi.model.Ingrediente;
	import com.desi.repository.IngredienteRepository;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.stereotype.Service;

	import java.util.List;

	@Service
	public class IngredienteServiceImpl implements IngredienteService {
	    @Autowired
	    private IngredienteRepository ingredienteRepository;

	    @Override
	    public List<Ingrediente> listarActivos() {
	        return ingredienteRepository.findByActivoTrue();
	    }
	    
	    @Override
	    public List<Ingrediente> obtenerTodos() {
	        return ingredienteRepository.findByActivoTrue();
	    }

	    @Override
	    public Ingrediente guardar(Ingrediente ingrediente) {
	        ingrediente.setActivo(true);
	        return ingredienteRepository.save(ingrediente);
	    }

	    @Override
	    public void eliminar(Long id) {
	        ingredienteRepository.findById(id).ifPresent(ing -> {
	            ing.setActivo(false);
	            ingredienteRepository.save(ing);
	        });
	    }

	    @Override
	    public Ingrediente obtenerPorId(Long id) {
	        return ingredienteRepository.findById(id).orElse(null);
	    }
	}
