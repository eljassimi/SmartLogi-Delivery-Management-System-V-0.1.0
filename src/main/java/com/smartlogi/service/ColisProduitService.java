package com.smartlogi.service;

import com.smartlogi.model.ColisProduit;
import com.smartlogi.model.ColisProduitId;
import com.smartlogi.repository.ColisProduitRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ColisProduitService {

    private final ColisProduitRepository colisProduitRepository;

    public ColisProduitService(ColisProduitRepository colisProduitRepository) {
        this.colisProduitRepository = colisProduitRepository;
    }

    public List<ColisProduit> findAll() {
        return colisProduitRepository.findAll();
    }

    public Optional<ColisProduit> findById(ColisProduitId id) {
        return colisProduitRepository.findById(id);
    }

    public ColisProduit save(ColisProduit colisProduit) {
        return colisProduitRepository.save(colisProduit);
    }

    public void delete(ColisProduitId id) {
        colisProduitRepository.deleteById(id);
    }
}
