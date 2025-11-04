package com.smartlogi.controller;

import com.smartlogi.dto.colisproduit.ColisProduitRequestDTO;
import com.smartlogi.dto.colisproduit.ColisProduitResponseDTO;
import com.smartlogi.model.ColisProduitId;
import com.smartlogi.service.ColisProduitService;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colis-produits")
@Hidden
public class ColisProduitController {

    private final ColisProduitService colisProduitService;

    public ColisProduitController(ColisProduitService colisProduitService) {
        this.colisProduitService = colisProduitService;
    }

    @GetMapping
    public List<ColisProduitResponseDTO> getAll() {
        return colisProduitService.findAll();
    }

    @GetMapping("/colis/{colisId}/produit/{produitId}")
    public ColisProduitResponseDTO getById(@PathVariable String colisId, @PathVariable String produitId) {
        return colisProduitService.findById(new ColisProduitId(colisId, produitId));
    }

    @PostMapping
    public ColisProduitResponseDTO create(@Valid @RequestBody ColisProduitRequestDTO request) {
        return colisProduitService.save(request);
    }

    @DeleteMapping("/colis/{colisId}/produit/{produitId}")
    public void delete(@PathVariable String colisId, @PathVariable String produitId) {
        colisProduitService.delete(new ColisProduitId(colisId, produitId));
    }
}
