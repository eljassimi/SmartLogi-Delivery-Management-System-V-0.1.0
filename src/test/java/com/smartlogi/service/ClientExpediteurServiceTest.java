package com.smartlogi.service;

import com.smartlogi.dto.clientexpediteur.ClientExpediteurRequestDTO;
import com.smartlogi.dto.clientexpediteur.ClientExpediteurResponseDTO;
import com.smartlogi.exception.ResourceNotFoundException;
import com.smartlogi.mapper.ClientExpediteurMapper;
import com.smartlogi.model.ClientExpediteur;
import com.smartlogi.repository.ClientExpediteurRepository;
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
class ClientExpediteurServiceTest {

    @Mock
    private ClientExpediteurRepository repository;

    @Mock
    private ClientExpediteurMapper mapper;

    @InjectMocks
    private ClientExpediteurService service;

    @Test
    void findAll_returnsResponses() {
        ClientExpediteur client1 = buildEntity("1");
        ClientExpediteur client2 = buildEntity("2");
        ClientExpediteurResponseDTO response1 = buildResponse("1");
        ClientExpediteurResponseDTO response2 = buildResponse("2");

        when(repository.findAll()).thenReturn(List.of(client1, client2));
        when(mapper.toResponse(client1)).thenReturn(response1);
        when(mapper.toResponse(client2)).thenReturn(response2);

        List<ClientExpediteurResponseDTO> result = service.findAll();

        assertEquals(List.of(response1, response2), result);
        verify(repository).findAll();
        verify(mapper).toResponse(client1);
        verify(mapper).toResponse(client2);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findById_returnsResponse_whenExists() {
        String id = "client-1";
        ClientExpediteur client = buildEntity(id);
        ClientExpediteurResponseDTO response = buildResponse(id);

        when(repository.findById(id)).thenReturn(Optional.of(client));
        when(mapper.toResponse(client)).thenReturn(response);

        ClientExpediteurResponseDTO result = service.findById(id);

        assertSame(response, result);
        verify(repository).findById(id);
        verify(mapper).toResponse(client);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findById_throws_whenMissing() {
        String id = "missing";
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(id));

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void save_persistsEntity() {
        ClientExpediteurRequestDTO request = buildRequest();
        ClientExpediteur toSave = buildEntity(null);
        ClientExpediteur saved = buildEntity("saved");
        ClientExpediteurResponseDTO response = buildResponse("saved");

        when(mapper.toEntity(request)).thenReturn(toSave);
        when(repository.save(toSave)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        ClientExpediteurResponseDTO result = service.save(request);

        assertSame(response, result);
        verify(mapper).toEntity(request);
        verify(repository).save(toSave);
        verify(mapper).toResponse(saved);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void update_updatesExistingClient() {
        String id = "client-1";
        ClientExpediteurRequestDTO request = buildRequest();
        ClientExpediteur existing = buildEntity(id);
        ClientExpediteurResponseDTO response = buildResponse(id);

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);
        when(mapper.toResponse(existing)).thenReturn(response);

        ClientExpediteurResponseDTO result = service.update(id, request);

        assertSame(response, result);
        assertEquals(request.getNom(), existing.getNom());
        assertEquals(request.getPrenom(), existing.getPrenom());
        assertEquals(request.getEmail(), existing.getEmail());
        assertEquals(request.getTelephone(), existing.getTelephone());
        assertEquals(request.getAdresse(), existing.getAdresse());

        verify(repository).findById(id);
        verify(repository).save(existing);
        verify(mapper).toResponse(existing);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void update_throws_whenMissing() {
        String id = "missing";
        ClientExpediteurRequestDTO request = buildRequest();
        when(repository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.update(id, request));

        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(mapper);
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

    private ClientExpediteur buildEntity(String id) {
        ClientExpediteur entity = new ClientExpediteur();
        entity.setId(id);
        entity.setNom("Nom");
        entity.setPrenom("Prenom");
        entity.setEmail("email@example.com");
        entity.setTelephone("0606060606");
        entity.setAdresse("Adresse 1");
        return entity;
    }

    private ClientExpediteurRequestDTO buildRequest() {
        ClientExpediteurRequestDTO request = new ClientExpediteurRequestDTO();
        request.setNom("Nom");
        request.setPrenom("Prenom");
        request.setEmail("email@example.com");
        request.setTelephone("0606060606");
        request.setAdresse("Adresse 1");
        return request;
    }

    private ClientExpediteurResponseDTO buildResponse(String id) {
        ClientExpediteurResponseDTO response = new ClientExpediteurResponseDTO();
        response.setId(id);
        response.setNom("Nom");
        response.setPrenom("Prenom");
        response.setEmail("email@example.com");
        response.setTelephone("0606060606");
        response.setAdresse("Adresse 1");
        return response;
    }
}


