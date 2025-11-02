package com.smartlogi.service;

import com.smartlogi.dto.colisproduit.ColisProduitRequestDTO;
import com.smartlogi.dto.colisproduit.ColisProduitResponseDTO;
import com.smartlogi.mapper.ColisProduitMapper;
import com.smartlogi.model.ColisProduit;
import com.smartlogi.model.ColisProduitId;
import com.smartlogi.repository.ColisProduitRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ColisProduitService {
    private final ColisProduitRepository repo;
    private final ColisProduitMapper mapper;
    public ColisProduitService(ColisProduitRepository repo, ColisProduitMapper mapper){this.repo=repo;this.mapper=mapper;}
    public List<ColisProduitResponseDTO> findAll() {
        return repo.findAll().stream().map(mapper::toResponse).toList();
    }
    public ColisProduitResponseDTO findById(ColisProduitId id) {
        return repo.findById(id).map(mapper::toResponse).orElse(null);
    }
    public ColisProduitResponseDTO save(ColisProduitRequestDTO dto) {
        ColisProduit entity = mapper.toEntity(dto);
        if (repo.existsById(entity.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "association Colis Produit Deja Existe !");
        }
        entity = repo.save(entity);
        return mapper.toResponse(entity);
    }
    public void delete(ColisProduitId id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune association Colis/Produit Trouve a supprimer.");
        }
        repo.deleteById(id);
    }
}
