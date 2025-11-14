package com.smartlogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartlogi.dto.historiquelivraison.HistoriqueLivraisonRequestDTO;
import com.smartlogi.dto.historiquelivraison.HistoriqueLivraisonResponseDTO;
import com.smartlogi.service.HistoriqueLivraisonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HistoriqueLivraisonController.class)
class HistoriqueLivraisonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HistoriqueLivraisonService service;

    @Test
    void getAll_returnsList() throws Exception {
        HistoriqueLivraisonResponseDTO response1 = buildResponse("hist-1");
        HistoriqueLivraisonResponseDTO response2 = buildResponse("hist-2");
        when(service.findAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/api/historiques"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("hist-1")));

        verify(service).findAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    void create_returnsCreatedHistorique() throws Exception {
        HistoriqueLivraisonRequestDTO request = buildRequest();
        HistoriqueLivraisonResponseDTO response = buildResponse("created");
        when(service.save(any(HistoriqueLivraisonRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/historiques")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("created")));

        verify(service).save(any(HistoriqueLivraisonRequestDTO.class));
        verifyNoMoreInteractions(service);
    }

    private HistoriqueLivraisonRequestDTO buildRequest() {
        HistoriqueLivraisonRequestDTO request = new HistoriqueLivraisonRequestDTO();
        request.setColisId("colis-1");
        request.setStatut("EN_COURS");
        request.setDateChangement(LocalDateTime.of(2024, 1, 1, 10, 0));
        request.setCommentaire("Commentaire");
        return request;
    }

    private HistoriqueLivraisonResponseDTO buildResponse(String id) {
        HistoriqueLivraisonResponseDTO response = new HistoriqueLivraisonResponseDTO();
        response.setId(id);
        response.setColisId("colis-1");
        response.setStatut("EN_COURS");
        response.setDateChangement(LocalDateTime.of(2024, 1, 1, 10, 0));
        response.setCommentaire("Commentaire");
        return response;
    }
}


