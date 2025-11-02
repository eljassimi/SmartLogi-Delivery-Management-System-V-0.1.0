package com.smartlogi.service;

import com.smartlogi.dto.destinataire.DestinataireRequestDTO;
import com.smartlogi.dto.destinataire.DestinataireResponseDTO;
import com.smartlogi.exception.ResourceNotFoundException;
import com.smartlogi.mapper.DestinataireMapper;
import com.smartlogi.model.Destinataire;
import com.smartlogi.repository.DestinataireRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DestinataireService {
    private final DestinataireRepository destinataireRepository;
    private final DestinataireMapper destinataireMapper;

    public DestinataireService(DestinataireRepository destinataireRepository, DestinataireMapper destinataireMapper) {
        this.destinataireRepository = destinataireRepository;
        this.destinataireMapper = destinataireMapper;
    }

    public List<DestinataireResponseDTO> findAll() {
        return destinataireRepository.findAll().stream()
                .map(destinataireMapper::toResponse)
                .toList();
    }

    public DestinataireResponseDTO findById(String id) {
        Destinataire destinataire = destinataireRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Destinataire", "id", id));
        return destinataireMapper.toResponse(destinataire);
    }

    public DestinataireResponseDTO save(DestinataireRequestDTO dto) {
        Destinataire destinataire = destinataireMapper.toEntity(dto);
        destinataire = destinataireRepository.save(destinataire);
        return destinataireMapper.toResponse(destinataire);
    }

    public void delete(String id) {
        if (!destinataireRepository.existsById(id)) {
            throw new ResourceNotFoundException("Destinataire", "id", id);
        }
        destinataireRepository.deleteById(id);
    }
}
