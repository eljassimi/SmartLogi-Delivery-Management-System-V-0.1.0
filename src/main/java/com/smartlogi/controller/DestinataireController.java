package com.smartlogi.controller;

import com.smartlogi.model.Destinataire;
import com.smartlogi.service.DestinataireService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/destinataires")
public class DestinataireController {

    private final DestinataireService destinataireService;

    public DestinataireController(DestinataireService destinataireService) {
        this.destinataireService = destinataireService;
    }

    @GetMapping
    public List<Destinataire> getAll() {
        return destinataireService.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Destinataire> getById(@PathVariable Long id) {
        return destinataireService.findById(id);
    }

    @PostMapping
    public Destinataire create(@RequestBody Destinataire destinataire) {
        return destinataireService.save(destinataire);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        destinataireService.delete(id);
    }
}
