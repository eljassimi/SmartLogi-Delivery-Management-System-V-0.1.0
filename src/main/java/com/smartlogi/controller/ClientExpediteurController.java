package com.smartlogi.controller;

import com.smartlogi.dto.clientexpediteur.ClientExpediteurRequestDTO;
import com.smartlogi.dto.clientexpediteur.ClientExpediteurResponseDTO;
import com.smartlogi.service.ClientExpediteurService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clients")
public class ClientExpediteurController {

    private final ClientExpediteurService clientExpediteurService;

    public ClientExpediteurController(ClientExpediteurService clientExpediteurService){
        this.clientExpediteurService = clientExpediteurService;
    }

    @GetMapping
    public List<ClientExpediteurResponseDTO> getAllClientExpediteur(){
        return clientExpediteurService.findAll();
    }

    @PostMapping
    public ClientExpediteurResponseDTO createClientExpediteur(@Valid @RequestBody ClientExpediteurRequestDTO request){
        return clientExpediteurService.save(request);
    }

    @GetMapping("/{clientId}")
    public ClientExpediteurResponseDTO getClientById(@PathVariable String clientId){
        return clientExpediteurService.findById(clientId);
    }

    @PutMapping("/{clientId}")
    public ClientExpediteurResponseDTO updateClient(@PathVariable String clientId, @Valid @RequestBody ClientExpediteurRequestDTO request){
        return clientExpediteurService.update(clientId,request);
    }

    @DeleteMapping("/{clientId}")
    public void deleteClient(@PathVariable String clientId){
        clientExpediteurService.delete(clientId);
    }
}
