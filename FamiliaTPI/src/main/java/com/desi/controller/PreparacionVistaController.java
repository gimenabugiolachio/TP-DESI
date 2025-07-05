package com.desi.controller;

import com.desi.dto.PreparacionDTO;
import com.desi.service.PreparacionService;
import com.desi.service.RecetaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/preparaciones")
public class PreparacionVistaController {

    @Autowired
    private PreparacionService preparacionService;

    @Autowired
    private RecetaService recetaService;

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("preparaciones", preparacionService.listarActivas());
        return "lista-preparaciones";
    }

    @GetMapping("/nuevo")
    public String nueva(Model model) {
        model.addAttribute("preparacion", new PreparacionDTO());
        model.addAttribute("recetas", recetaService.listarActivas());
        return "formulario-preparacion";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("preparacion") PreparacionDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("recetas", recetaService.listarActivas());
            return "formulario-preparacion";
        }
        preparacionService.guardarDesdeVista(dto);
        return "redirect:/preparaciones";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        PreparacionDTO dto = preparacionService.buscarDTOPorId(id);
        model.addAttribute("preparacion", dto);
        model.addAttribute("recetas", recetaService.listarActivas());
        return "formulario-preparacion";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizar(@PathVariable Long id, @Valid @ModelAttribute("preparacion") PreparacionDTO dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("recetas", recetaService.listarActivas());
            return "formulario-preparacion";
        }
        preparacionService.actualizarDesdeVista(id, dto);
        return "redirect:/preparaciones";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        preparacionService.eliminar(id);
        return "redirect:/preparaciones";
    }
}
