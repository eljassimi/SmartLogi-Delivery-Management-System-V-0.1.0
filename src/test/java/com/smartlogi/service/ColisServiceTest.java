package com.smartlogi.service;

import com.smartlogi.dto.colis.ColisRequestDTO;
import com.smartlogi.dto.colis.ColisResponseDTO;
import com.smartlogi.exception.ResourceNotFoundException;
import com.smartlogi.mapper.ColisMapper;
import com.smartlogi.model.Colis;
import com.smartlogi.repository.ColisRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ColisServiceTest {

    @Mock
    private ColisRepository repository;

    @Mock
    private ColisMapper mapper;

    @InjectMocks
    private ColisService service;

    @Test
    void search_withStatutUsesSpecificationAndProvidedPageable() {
        Pageable pageable = PageRequest.of(1, 5, Sort.by("dateCreation"));
        Colis entity = buildEntity("1");
        ColisResponseDTO response = buildResponse("1");
        Page<Colis> page = new PageImpl<>(List.of(entity), pageable, 1);

        when(repository.findAll(org.mockito.ArgumentMatchers.<Specification<Colis>>any(), any(Pageable.class))).thenReturn(page);
        when(mapper.toResponse(entity)).thenReturn(response);

        Page<ColisResponseDTO> result = service.search("EN_COURS", pageable);

        assertEquals(List.of(response), result.getContent());
        ArgumentCaptor<Specification<Colis>> specCaptor = ArgumentCaptor.forClass(Specification.class);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(repository).findAll(specCaptor.capture(), pageableCaptor.capture());
        assertNotNull(specCaptor.getValue());
        assertSame(pageable, pageableCaptor.getValue());
        verify(mapper).toResponse(entity);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void search_withoutPageableUsesDefaultAndNullSpecification() {
        Page<Colis> page = new PageImpl<>(List.of());
        when(repository.findAll(org.mockito.ArgumentMatchers.<Specification<Colis>>any(), any(Pageable.class))).thenReturn(page);

        Page<ColisResponseDTO> result = service.search(null, null);

        assertEquals(List.of(), result.getContent());
        ArgumentCaptor<Specification<Colis>> specCaptor = ArgumentCaptor.forClass(Specification.class);
        ArgumentCaptor<Pageable> pageableCaptor = ArgumentCaptor.forClass(Pageable.class);
        verify(repository).findAll(specCaptor.capture(), pageableCaptor.capture());
        assertNull(specCaptor.getValue());
        Pageable expectedPageable = PageRequest.of(0, 20, Sort.by(Sort.Direction.DESC, "dateCreation"));
        assertEquals(expectedPageable, pageableCaptor.getValue());
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void findById_returnsResponse_whenExists() {
        String id = "colis-id";
        Colis entity = buildEntity(id);
        ColisResponseDTO response = buildResponse(id);

        when(repository.findById(id)).thenReturn(Optional.of(entity));
        when(mapper.toResponse(entity)).thenReturn(response);

        ColisResponseDTO result = service.findById(id);

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
    void save_setsDateIfMissingAndReturnsResponse() {
        ColisRequestDTO request = buildRequest();
        Colis entity = buildEntity(null);
        entity.setDateCreation(null);
        Colis saved = buildEntity("saved");
        saved.setDateCreation(LocalDate.now());
        ColisResponseDTO response = buildResponse("saved");

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        ColisResponseDTO result = service.save(request);

        assertSame(response, result);
        assertNotNull(entity.getDateCreation());
        verify(mapper).toEntity(request);
        verify(repository).save(entity);
        verify(mapper).toResponse(saved);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void delete_removes_whenExists() {
        String id = "delete-me";
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

    private Colis buildEntity(String id) {
        Colis entity = new Colis();
        entity.setId(id);
        entity.setDescription("Description");
        entity.setPoids(2.5);
        entity.setStatut("EN_COURS");
        entity.setPriorite("HIGH");
        entity.setVilleDestination("Casablanca");
        entity.setDateCreation(LocalDate.now());
        return entity;
    }

    private ColisResponseDTO buildResponse(String id) {
        ColisResponseDTO response = new ColisResponseDTO();
        response.setId(id);
        response.setDescription("Description");
        response.setPoids(2.5);
        response.setStatut("EN_COURS");
        response.setPriorite("HIGH");
        response.setVilleDestination("Casablanca");
        response.setDateCreation(LocalDate.now());
        return response;
    }

    private ColisRequestDTO buildRequest() {
        ColisRequestDTO request = new ColisRequestDTO();
        request.setDescription("Description");
        request.setPoids(2.5);
        request.setStatut("EN_COURS");
        request.setPriorite("HIGH");
        request.setVilleDestination("Casablanca");
        request.setClientExpediteurId("client");
        request.setDestinataireId("dest");
        request.setLivreurId("livreur");
        request.setZoneId("zone");
        return request;
    }
}

