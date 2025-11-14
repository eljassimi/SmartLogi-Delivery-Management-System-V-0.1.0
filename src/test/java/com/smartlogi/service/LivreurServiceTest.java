package com.smartlogi.service;

import com.smartlogi.dto.livreur.LivreurRequestDTO;
import com.smartlogi.dto.livreur.LivreurResponseDTO;
import com.smartlogi.exception.ResourceNotFoundException;
import com.smartlogi.mapper.LivreurMapper;
import com.smartlogi.model.Livreur;
import com.smartlogi.repository.LivreurRepository;
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
class LivreurServiceTest {

    @Mock
    private LivreurRepository repository;

    @Mock
    private LivreurMapper mapper;

    @InjectMocks
    private LivreurService service;

    @Test
    void findAll_returnsResponses() {
        Livreur entity1 = buildEntity("1");
        Livreur entity2 = buildEntity("2");
        LivreurResponseDTO response1 = buildResponse("1");
        LivreurResponseDTO response2 = buildResponse("2");

        when(repository.findAll()).thenReturn(List.of(entity1, entity2));
        when(mapper.toResponse(entity1)).thenReturn(response1);
        when(mapper.toResponse(entity2)).thenReturn(response2);

        List<LivreurResponseDTO> result = service.findAll();

        assertEquals(List.of(response1, response2), result);
        verify(repository).findAll();
        verify(mapper).toResponse(entity1);
        verify(mapper).toResponse(entity2);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findById_returnsResponse_whenExists() {
        String id = "livreur-1";
        Livreur entity = buildEntity(id);
        LivreurResponseDTO response = buildResponse(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        LivreurResponseDTO result = service.findById(id);

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
        LivreurRequestDTO request = buildRequest();
        Livreur entity = buildEntity(null);
        Livreur saved = buildEntity("saved");
        LivreurResponseDTO response = buildResponse("saved");

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        LivreurResponseDTO result = service.save(request);

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

    private Livreur buildEntity(String id) {
        Livreur entity = new Livreur();
        entity.setId(id);
        entity.setNom("Nom");
        entity.setPrenom("Prenom");
        entity.setTelephone("0606060606");
        entity.setVehicule("Camion");
        return entity;
    }

    private LivreurResponseDTO buildResponse(String id) {
        LivreurResponseDTO response = new LivreurResponseDTO();
        response.setId(id);
        response.setNom("Nom");
        response.setPrenom("Prenom");
        response.setTelephone("0606060606");
        response.setVehicule("Camion");
        response.setZoneAssigneeId("zone");
        return response;
    }

    private LivreurRequestDTO buildRequest() {
        LivreurRequestDTO request = new LivreurRequestDTO();
        request.setNom("Nom");
        request.setPrenom("Prenom");
        request.setTelephone("0606060606");
        request.setVehicule("Camion");
        request.setZoneAssigneeId("zone");
        return request;
    }
}


