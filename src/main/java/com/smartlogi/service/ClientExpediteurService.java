package com.smartlogi.service;

import com.smartlogi.dto.clientexpediteur.ClientExpediteurRequestDTO;
import com.smartlogi.dto.clientexpediteur.ClientExpediteurResponseDTO;
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
        return clientExpediteurRepository.findById(id)
            .map(clientExpediteurMapper::toResponse)
            .orElse(null);
    }

    public ClientExpediteurResponseDTO save(ClientExpediteurRequestDTO dto) {
        ClientExpediteur client = clientExpediteurMapper.toEntity(dto);
        client = clientExpediteurRepository.save(client);
        return clientExpediteurMapper.toResponse(client);
    }

    public void delete(String id) {
        clientExpediteurRepository.deleteById(id);
    }
}
