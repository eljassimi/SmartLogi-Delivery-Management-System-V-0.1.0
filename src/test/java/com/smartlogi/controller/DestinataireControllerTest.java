package com.smartlogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartlogi.dto.destinataire.DestinataireRequestDTO;
import com.smartlogi.dto.destinataire.DestinataireResponseDTO;
import com.smartlogi.service.DestinataireService;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DestinataireController.class)
class DestinataireControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DestinataireService service;

    @Test
    void getAll_returnsList() throws Exception {
        DestinataireResponseDTO response1 = buildResponse("dest-1");
        DestinataireResponseDTO response2 = buildResponse("dest-2");
        when(service.findAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/api/destinataires"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("dest-1")));

        verify(service).findAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    void getById_returnsDestinataire() throws Exception {
        DestinataireResponseDTO response = buildResponse("dest-1");
        when(service.findById("dest-1")).thenReturn(response);

        mockMvc.perform(get("/api/destinataires/{id}", "dest-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("dest-1")));

        verify(service).findById("dest-1");
        verifyNoMoreInteractions(service);
    }

    @Test
    void create_returnsCreatedDestinataire() throws Exception {
        DestinataireRequestDTO request = buildRequest();
        DestinataireResponseDTO response = buildResponse("created");
        when(service.save(any(DestinataireRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/destinataires")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("created")));

        verify(service).save(any(DestinataireRequestDTO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void delete_returnsOk() throws Exception {
        doNothing().when(service).delete("dest-1");

        mockMvc.perform(delete("/api/destinataires/{id}", "dest-1"))
                .andExpect(status().isOk());

        verify(service).delete("dest-1");
        verifyNoMoreInteractions(service);
    }

    private DestinataireRequestDTO buildRequest() {
        DestinataireRequestDTO request = new DestinataireRequestDTO();
        request.setNom("Nom");
        request.setPrenom("Prenom");
        request.setEmail("dest@example.com");
        request.setTelephone("0606060606");
        request.setAdresse("Adresse");
        return request;
    }

    private DestinataireResponseDTO buildResponse(String id) {
        DestinataireResponseDTO response = new DestinataireResponseDTO();
        response.setId(id);
        response.setNom("Nom");
        response.setPrenom("Prenom");
        response.setEmail("dest@example.com");
        response.setTelephone("0606060606");
        response.setAdresse("Adresse");
        return response;
    }
}


