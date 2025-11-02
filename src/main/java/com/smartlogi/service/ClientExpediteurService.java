package com.smartlogi.service;

import com.smartlogi.dto.clientexpediteur.ClientExpediteurRequestDTO;
import com.smartlogi.dto.clientexpediteur.ClientExpediteurResponseDTO;
import com.smartlogi.exception.ResourceNotFoundException;
import com.smartlogi.mapper.ClientExpediteurMapper;
import com.smartlogi.model.ClientExpediteur;
import com.smartlogi.repository.ClientExpediteurRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ClientExpediteurService {
    private final ClientExpediteurRepository clientExpediteurRepository;
    private final ClientExpediteurMapper clientExpediteurMapper;

    public ClientExpediteurService(ClientExpediteurRepository clientExpediteurRepository, ClientExpediteurMapper clientExpediteurMapper) {
        this.clientExpediteurRepository = clientExpediteurRepository;
        this.clientExpediteurMapper = clientExpediteurMapper;
    }

    public List<ClientExpediteurResponseDTO> findAll() {
        return clientExpediteurRepository.findAll().stream()
                .map(clientExpediteurMapper::toResponse)
                .toList();
    }

    public ClientExpediteurResponseDTO findById(String id) {
        ClientExpediteur client = clientExpediteurRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("ClientExpediteur", "id", id));
        return clientExpediteurMapper.toResponse(client);
    }

    public ClientExpediteurResponseDTO save(ClientExpediteurRequestDTO dto) {
        ClientExpediteur client = clientExpediteurMapper.toEntity(dto);
        client = clientExpediteurRepository.save(client);
        return clientExpediteurMapper.toResponse(client);
    }

    public ClientExpediteurResponseDTO update(String clientId, ClientExpediteurRequestDTO dto){
        ClientExpediteur existing = clientExpediteurRepository.findById(clientId)
            .orElseThrow(() -> new ResourceNotFoundException("ClientExpediteur", "id", clientId));
        existing.setNom(dto.getNom());
        existing.setPrenom(dto.getPrenom());
        existing.setEmail(dto.getEmail());
        existing.setTelephone(dto.getTelephone());
        existing.setAdresse(dto.getAdresse());
        ClientExpediteur updated = clientExpediteurRepository.save(existing);
        return clientExpediteurMapper.toResponse(updated);
    }

    public void delete(String id) {
        if (!clientExpediteurRepository.existsById(id)) {
            throw new ResourceNotFoundException("ClientExpediteur", "id", id);
        }
        clientExpediteurRepository.deleteById(id);
    }
}
