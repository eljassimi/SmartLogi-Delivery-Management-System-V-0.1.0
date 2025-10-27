package com.smartlogi.controller;


import com.smartlogi.model.Livreur;
import com.smartlogi.service.LivreurService;
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
    public List<Livreur> getAllLivreur(){
        return livreurService.findAll();
    }

    @PostMapping
    public Livreur createLivreur(@RequestBody Livreur livreur){
        return livreurService.save(livreur);
    }

}
