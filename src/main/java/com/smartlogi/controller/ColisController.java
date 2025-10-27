package com.smartlogi.controller;

import com.smartlogi.model.Colis;
import com.smartlogi.repository.ColisRepository;
import com.smartlogi.service.ColisService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colis")
public class ColisController {

    private final ColisService colisService;

    public ColisController(ColisService colisService) {
        this.colisService = colisService;
    }

    @GetMapping
    public List<Colis> getAllColis() {
        return colisService.findAll();
    }

    @PostMapping
    public Colis createColis(@RequestBody Colis colis) {
        return colisService.save(colis);
    }
}
