package com.smartlogi.service;

import com.smartlogi.dto.historiquelivraison.HistoriqueLivraisonRequestDTO;
import com.smartlogi.dto.historiquelivraison.HistoriqueLivraisonResponseDTO;
import com.smartlogi.mapper.HistoriqueLivraisonMapper;
import com.smartlogi.model.HistoriqueLivraison;
import com.smartlogi.repository.HistoriqueLivraisonRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class HistoriqueLivraisonService {
    private final HistoriqueLivraisonRepository repo;
    private final HistoriqueLivraisonMapper mapper;
    public HistoriqueLivraisonService(HistoriqueLivraisonRepository repo, HistoriqueLivraisonMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }
    public List<HistoriqueLivraisonResponseDTO> findAll() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }
    public HistoriqueLivraisonResponseDTO save(HistoriqueLivraisonRequestDTO dto) {
        HistoriqueLivraison entity = mapper.toEntity(dto);
        entity = repo.save(entity);
        return mapper.toResponse(entity);
    }
    // Add findById if needed
}
