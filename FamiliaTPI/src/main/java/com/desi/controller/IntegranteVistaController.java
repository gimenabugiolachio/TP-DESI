package com.desi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.desi.dto.IntegranteDTO;
import com.desi.model.Familia;
import com.desi.model.Integrante;
import com.desi.service.FamiliaService;
import com.desi.service.IntegranteService;

import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequestMapping("/integrantes-vista")
public class IntegranteVistaController {

    @Autowired
    private IntegranteService integranteService;

    @Autowired
    private FamiliaService familiaService;

    @GetMapping("/nuevo")
    public String mostrarFormularioIntegrante(Model model, RedirectAttributes redirectAttributes) {
        List<Familia> familias = familiaService.listarActivas();

        if (familias.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Debe crear al menos una familia antes de registrar un integrante.");
            return "redirect:/familias-vista/lista-familia"; 
        }

        model.addAttribute("integrante", new IntegranteDTO());
        model.addAttribute("familias", familias);
        return "formulario-integrante";
    }


    @PostMapping("/guardar")
    public String guardarIntegrante(
            @ModelAttribute("integrante") @Valid IntegranteDTO dto,
            BindingResult result,
            Model model,
            @RequestParam(required = false) String action) {

        if ("cancelar".equals(action)) {
            return "redirect:/inicio";
        }

        if (result.hasErrors()) {
            model.addAttribute("familias", familiaService.listarActivas());
            return "formulario-integrante";
        }

        Familia familia = familiaService.buscarPorId(dto.getFamiliaId());
        if (familia == null) {
            result.rejectValue("familiaId", "error.familia", "La familia seleccionada no existe.");
            model.addAttribute("familias", familiaService.listarActivas());
            return "formulario-integrante";
        }

        Integrante integrante = dto.toEntity(familia);
        integranteService.guardar(integrante);

        return "redirect:/integrantes-vista/lista-integrantes";
    }


    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Integrante integrante = integranteService.buscarPorId(id);
        IntegranteDTO dto = new IntegranteDTO();

        dto.setId(integrante.getId());
        dto.setDni(integrante.getDni());
        dto.setNombre(integrante.getNombre());
        dto.setApellido(integrante.getApellido());
        dto.setOcupacion(integrante.getOcupacion());
        dto.setFechaNacimiento(integrante.getFechaNacimiento());
        dto.setFamiliaId(integrante.getFamilia().getId());
        dto.setActivo(integrante.isActivo());

        model.addAttribute("integrante", dto);
        model.addAttribute("familias", familiaService.listarActivas());
        return "formulario-integrante";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarIntegrante(@PathVariable Long id,
                                       @ModelAttribute("integrante") @Valid IntegranteDTO dto,
                                       BindingResult result,
                                       Model model,
                                       @RequestParam(required = false) String action) {

        if ("cancelar".equals(action)) {
            return "redirect:/integrantes-vista/lista-integrantes";
        }

        if (result.hasErrors()) {
            model.addAttribute("familias", familiaService.listarActivas());
            return "formulario-integrante";
        }

        Familia familia = familiaService.buscarPorId(dto.getFamiliaId());
        Integrante actualizado = dto.toEntity(familia);
        actualizado.setId(id);
        integranteService.actualizar(id, actualizado);
        return "redirect:/integrantes-vista/lista-integrantes";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarIntegrante(@PathVariable Long id) {
        Integrante integrante = integranteService.buscarPorId(id);
        if (integrante != null) {
            integrante.setActivo(false); 
            integranteService.guardar(integrante); 
        }
        return "redirect:/integrantes-vista/lista-integrantes";
    }
    @GetMapping("/lista-integrantes")
    public String listarIntegrantes(Model model) {
    	 List<Integrante> integrantes = integranteService.listarActivos();
    	    model.addAttribute("integrantes", integrantes);
    	    return "lista-integrantes";
    }
    
    @GetMapping("/inactivos")
    public String listarIntegrantesInactivos(Model model) {
        List<Integrante> integrantes = integranteService.listarInactivos();
        model.addAttribute("integrantes", integrantes);
        return "integrantes-inactivos";
    }
}
