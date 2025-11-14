package com.smartlogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartlogi.dto.livreur.LivreurRequestDTO;
import com.smartlogi.dto.livreur.LivreurResponseDTO;
import com.smartlogi.service.LivreurService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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

@WebMvcTest(controllers = LivreurController.class)
class LivreurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private LivreurService service;

    @Test
    void getAllLivreur_returnsList() throws Exception {
        LivreurResponseDTO response1 = buildResponse("livreur-1");
        LivreurResponseDTO response2 = buildResponse("livreur-2");
        when(service.findAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/api/livreurs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("livreur-1")));

        verify(service).findAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    void getLivreurById_returnsLivreur() throws Exception {
        LivreurResponseDTO response = buildResponse("livreur-1");
        when(service.findById("livreur-1")).thenReturn(response);

        mockMvc.perform(get("/api/livreurs/{id}", "livreur-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("livreur-1")));

        verify(service).findById("livreur-1");
        verifyNoMoreInteractions(service);
    }

    @Test
    void createLivreur_returnsCreated() throws Exception {
        LivreurRequestDTO request = buildRequest();
        LivreurResponseDTO response = buildResponse("created");
        when(service.save(any(LivreurRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/livreurs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("created")));

        verify(service).save(any(LivreurRequestDTO.class));
        verifyNoMoreInteractions(service);
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
}


