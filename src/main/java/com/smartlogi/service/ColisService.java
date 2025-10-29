package com.smartlogi.service;

import com.smartlogi.model.Colis;
import com.smartlogi.repository.ColisRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ColisService {

    private final ColisRepository colisRepository;

    public ColisService(ColisRepository colisRepository){
        this.colisRepository = colisRepository;
    }

    public List<Colis> findAll(){
        return colisRepository.findAll();
    }

    public Optional<Colis> findById(String id){
        return colisRepository.findById(id);
    }

    public Colis save(Colis colis){
        return  colisRepository.save(colis);
    }

    public void delete(String id){
        colisRepository.deleteById(id);
    }

}
