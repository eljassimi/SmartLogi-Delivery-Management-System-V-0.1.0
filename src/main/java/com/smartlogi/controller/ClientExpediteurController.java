package com.smartlogi.controller;


import com.smartlogi.model.ClientExpediteur;
import com.smartlogi.service.ClientExpediteurService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client-expediteurs")
public class ClientExpediteurController {

    private final ClientExpediteurService clientExpediteurService;

    public ClientExpediteurController(ClientExpediteurService clientExpediteurService){
        this.clientExpediteurService = clientExpediteurService;
    }

    @GetMapping
    public List<ClientExpediteur> getAllClientExpediteur(){
        return clientExpediteurService.findAll();
    }

    @PostMapping
    public ClientExpediteur createClientExpediteur(@RequestBody ClientExpediteur clientExpediteur){
        return clientExpediteurService.save(clientExpediteur);
    }

}
