package com.smartlogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartlogi.dto.colis.ColisRequestDTO;
import com.smartlogi.dto.colis.ColisResponseDTO;
import com.smartlogi.service.ColisService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ColisController.class)
class ColisControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ColisService service;

    @Test
    void getColis_returnsPagedResult() throws Exception {
        ColisResponseDTO response = buildResponse("colis-1");
        Page<ColisResponseDTO> page = new PageImpl<>(List.of(response), PageRequest.of(0, 20), 1);
        when(service.search(eq("EN_COURS"), any(Pageable.class))).thenReturn(page);

        mockMvc.perform(get("/api/colis")
                        .param("statut", "EN_COURS")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sortBy", "poids")
                        .param("sortDir", "asc"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content", hasSize(1)))
                .andExpect(jsonPath("$.content[0].id", is("colis-1")));

        verify(service).search(eq("EN_COURS"), any(Pageable.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void createColis_returnsCreated() throws Exception {
        ColisRequestDTO request = buildRequest();
        ColisResponseDTO response = buildResponse("created");
        when(service.save(any(ColisRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/colis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("created")));

        verify(service).save(any(ColisRequestDTO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void getColisById_returnsColis() throws Exception {
        ColisResponseDTO response = buildResponse("colis-1");
        when(service.findById("colis-1")).thenReturn(response);

        mockMvc.perform(get("/api/colis/{id}", "colis-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("colis-1")));

        verify(service).findById("colis-1");
        verifyNoMoreInteractions(service);
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
        request.setDateCreation(LocalDate.now());
        return request;
    }

    private ColisResponseDTO buildResponse(String id) {
        ColisResponseDTO response = new ColisResponseDTO();
        response.setId(id);
        response.setDescription("Description");
        response.setPoids(2.5);
        response.setStatut("EN_COURS");
        response.setPriorite("HIGH");
        response.setVilleDestination("Casablanca");
        response.setClientExpediteurId("client");
        response.setDestinataireId("dest");
        response.setLivreurId("livreur");
        response.setZoneId("zone");
        response.setDateCreation(LocalDate.now());
        return response;
    }
}


