package com.smartlogi.controller;

import com.smartlogi.model.ColisProduit;
import com.smartlogi.model.ColisProduitId;
import com.smartlogi.service.ColisProduitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/colis-produits")
public class ColisProduitController {

    private final ColisProduitService colisProduitService;

    public ColisProduitController(ColisProduitService colisProduitService) {
        this.colisProduitService = colisProduitService;
    }

    @GetMapping
    public List<ColisProduit> getAll() {
        return colisProduitService.findAll();
    }

    @GetMapping("/colis/{colisId}/produit/{produitId}")
    public Optional<ColisProduit> getById(@PathVariable Long colisId, @PathVariable Long produitId) {
        return colisProduitService.findById(new ColisProduitId(colisId, produitId));
    }

    @PostMapping
    public ColisProduit create(@RequestBody ColisProduit colisProduit) {
        return colisProduitService.save(colisProduit);
    }

    @DeleteMapping("/colis/{colisId}/produit/{produitId}")
    public void delete(@PathVariable Long colisId, @PathVariable Long produitId) {
        colisProduitService.delete(new ColisProduitId(colisId, produitId));
    }
}
