package com.smartlogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartlogi.dto.colisproduit.ColisProduitRequestDTO;
import com.smartlogi.dto.colisproduit.ColisProduitResponseDTO;
import com.smartlogi.model.ColisProduitId;
import com.smartlogi.service.ColisProduitService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ColisProduitController.class)
class ColisProduitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ColisProduitService service;

    @Test
    void getAll_returnsList() throws Exception {
        ColisProduitResponseDTO response1 = buildResponse("colis-1", "prod-1");
        ColisProduitResponseDTO response2 = buildResponse("colis-2", "prod-2");
        when(service.findAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/api/colis-produits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].colisId", is("colis-1")))
                .andExpect(jsonPath("$[1].colisId", is("colis-2")));

        verify(service).findAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    void getById_returnsAssociation() throws Exception {
        ColisProduitResponseDTO response = buildResponse("colis-1", "prod-1");
        ColisProduitId id = new ColisProduitId("colis-1", "prod-1");
        when(service.findById(id)).thenReturn(response);

        mockMvc.perform(get("/api/colis-produits/colis/{colisId}/produit/{produitId}", "colis-1", "prod-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.colisId", is("colis-1")))
                .andExpect(jsonPath("$.produitId", is("prod-1")));

        verify(service).findById(id);
        verifyNoMoreInteractions(service);
    }

    @Test
    void create_returnsCreatedAssociation() throws Exception {
        ColisProduitRequestDTO request = buildRequest();
        ColisProduitResponseDTO response = buildResponse("colis-1", "prod-1");
        when(service.save(any(ColisProduitRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/colis-produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.colisId", is("colis-1")))
                .andExpect(jsonPath("$.produitId", is("prod-1")));

        verify(service).save(any(ColisProduitRequestDTO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void delete_returnsOk() throws Exception {
        ColisProduitId id = new ColisProduitId("colis-1", "prod-1");
        doNothing().when(service).delete(id);

        mockMvc.perform(delete("/api/colis-produits/colis/{colisId}/produit/{produitId}", "colis-1", "prod-1"))
                .andExpect(status().isOk());

        verify(service).delete(id);
        verifyNoMoreInteractions(service);
    }

    private ColisProduitRequestDTO buildRequest() {
        ColisProduitRequestDTO request = new ColisProduitRequestDTO();
        request.setColisId("colis-1");
        request.setProduitId("prod-1");
        request.setQuantite(2);
        request.setPrix(99.99);
        request.setDateAjout(LocalDate.now());
        return request;
    }

    private ColisProduitResponseDTO buildResponse(String colisId, String produitId) {
        ColisProduitResponseDTO response = new ColisProduitResponseDTO();
        response.setColisId(colisId);
        response.setProduitId(produitId);
        response.setQuantite(2);
        response.setPrix(99.99);
        response.setDateAjout(LocalDate.now());
        return response;
    }
}

