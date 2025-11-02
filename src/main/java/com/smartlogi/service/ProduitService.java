package com.smartlogi.service;

import com.smartlogi.dto.produit.ProduitRequestDTO;
import com.smartlogi.dto.produit.ProduitResponseDTO;
import com.smartlogi.exception.ResourceNotFoundException;
import com.smartlogi.mapper.ProduitMapper;
import com.smartlogi.model.Produit;
import com.smartlogi.repository.ProduitRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProduitService {
    private final ProduitRepository produitRepository;
    private final ProduitMapper produitMapper;

    public ProduitService(ProduitRepository produitRepository, ProduitMapper produitMapper) {
        this.produitRepository = produitRepository;
        this.produitMapper = produitMapper;
    }

    public List<ProduitResponseDTO> findAll() {
        return produitRepository.findAll().stream()
                .map(produitMapper::toResponse)
                .toList();
    }

    public ProduitResponseDTO findById(String id) {
        Produit produit = produitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", id));
        return produitMapper.toResponse(produit);
    }

    public ProduitResponseDTO save(ProduitRequestDTO dto) {
        Produit produit = produitMapper.toEntity(dto);
        produit = produitRepository.save(produit);
        return produitMapper.toResponse(produit);
    }

    public ProduitResponseDTO update(String id, ProduitRequestDTO dto) {
        Produit existing = produitRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produit", "id", id));
        existing.setNom(dto.getNom());
        existing.setCategorie(dto.getCategorie());
        existing.setPoids(dto.getPoids());
        existing.setPrix(dto.getPrix());
        Produit updated = produitRepository.save(existing);
        return produitMapper.toResponse(updated);
    }

    public void delete(String id) {
        if (!produitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produit", "id", id);
        }
        produitRepository.deleteById(id);
    }
}
