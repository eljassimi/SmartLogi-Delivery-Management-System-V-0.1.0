package com.smartlogi.service;

import com.smartlogi.model.HistoriqueLivraison;
import com.smartlogi.repository.HistoriqueLivraisonRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoriqueLivraisonService {
    private final HistoriqueLivraisonRepository repo;

    public HistoriqueLivraisonService(HistoriqueLivraisonRepository repo) {
        this.repo = repo;
    }

    public List<HistoriqueLivraison> findAll() {
        return repo.findAll();
    }

    public HistoriqueLivraison save(HistoriqueLivraison historique) {
        return repo.save(historique);
    }
}
