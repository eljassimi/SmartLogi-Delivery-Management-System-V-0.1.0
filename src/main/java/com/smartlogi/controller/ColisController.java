package com.smartlogi.controller;

import com.smartlogi.model.Colis;
import com.smartlogi.repository.ColisRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colis")
public class ColisController {

    private final ColisRepository colisRepository;

    public ColisController(ColisRepository colisRepository) {
        this.colisRepository = colisRepository;
    }

    @GetMapping
    public List<Colis> getAllColis() {
        return colisRepository.findAll();
    }

    @PostMapping
    public Colis createColis(@RequestBody Colis colis) {
        return colisRepository.save(colis);
    }
}
