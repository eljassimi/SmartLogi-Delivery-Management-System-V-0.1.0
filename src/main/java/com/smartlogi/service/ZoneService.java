package com.smartlogi.service;

import com.smartlogi.dto.zone.ZoneRequestDTO;
import com.smartlogi.dto.zone.ZoneResponseDTO;
import com.smartlogi.exception.ResourceNotFoundException;
import com.smartlogi.mapper.ZoneMapper;
import com.smartlogi.model.Zone;
import com.smartlogi.repository.ZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZoneService {
    private final ZoneRepository zoneRepository;
    private final ZoneMapper zoneMapper;

    public ZoneService(ZoneRepository zoneRepository, ZoneMapper zoneMapper) {
        this.zoneRepository = zoneRepository;
        this.zoneMapper = zoneMapper;
    }

    public List<ZoneResponseDTO> findAll() {
        return zoneRepository.findAll().stream()
                .map(zoneMapper::toResponse)
                .toList();
    }

    public ZoneResponseDTO findById(String id) {
        Zone zone = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone", "id", id));
        return zoneMapper.toResponse(zone);
    }

    public ZoneResponseDTO save(ZoneRequestDTO dto) {
        Zone zone = zoneMapper.toEntity(dto);
        zone = zoneRepository.save(zone);
        return zoneMapper.toResponse(zone);
    }

    public ZoneResponseDTO update(String id, ZoneRequestDTO dto) {
        Zone existing = zoneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Zone", "id", id));
        existing.setNom(dto.getNom());
        existing.setCodePostal(dto.getCodePostal());
        existing = zoneRepository.save(existing);
        return zoneMapper.toResponse(existing);
    }

    public void delete(String id) {
        if (!zoneRepository.existsById(id)) {
            throw new ResourceNotFoundException("Zone", "id", id);
        }
        zoneRepository.deleteById(id);
    }
}


