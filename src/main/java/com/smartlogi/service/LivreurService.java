package com.smartlogi.service;

import com.smartlogi.dto.livreur.LivreurRequestDTO;
import com.smartlogi.dto.livreur.LivreurResponseDTO;
import com.smartlogi.exception.ResourceNotFoundException;
import com.smartlogi.mapper.LivreurMapper;
import com.smartlogi.model.Livreur;
import com.smartlogi.repository.LivreurRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class LivreurService {
    private final LivreurRepository livreurRepository;
    private final LivreurMapper livreurMapper;

    public LivreurService(LivreurRepository livreurRepository, LivreurMapper livreurMapper) {
        this.livreurRepository = livreurRepository;
        this.livreurMapper = livreurMapper;
    }

    public List<LivreurResponseDTO> findAll() {
        return livreurRepository.findAll().stream()
            .map(livreurMapper::toResponse)
            .toList();
    }
    public LivreurResponseDTO findById(String id) {
        Livreur livreur = livreurRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Livreur", "id", id));
        return livreurMapper.toResponse(livreur);
    }
    public LivreurResponseDTO save(LivreurRequestDTO dto) {
        Livreur entity = livreurMapper.toEntity(dto);
        entity = livreurRepository.save(entity);
        return livreurMapper.toResponse(entity);
    }
    public void delete(String id) {
        if (!livreurRepository.existsById(id)) {
            throw new ResourceNotFoundException("Livreur", "id", id);
        }
        livreurRepository.deleteById(id);
    }
}
