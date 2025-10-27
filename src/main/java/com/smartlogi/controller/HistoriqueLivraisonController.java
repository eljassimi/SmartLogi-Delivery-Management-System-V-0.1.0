package com.smartlogi.controller;

import com.smartlogi.model.HistoriqueLivraison;
import com.smartlogi.service.HistoriqueLivraisonService;
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
    public List<HistoriqueLivraison> getAll() {
        return service.findAll();
    }

    @PostMapping
    public HistoriqueLivraison create(@RequestBody HistoriqueLivraison historique) {
        return service.save(historique);
    }
}
