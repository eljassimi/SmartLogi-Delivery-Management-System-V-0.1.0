package com.smartlogi.service;

import com.smartlogi.dto.colisproduit.ColisProduitRequestDTO;
import com.smartlogi.dto.colisproduit.ColisProduitResponseDTO;
import com.smartlogi.mapper.ColisProduitMapper;
import com.smartlogi.model.ColisProduit;
import com.smartlogi.model.ColisProduitId;
import com.smartlogi.repository.ColisProduitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ColisProduitServiceTest {

    @Mock
    private ColisProduitRepository repository;

    @Mock
    private ColisProduitMapper mapper;

    @InjectMocks
    private ColisProduitService service;

    @Test
    void findAll_returnsMappedResponses() {
        ColisProduit entity1 = buildEntity("colis1", "prod1");
        ColisProduit entity2 = buildEntity("colis2", "prod2");
        ColisProduitResponseDTO response1 = buildResponse("colis1", "prod1");
        ColisProduitResponseDTO response2 = buildResponse("colis2", "prod2");

        when(repository.findAll()).thenReturn(List.of(entity1, entity2));
        when(mapper.toResponse(entity1)).thenReturn(response1);
        when(mapper.toResponse(entity2)).thenReturn(response2);

        List<ColisProduitResponseDTO> result = service.findAll();

        assertEquals(List.of(response1, response2), result);
        verify(repository).findAll();
        verify(mapper).toResponse(entity1);
        verify(mapper).toResponse(entity2);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findById_returnsNull_whenMissing() {
        ColisProduitId id = buildId("colis", "prod");
        when(repository.findById(id)).thenReturn(Optional.empty());

        ColisProduitResponseDTO result = service.findById(id);

        assertNull(result);
        verify(repository).findById(id);
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void findById_returnsMappedResponse_whenExists() {
        ColisProduitId id = buildId("colis", "prod");
        ColisProduit entity = buildEntity("colis", "prod");
        ColisProduitResponseDTO response = buildResponse("colis", "prod");

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        ColisProduitResponseDTO result = service.findById(id);

        assertSame(response, result);
        verify(repository).findById(id);
        verify(mapper).toResponse(entity);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void save_throwsConflict_whenAssociationExists() {
        ColisProduitRequestDTO request = buildRequest("colis", "prod");
        ColisProduit entity = buildEntity("colis", "prod");

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.existsById(entity.getId())).thenReturn(true);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.save(request));

        assertEquals(HttpStatus.CONFLICT, exception.getStatusCode());
        verify(mapper).toEntity(request);
        verify(repository).existsById(entity.getId());
        verify(repository, never()).save(entity);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void save_persistsNewAssociation() {
        ColisProduitRequestDTO request = buildRequest("colis", "prod");
        ColisProduit entity = buildEntity("colis", "prod");
        ColisProduit saved = buildEntity("colis", "prod");
        ColisProduitResponseDTO response = buildResponse("colis", "prod");

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.existsById(entity.getId())).thenReturn(false);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        ColisProduitResponseDTO result = service.save(request);

        assertSame(response, result);
        verify(mapper).toEntity(request);
        verify(repository).existsById(entity.getId());
        verify(repository).save(entity);
        verify(mapper).toResponse(saved);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void delete_throwsNotFound_whenMissing() {
        ColisProduitId id = buildId("colis", "prod");
        when(repository.existsById(id)).thenReturn(false);

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> service.delete(id));

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        verify(repository).existsById(id);
        verify(repository, never()).deleteById(id);
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(mapper);
    }

    @Test
    void delete_removesAssociation_whenExists() {
        ColisProduitId id = buildId("colis", "prod");
        when(repository.existsById(id)).thenReturn(true);
        doNothing().when(repository).deleteById(id);

        service.delete(id);

        verify(repository).existsById(id);
        verify(repository).deleteById(id);
        verifyNoMoreInteractions(repository);
        verifyNoMoreInteractions(mapper);
    }

    private ColisProduitId buildId(String colisId, String produitId) {
        ColisProduitId id = new ColisProduitId();
        id.setColisId(colisId);
        id.setProduitId(produitId);
        return id;
    }

    private ColisProduit buildEntity(String colisId, String produitId) {
        ColisProduit entity = new ColisProduit();
        entity.setId(buildId(colisId, produitId));
        entity.setQuantite(1);
        return entity;
    }

    private ColisProduitResponseDTO buildResponse(String colisId, String produitId) {
        ColisProduitResponseDTO response = new ColisProduitResponseDTO();
        response.setColisId(colisId);
        response.setProduitId(produitId);
        response.setQuantite(1);
        return response;
    }

    private ColisProduitRequestDTO buildRequest(String colisId, String produitId) {
        ColisProduitRequestDTO request = new ColisProduitRequestDTO();
        request.setColisId(colisId);
        request.setProduitId(produitId);
        request.setQuantite(1);
        return request;
    }
}


