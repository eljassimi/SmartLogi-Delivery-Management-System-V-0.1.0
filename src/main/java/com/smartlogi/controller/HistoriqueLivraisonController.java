package com.smartlogi.controller;

import com.smartlogi.dto.historiquelivraison.HistoriqueLivraisonRequestDTO;
import com.smartlogi.dto.historiquelivraison.HistoriqueLivraisonResponseDTO;
import com.smartlogi.service.HistoriqueLivraisonService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/historiques")
public class HistoriqueLivraisonController {

    private final HistoriqueLivraisonService service;

    public HistoriqueLivraisonController(HistoriqueLivraisonService service) {
        this.service = service;
    }

    @GetMapping
    public List<HistoriqueLivraisonResponseDTO> getAll() {
        return service.findAll();
    }

    @PostMapping
    public HistoriqueLivraisonResponseDTO create(@Valid @RequestBody HistoriqueLivraisonRequestDTO request) {
        return service.save(request);
    }
}
