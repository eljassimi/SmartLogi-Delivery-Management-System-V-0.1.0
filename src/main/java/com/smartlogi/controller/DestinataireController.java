package com.smartlogi.controller;

import com.smartlogi.dto.destinataire.DestinataireRequestDTO;
import com.smartlogi.dto.destinataire.DestinataireResponseDTO;
import com.smartlogi.service.DestinataireService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/destinataires")
public class DestinataireController {

    private final DestinataireService destinataireService;

    public DestinataireController(DestinataireService destinataireService) {
        this.destinataireService = destinataireService;
    }

    @GetMapping
    public List<DestinataireResponseDTO> getAll() {
        return destinataireService.findAll();
    }

    @GetMapping("/{id}")
    public DestinataireResponseDTO getById(@PathVariable String id) {
        return destinataireService.findById(id);
    }

    @PostMapping
    public DestinataireResponseDTO create(@Valid @RequestBody DestinataireRequestDTO request) {
        return destinataireService.save(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        destinataireService.delete(id);
    }
}
