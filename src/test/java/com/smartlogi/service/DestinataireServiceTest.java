package com.smartlogi.service;

import com.smartlogi.dto.destinataire.DestinataireRequestDTO;
import com.smartlogi.dto.destinataire.DestinataireResponseDTO;
import com.smartlogi.exception.ResourceNotFoundException;
import com.smartlogi.mapper.DestinataireMapper;
import com.smartlogi.model.Destinataire;
import com.smartlogi.repository.DestinataireRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DestinataireServiceTest {

    @Mock
    private DestinataireRepository repository;

    @Mock
    private DestinataireMapper mapper;

    @InjectMocks
    private DestinataireService service;

    @Test
    void findAll_returnsResponses() {
        Destinataire entity1 = buildEntity("1");
        Destinataire entity2 = buildEntity("2");
        DestinataireResponseDTO response1 = buildResponse("1");
        DestinataireResponseDTO response2 = buildResponse("2");

        when(repository.findAll()).thenReturn(List.of(entity1, entity2));
        when(mapper.toResponse(entity1)).thenReturn(response1);
        when(mapper.toResponse(entity2)).thenReturn(response2);

        List<DestinataireResponseDTO> result = service.findAll();

        assertEquals(List.of(response1, response2), result);
        verify(repository).findAll();
        verify(mapper).toResponse(entity1);
        verify(mapper).toResponse(entity2);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findById_returnsResponse_whenExists() {
        String id = "dest-1";
        Destinataire entity = buildEntity(id);
        DestinataireResponseDTO response = buildResponse(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        DestinataireResponseDTO result = service.findById(id);

        assertSame(response, result);
        verify(repository).findById(id);
        verify(mapper).toResponse(entity);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findById_throws_whenMissing() {
        String id = "missing";
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(id));

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void save_persistsEntity() {
        DestinataireRequestDTO request = buildRequest();
        Destinataire entity = buildEntity(null);
        Destinataire saved = buildEntity("saved");
        DestinataireResponseDTO response = buildResponse("saved");

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        DestinataireResponseDTO result = service.save(request);

        assertSame(response, result);
        verify(mapper).toEntity(request);
        verify(repository).save(entity);
        verify(mapper).toResponse(saved);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void delete_removes_whenExists() {
        String id = "delete";
        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);

        service.delete(id);

        verify(repository).existsById(id);
        verify(repository).deleteById(id);
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void delete_throws_whenMissing() {
        String id = "missing";
        when(repository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> service.delete(id));

        verify(repository).existsById(id);
        verify(repository, never()).deleteById(id);
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(mapper);
    }

    private Destinataire buildEntity(String id) {
        Destinataire entity = new Destinataire();
        entity.setId(id);
        entity.setNom("Nom");
        entity.setPrenom("Prenom");
        entity.setTelephone("0707070707");
        entity.setAdresse("Adresse");
        entity.setEmail("dest@example.com");
        return entity;
    }

    private DestinataireResponseDTO buildResponse(String id) {
        DestinataireResponseDTO response = new DestinataireResponseDTO();
        response.setId(id);
        response.setNom("Nom");
        response.setPrenom("Prenom");
        response.setTelephone("0707070707");
        response.setAdresse("Adresse");
        response.setEmail("dest@example.com");
        return response;
    }

    private DestinataireRequestDTO buildRequest() {
        DestinataireRequestDTO request = new DestinataireRequestDTO();
        request.setNom("Nom");
        request.setPrenom("Prenom");
        request.setTelephone("0707070707");
        request.setAdresse("Adresse");
        request.setEmail("dest@example.com");
        return request;
    }
}

