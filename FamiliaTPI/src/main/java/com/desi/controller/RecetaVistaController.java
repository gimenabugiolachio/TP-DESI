package com.desi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.desi.dto.RecetaDTO;
import com.desi.dto.RecetaIngredientesDTO;
import com.desi.model.Receta;
import com.desi.service.IngredienteService;
import com.desi.service.RecetaService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/recetas-vista")
public class RecetaVistaController {
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

    @GetMapping("/nuevo") 
    public String nuevaReceta(Model model) {
        RecetaDTO recetaDTO = new RecetaDTO();
        recetaDTO.getIngredientes().add(new RecetaIngredientesDTO());

        model.addAttribute("receta", recetaDTO);
        model.addAttribute("ingredientesDisponibles", ingredienteService.listarActivos());
        return "formulario-receta";
    }
    
    @PostMapping("/guardar")
    public String guardarReceta(@ModelAttribute("receta") @Valid RecetaDTO dto,
                                BindingResult result,
                                Model model) {

        model.addAttribute("ingredientesDisponibles", ingredienteService.listarActivos());

        if (result.hasErrors()) {
            return "formulario-receta";
        }

        boolean yaExiste = recetaService.existeNombre(dto.getNombre());

        if (dto.getId() != null) {
            recetaService.actualizar(dto.getId(), dto);
        } else {
            if (yaExiste) {
                model.addAttribute("errorMensaje", "Ya existe una receta con ese nombre.");
                return "formulario-receta";
            }
            recetaService.guardar(dto.toEntity());
        }

        return "redirect:/recetas-vista";
    }
    @GetMapping("/editar/{id}")
    public String editarReceta(@PathVariable Long id, Model model) {
        Receta receta = recetaService.buscarPorId(id);
        RecetaDTO dto = RecetaDTO.fromEntity(receta);

        if (dto.getIngredientes() == null || dto.getIngredientes().isEmpty()) {
            dto.getIngredientes().add(new com.desi.dto.RecetaIngredientesDTO());
        }

        model.addAttribute("receta", dto);
        model.addAttribute("ingredientesDisponibles", ingredienteService.listarActivos());
        return "formulario-receta";
    }
    
    @GetMapping("/recetas-vista/inactivas")
    public String verInactivas(Model model) {
        List<Receta> recetasInactivas = recetaService.listarInactivas(); 
        model.addAttribute("recetasInactivas", recetasInactivas);
        return "recetas-inactivas";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarReceta(@PathVariable Long id) {
        recetaService.eliminar(id);
        return "redirect:/recetas-vista";
    }
    
    @PostMapping("/actualizar/{id}")
    public String actualizarReceta(@PathVariable Long id,
                                   @ModelAttribute("receta") @Valid RecetaDTO recetaDTO,
                                   BindingResult result,
                                   Model model) {
        model.addAttribute("ingredientesDisponibles", ingredienteService.listarActivos());

        if (result.hasErrors()) {
            return "formulario-receta";
        }

        recetaService.actualizar(id, recetaDTO);
        return "redirect:/recetas-vista";
    }
}
