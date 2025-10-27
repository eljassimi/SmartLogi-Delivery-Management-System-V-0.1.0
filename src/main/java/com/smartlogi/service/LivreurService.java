package com.smartlogi.service;

import com.smartlogi.model.Livreur;
import com.smartlogi.repository.LivreurRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LivreurService {

    private final LivreurRepository livreurRepository;

    public LivreurService(LivreurRepository livreurRepository){
        this.livreurRepository = livreurRepository;
    }

    public List<Livreur> findAll(){
        return  livreurRepository.findAll();
    }

    public Optional<Livreur> findById(Long id){
        return livreurRepository.findById(id);
    }

    public Livreur save(Livreur livreur){
        return livreurRepository.save(livreur);
    }

    public void delete(Long id){
        livreurRepository.deleteById(id);
    }

}
