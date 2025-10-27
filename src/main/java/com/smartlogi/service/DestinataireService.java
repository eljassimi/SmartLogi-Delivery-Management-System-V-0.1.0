package com.smartlogi.service;

import com.smartlogi.model.Destinataire;
import com.smartlogi.repository.DestinataireRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DestinataireService {

    private final DestinataireRepository destinataireRepository;

    public DestinataireService(DestinataireRepository destinataireRepository) {
        this.destinataireRepository = destinataireRepository;
    }

    public List<Destinataire> findAll() {
        return destinataireRepository.findAll();
    }

    public Optional<Destinataire> findById(Long id) {
        return destinataireRepository.findById(id);
    }

    public Destinataire save(Destinataire destinataire) {
        return destinataireRepository.save(destinataire);
    }

    public void delete(Long id) {
        destinataireRepository.deleteById(id);
    }
}
