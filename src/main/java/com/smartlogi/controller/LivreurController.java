package com.smartlogi.controller;

import com.smartlogi.dto.livreur.LivreurRequestDTO;
import com.smartlogi.dto.livreur.LivreurResponseDTO;
import com.smartlogi.service.LivreurService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/livreurs")
public class LivreurController {

    private final LivreurService livreurService;

    public LivreurController(LivreurService livreurService){
        this.livreurService = livreurService;
    }

    @GetMapping
    public List<LivreurResponseDTO> getAllLivreur(){
        return livreurService.findAll();
    }

    @PostMapping
    public LivreurResponseDTO createLivreur(@Valid @RequestBody LivreurRequestDTO request){
        return livreurService.save(request);
    }
    @GetMapping("/{livreurId}")
    public LivreurResponseDTO getLivreurById(@PathVariable String livreurId){
        return livreurService.findById(livreurId);
    }

}
