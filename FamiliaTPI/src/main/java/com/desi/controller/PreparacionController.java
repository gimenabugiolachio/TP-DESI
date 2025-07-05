package com.desi.controller;

import com.desi.dto.PreparacionDTO;
import com.desi.model.Preparacion;
import com.desi.service.PreparacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/preparaciones")
public class PreparacionController {

    @Autowired
    private PreparacionService preparacionService;

    @GetMapping
    public List<Preparacion> listar() {
        return preparacionService.listarActivas();
    }

    @PostMapping
    public Preparacion guardar(@RequestBody PreparacionDTO dto) {
        return preparacionService.guardarDesdeVista(dto);
    }

    @PutMapping("/{id}")
    public Preparacion actualizar(@PathVariable Long id, @RequestBody PreparacionDTO dto) {
        return preparacionService.actualizarDesdeVista(id, dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        preparacionService.eliminar(id);
    }
}
