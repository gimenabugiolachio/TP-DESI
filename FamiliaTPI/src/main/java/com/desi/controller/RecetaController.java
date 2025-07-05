package com.desi.controller;

import com.desi.dto.RecetaDTO;
import com.desi.model.Receta;
import com.desi.service.IngredienteService;
import com.desi.service.RecetaService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/recetas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @Autowired
    private IngredienteService ingredienteService;

    @GetMapping
    public String listarRecetas(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Integer caloriasMin,
            @RequestParam(required = false) Integer caloriasMax,
            Model model
    ) {
    	List<Receta> recetas = recetaService.buscarPorNombreOCalorias(nombre, caloriasMin, caloriasMax); 
        model.addAttribute("recetas", recetas);
        model.addAttribute("filtroNombre", nombre);
        model.addAttribute("filtroMin", caloriasMin);
        model.addAttribute("filtroMax", caloriasMax);
        return "lista-recetas";
    }

    @GetMapping("/nueva")
    public String nuevaReceta(Model model) {
        model.addAttribute("receta", new RecetaDTO());
        model.addAttribute("ingredientesDisponibles", ingredienteService.listarActivos());
        return "formulario-receta";
    }

    @PostMapping("/guardar")
    public String guardarReceta(@ModelAttribute("receta") @Valid RecetaDTO recetaDTO,
                                BindingResult result,
                                Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ingredientesDisponibles", ingredienteService.listarActivos());
            return "formulario-receta";
        }

        recetaService.guardar(recetaDTO.toEntity());
        return "redirect:/recetas";
    }

    @GetMapping("/editar/{id}")
    public String editarReceta(@PathVariable Long id, Model model) {
        Receta receta = recetaService.buscarPorId(id);
        model.addAttribute("receta", RecetaDTO.fromEntity(receta));
        model.addAttribute("ingredientesDisponibles", ingredienteService.listarActivos());
        return "formulario-receta";
    }

 
    @PostMapping("/actualizar/{id}")
    public String actualizarReceta(@PathVariable Long id,
                                   @ModelAttribute("receta") @Valid RecetaDTO recetaDTO,
                                   BindingResult result,
                                   Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ingredientesDisponibles", ingredienteService.listarActivos());
            return "formulario-receta";
        }

        recetaService.actualizar(id, recetaDTO);
        return "redirect:/recetas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarReceta(@PathVariable Long id) {
        recetaService.eliminar(id);
        return "redirect:/recetas";
    }
}
