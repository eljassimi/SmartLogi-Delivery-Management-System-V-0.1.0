package com.smartlogi.service;

import com.smartlogi.dto.historiquelivraison.HistoriqueLivraisonRequestDTO;
import com.smartlogi.dto.historiquelivraison.HistoriqueLivraisonResponseDTO;
import com.smartlogi.mapper.HistoriqueLivraisonMapper;
import com.smartlogi.model.HistoriqueLivraison;
import com.smartlogi.repository.HistoriqueLivraisonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class HistoriqueLivraisonServiceTest {

    @Mock
    private HistoriqueLivraisonRepository repository;

    @Mock
    private HistoriqueLivraisonMapper mapper;

    @InjectMocks
    private HistoriqueLivraisonService service;

    @Test
    void findAll_returnsMappedResponses() {
        HistoriqueLivraison entity1 = buildEntity("1");
        HistoriqueLivraison entity2 = buildEntity("2");
        HistoriqueLivraisonResponseDTO response1 = buildResponse("1");
        HistoriqueLivraisonResponseDTO response2 = buildResponse("2");

        when(repository.findAll()).thenReturn(List.of(entity1, entity2));
        when(mapper.toResponse(entity1)).thenReturn(response1);
        when(mapper.toResponse(entity2)).thenReturn(response2);

        List<HistoriqueLivraisonResponseDTO> result = service.findAll();

        assertEquals(List.of(response1, response2), result);
        verify(repository).findAll();
        verify(mapper).toResponse(entity1);
        verify(mapper).toResponse(entity2);
        verifyNoMoreInteractions(repository, mapper);
    }

    @Test
    void save_persistsEntityAndReturnsResponse() {
        HistoriqueLivraisonRequestDTO request = buildRequest();
        HistoriqueLivraison entity = buildEntity(null);
        HistoriqueLivraison saved = buildEntity("saved");
        HistoriqueLivraisonResponseDTO response = buildResponse("saved");

        when(mapper.toEntity(request)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(saved);
        when(mapper.toResponse(saved)).thenReturn(response);

        HistoriqueLivraisonResponseDTO result = service.save(request);

        assertSame(response, result);
        verify(mapper).toEntity(request);
        verify(repository).save(entity);
        verify(mapper).toResponse(saved);
        verifyNoMoreInteractions(repository, mapper);
    }

    private HistoriqueLivraison buildEntity(String id) {
        HistoriqueLivraison entity = new HistoriqueLivraison();
        entity.setId(id);
        entity.setStatut("EN_COURS");
        entity.setDateChangement(LocalDateTime.of(2024, 1, 1, 12, 0));
        entity.setCommentaire("Commentaire");
        return entity;
    }

    private HistoriqueLivraisonResponseDTO buildResponse(String id) {
        HistoriqueLivraisonResponseDTO response = new HistoriqueLivraisonResponseDTO();
        response.setId(id);
        response.setColisId("colis");
        response.setStatut("EN_COURS");
        response.setDateChangement(LocalDateTime.of(2024, 1, 1, 12, 0));
        response.setCommentaire("Commentaire");
        return response;
    }

    private HistoriqueLivraisonRequestDTO buildRequest() {
        HistoriqueLivraisonRequestDTO request = new HistoriqueLivraisonRequestDTO();
        request.setColisId("colis");
        request.setStatut("EN_COURS");
        request.setDateChangement(LocalDateTime.of(2024, 1, 1, 12, 0));
        request.setCommentaire("Commentaire");
        return request;
    }
}


