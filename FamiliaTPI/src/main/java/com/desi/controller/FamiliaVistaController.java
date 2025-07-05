package com.desi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.desi.dto.FamiliaDTO;
import com.desi.model.Familia;
import com.desi.service.FamiliaService;

import org.springframework.validation.annotation.Validated;
import org.springframework.ui.ModelMap;

import java.util.List;

@Controller
@RequestMapping("/familias-vista")
public class FamiliaVistaController {

    @Autowired
    private FamiliaService familiaService;

    @GetMapping("/nueva")
    public String mostrarFormularioFamilia(Model model) {
        model.addAttribute("familia", new FamiliaDTO());
        return "formulario-familia";
    }

    @GetMapping("/familias-inactivas")
    public String listarInactivas(Model model) {
        List<Familia> familias = familiaService.listarInactivas();
        List<FamiliaDTO> dtos = familias.stream().map(f -> {
            FamiliaDTO dto = new FamiliaDTO();
            dto.setNombreFamilia(f.getNombreFamilia());
            dto.setDireccion(f.getDireccion());
            dto.setFechaAlta(f.getFechaAlta());
            dto.generarFechaFormateada();
            dto.setId(f.getId());
            dto.setActivo(f.isActivo());
            return dto;
        }).toList();

        model.addAttribute("familias", dtos);
        return "familias-inactivas";
    }

    @GetMapping("/formulario-familia")
    public String mostrarFormulario(Model model) {
        model.addAttribute("familia", new FamiliaDTO());
        return "formulario";
    }

    @PostMapping("/guardar")
    public String guardarFamilia(
            @ModelAttribute("familia") @Validated FamiliaDTO familiaDTO,
            BindingResult result,
            ModelMap model,
            @RequestParam(required = false) String action
    ) {
        if ("cancelar".equals(action)) {
            return "redirect:/";
        }

        System.out.println("Nombre recibido: '" + familiaDTO.getNombreFamilia() + "'");

        if (result.hasErrors()) {
            System.out.println("Errores de validación:");
            result.getAllErrors().forEach(error -> System.out.println(error.toString()));
            return "formulario-familia";
        }

        Familia familia = familiaDTO.toEntity();
        familiaService.guardar(familia);
        return "redirect:/familias-vista/lista-familia";
    }

    @GetMapping("/editar/{id}")
    public String editarFamilia(@PathVariable Long id, Model model) {
        Familia familia = familiaService.buscarPorId(id);
        FamiliaDTO dto = new FamiliaDTO();
        dto.setId(familia.getId());
        dto.setNombreFamilia(familia.getNombreFamilia());
        dto.setDireccion(familia.getDireccion());
        dto.setFechaAlta(familia.getFechaAlta());

        model.addAttribute("familia", dto);
        model.addAttribute("id", id);
        return "formulario-familia";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarFamilia(
            @PathVariable Long id,
            @ModelAttribute("familia") @Validated FamiliaDTO familiaDTO,
            BindingResult result,
            ModelMap model,
            @RequestParam(required = false) String action
    ) {
        if ("cancelar".equals(action)) {
            return "redirect:/familias-vista/lista-familia";
        }

        if (result.hasErrors()) {
            System.out.println("Errores de validación (actualizar):");
            result.getAllErrors().forEach(error -> System.out.println(error.toString()));
            model.addAttribute("id", id);
            return "formulario-familia";
        }

        Familia actualizada = familiaDTO.toEntity();
        actualizada.setId(id);
        familiaService.actualizarFamilia(id, actualizada);

        return "redirect:/familias-vista/lista-familia";
    }

    @PostMapping("/eliminar/{id}")
    public String eliminarFamilia(@PathVariable Long id) {
        familiaService.eliminarFamilia(id);
        return "redirect:/familias-vista/lista-familia";
    }

    @GetMapping("/lista-familia")
    public String listar(@RequestParam(required = false) String filtroNombre,
                         @RequestParam(required = false) Long filtroId,
                         Model model) {

        List<Familia> familias;

        if ((filtroNombre != null && !filtroNombre.isBlank()) || filtroId != null) {
            familias = familiaService.buscarPorNombreOId(filtroNombre, filtroId);
        } else {
            familias = familiaService.listarActivas();
        }

        List<FamiliaDTO> dtos = familias.stream().map(f -> {
            FamiliaDTO dto = new FamiliaDTO();
            dto.setNombreFamilia(f.getNombreFamilia());
            dto.setDireccion(f.getDireccion());
            dto.setFechaAlta(f.getFechaAlta());
            dto.generarFechaFormateada();
            dto.setId(f.getId());
            dto.setActivo(f.isActivo());

            List<String> nombresActivos = f.getIntegrantes().stream()
                .filter(integrante -> integrante.isActivo())
                .map(integrante -> integrante.getNombre() + " " + integrante.getApellido())
                .toList();

            System.out.println("Familia ID " + f.getId() + " - Integrantes activos: " + nombresActivos);

            dto.setNombresIntegrantesActivos(nombresActivos);

            return dto;
        }).toList();


        model.addAttribute("familias", dtos);
        model.addAttribute("filtroNombre", filtroNombre);
        model.addAttribute("filtroId", filtroId);
        return "lista-familias";
    }
}
