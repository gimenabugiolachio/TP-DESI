package com.desi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.desi.model.Ingrediente;
import com.desi.service.IngredienteService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredientes-vista")
public class IngredienteVistaController {
    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("ingrediente", new Ingrediente());
        return "formulario-ingrediente"; 
    }

    @PostMapping("/guardar")
    public String guardarIngrediente(@ModelAttribute("ingrediente") @Valid Ingrediente ingrediente,
                                     BindingResult result,
                                     Model model) {
        if (result.hasErrors()) {
            return "formulario-ingrediente";
        }

        ingredienteService.guardar(ingrediente);
        return "redirect:/ingredientes";
    }
    
    @GetMapping
    public String listarIngredientes(Model model) {
        model.addAttribute("ingredientes", ingredienteService.obtenerTodos());
        return "lista-ingredientes"; 
    }
}
