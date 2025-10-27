package com.smartlogi.controller;


import com.smartlogi.model.ClientExpediteur;
import com.smartlogi.repository.ClientExpediteurRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/client-expediteurs")
public class ClientExpediteurController {

    private final ClientExpediteurRepository clientExpediteurRepository;

    public ClientExpediteurController(ClientExpediteurRepository clientExpediteurRepository){
        this.clientExpediteurRepository = clientExpediteurRepository;
    }

    @GetMapping
    public List<ClientExpediteur> getAllClientExpediteur(){
        return clientExpediteurRepository.findAll();
    }

    @PostMapping
    public ClientExpediteur createClientExpediteur(@RequestBody ClientExpediteur clientExpediteur){
        return clientExpediteurRepository.save(clientExpediteur);
    }

}
