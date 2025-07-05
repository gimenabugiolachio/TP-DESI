package com.desi.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RecursoNoEncontradoException.class)
	public String manejarRecursoNoEncontrado(RecursoNoEncontradoException ex, Model model) {
	    model.addAttribute("errorMessage", ex.getMessage());
	    return "error";
	}

	//@ExceptionHandler(Exception.class)
	//public String manejarErroresGenerales(Exception ex, Model model) {
//	    model.addAttribute("errorMessage", "Ocurrió un error inesperado: " + ex.getMessage());
//	    return "error";
	//}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
	public String manejarErrorDeIntegridad(DataIntegrityViolationException ex, Model model) {
	    String mensaje = ex.getMostSpecificCause().getMessage();

	    if (mensaje != null && mensaje.contains("receta.nombre")) {
	        model.addAttribute("errorMessage", "Ya existe una receta con ese nombre.");
	    } else if (mensaje != null && mensaje.contains("calorias")) {
	        model.addAttribute("errorMessage", "El ingrediente no tiene definidas las calorías.");
	    } else {
	        model.addAttribute("errorMessage", "Error de integridad: " + mensaje);
	    }

	    return "error";
	}	    
}
	


