package com.smartlogi.service;

import com.smartlogi.model.ClientExpediteur;
import com.smartlogi.repository.ClientExpediteurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientExpediteurService {

    private final ClientExpediteurRepository clientExpediteurRepository;

    public ClientExpediteurService(ClientExpediteurRepository clientExpediteurRepository){
        this.clientExpediteurRepository = clientExpediteurRepository;
    }

    public List<ClientExpediteur> findAll(){
        return clientExpediteurRepository.findAll();
    }

    public Optional<ClientExpediteur> findById(Long id){
        return clientExpediteurRepository.findById(id);
    }

    public ClientExpediteur save(ClientExpediteur clientExpediteur){
        return clientExpediteurRepository.save(clientExpediteur);
    }

    public void delete(Long id){
        clientExpediteurRepository.deleteById(id);
    }
}
