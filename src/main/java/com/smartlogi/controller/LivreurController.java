package com.smartlogi.controller;


import com.smartlogi.model.Livreur;
import com.smartlogi.service.LivreurService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/livreur")
public class LivreurController {

    private final LivreurService livreurService;

    public LivreurController(LivreurService livreurService){
        this.livreurService = livreurService;
    }

    @GetMapping
    public List<Livreur> getAllLivreur(){
        return livreurService.findAll();
    }

}
