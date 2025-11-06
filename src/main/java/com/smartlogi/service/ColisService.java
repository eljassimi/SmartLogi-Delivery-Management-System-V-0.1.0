package com.smartlogi.service;

import com.smartlogi.dto.colis.ColisRequestDTO;
import com.smartlogi.dto.colis.ColisResponseDTO;
import com.smartlogi.exception.ResourceNotFoundException;
import com.smartlogi.mapper.ColisMapper;
import com.smartlogi.model.Colis;
import com.smartlogi.repository.ColisRepository;
import com.smartlogi.repository.specification.ColisSpecifications;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ColisService {
    private final ColisRepository colisRepository;
    private final ColisMapper colisMapper;

    public ColisService(ColisRepository colisRepository, ColisMapper colisMapper) {
        this.colisRepository = colisRepository;
        this.colisMapper = colisMapper;
    }

    public Page<ColisResponseDTO> search(String statut, Pageable pageable) {
        Pageable effectivePageable = pageable != null ?
                pageable :
                PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "dateCreation"));

        Specification<Colis> specification = null;
        if (statut != null && !statut.isEmpty()) {
            specification = ColisSpecifications.hasStatut(statut);
        }

        return colisRepository.findAll(specification, effectivePageable)
                .map(colisMapper::toResponse);
    }

    public ColisResponseDTO findById(String id) {
        Colis colis = colisRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Colis", "id", id));
        return colisMapper.toResponse(colis);
    }

    public ColisResponseDTO save(ColisRequestDTO dto) {
        Colis entity = colisMapper.toEntity(dto);
        if (entity.getDateCreation() == null) {
            entity.setDateCreation(LocalDate.now());
        }
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
