package com.smartlogi.service;

import com.smartlogi.dto.colis.ColisRequestDTO;
import com.smartlogi.dto.colis.ColisResponseDTO;
import com.smartlogi.exception.ResourceNotFoundException;
import com.smartlogi.mapper.ColisMapper;
import com.smartlogi.model.Colis;
import com.smartlogi.repository.ColisRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ColisService {
    private final ColisRepository colisRepository;
    private final ColisMapper colisMapper;

    public ColisService(ColisRepository colisRepository, ColisMapper colisMapper) {
        this.colisRepository = colisRepository;
        this.colisMapper = colisMapper;
    }

    public List<ColisResponseDTO> findAll() {
        return colisRepository.findAll().stream()
                .map(colisMapper::toResponse)
                .toList();
    }

    public ColisResponseDTO findById(String id) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis", "id", id));
        return colisMapper.toResponse(colis);
    }

    public ColisResponseDTO save(ColisRequestDTO dto) {
        Colis entity = colisMapper.toEntity(dto);
        entity = colisRepository.save(entity);
        return colisMapper.toResponse(entity);
    }

    public void delete(String id) {
        if (!colisRepository.existsById(id)) {
            throw new ResourceNotFoundException("Colis", "id", id);
        }
        colisRepository.deleteById(id);
    }
}
