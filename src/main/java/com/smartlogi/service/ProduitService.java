package com.smartlogi.service;

import com.smartlogi.dto.produit.ProduitRequestDTO;
import com.smartlogi.dto.produit.ProduitResponseDTO;
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
        return produitRepository.findById(id)
            .map(produitMapper::toResponse)
            .orElse(null);
    }

    public ProduitResponseDTO save(ProduitRequestDTO dto) {
        Produit produit = produitMapper.toEntity(dto);
        produit = produitRepository.save(produit);
        return produitMapper.toResponse(produit);
    }

    public ProduitResponseDTO update(String id, ProduitRequestDTO dto) {
        return produitRepository.findById(id)
            .map(existing -> {
                existing.setNom(dto.getNom());
                existing.setCategorie(dto.getCategorie());
                existing.setPoids(dto.getPoids());
                existing.setPrix(dto.getPrix());
                return produitRepository.save(existing);
            })
            .map(produitMapper::toResponse)
            .orElse(null);
    }

    public void delete(String id) {
        produitRepository.deleteById(id);
    }
}
