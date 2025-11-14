package com.smartlogi.service;

import com.smartlogi.dto.zone.ZoneRequestDTO;
import com.smartlogi.dto.zone.ZoneResponseDTO;
import com.smartlogi.exception.ResourceNotFoundException;
import com.smartlogi.mapper.ZoneMapper;
import com.smartlogi.model.Zone;
import com.smartlogi.repository.ZoneRepository;
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
class ZoneServiceTest {

    @Mock
    private ZoneRepository repository;

    @Mock
    private ZoneMapper mapper;

    @InjectMocks
    private ZoneService service;

    @Test
    void findAll_returnsResponses() {
        Zone entity1 = buildEntity("1");
        Zone entity2 = buildEntity("2");
        ZoneResponseDTO response1 = buildResponse("1");
        ZoneResponseDTO response2 = buildResponse("2");

        when(repository.findAll()).thenReturn(List.of(entity1, entity2));
        when(mapper.toResponse(entity1)).thenReturn(response1);
        when(mapper.toResponse(entity2)).thenReturn(response2);

        List<ZoneResponseDTO> result = service.findAll();

        assertEquals(List.of(response1, response2), result);
        verify(repository).findAll();
        verify(mapper).toResponse(entity1);
        verify(mapper).toResponse(entity2);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findById_returnsResponse_whenExists() {
        String id = "zone-1";
        Zone entity = buildEntity(id);
        ZoneResponseDTO response = buildResponse(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        ZoneResponseDTO result = service.findById(id);

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
        ZoneRequestDTO request = buildRequest();
        Zone entity = buildEntity(null);
        Zone saved = buildEntity("saved");
        ZoneResponseDTO response = buildResponse("saved");

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        ZoneResponseDTO result = service.save(request);

        assertSame(response, result);
        verify(mapper).toEntity(request);
        verify(repository).save(entity);
        verify(mapper).toResponse(saved);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void update_updatesExistingZone() {
        String id = "zone-1";
        ZoneRequestDTO request = buildRequest();
        Zone existing = buildEntity(id);
        ZoneResponseDTO response = buildResponse(id);

        when(repository.findById(id)).thenReturn(Optional.of(existing));
        when(repository.save(existing)).thenReturn(existing);
        when(mapper.toResponse(existing)).thenReturn(response);

        ZoneResponseDTO result = service.update(id, request);

        assertSame(response, result);
        assertEquals(request.getNom(), existing.getNom());
        assertEquals(request.getCodePostal(), existing.getCodePostal());
        verify(repository).findById(id);
        verify(repository).save(existing);
        verify(mapper).toResponse(existing);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void update_throws_whenMissing() {
        String id = "missing";
        ZoneRequestDTO request = buildRequest();
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

    private Zone buildEntity(String id) {
        Zone entity = new Zone();
        entity.setId(id);
        entity.setNom("Zone");
        entity.setCodePostal("20000");
        return entity;
    }

    private ZoneResponseDTO buildResponse(String id) {
        ZoneResponseDTO response = new ZoneResponseDTO();
        response.setId(id);
        response.setNom("Zone");
        response.setCodePostal("20000");
        return response;
    }

    private ZoneRequestDTO buildRequest() {
        ZoneRequestDTO request = new ZoneRequestDTO();
        request.setNom("Zone");
        request.setCodePostal("20000");
        return request;
    }
}


