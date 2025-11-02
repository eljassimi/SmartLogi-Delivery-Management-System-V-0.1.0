package com.smartlogi.controller;

import com.smartlogi.dto.colis.ColisRequestDTO;
import com.smartlogi.dto.colis.ColisResponseDTO;
import com.smartlogi.service.ColisService;
import jakarta.validation.Valid;
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
    public List<ColisResponseDTO> getAllColis() {
        return colisService.findAll();
    }

    @PostMapping
    public ColisResponseDTO createColis(@Valid @RequestBody ColisRequestDTO request) {
        return colisService.save(request);
    }

    @GetMapping("/{colisId}")
    public ColisResponseDTO getColisById(@PathVariable String colisId){
        return colisService.findById(colisId);
    }
}
