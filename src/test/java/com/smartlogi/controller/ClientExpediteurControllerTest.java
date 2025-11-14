package com.smartlogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartlogi.dto.clientexpediteur.ClientExpediteurRequestDTO;
import com.smartlogi.dto.clientexpediteur.ClientExpediteurResponseDTO;
import com.smartlogi.service.ClientExpediteurService;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ClientExpediteurController.class)
class ClientExpediteurControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ClientExpediteurService service;

    @Test
    void getAllClientExpediteur_returnsList() throws Exception {
        ClientExpediteurResponseDTO response1 = buildResponse("client-1");
        ClientExpediteurResponseDTO response2 = buildResponse("client-2");
        when(service.findAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("client-1")))
                .andExpect(jsonPath("$[1].id", is("client-2")));

        verify(service).findAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    void getClientExpediteurById_returnsClient() throws Exception {
        ClientExpediteurResponseDTO response = buildResponse("client-1");
        when(service.findById("client-1")).thenReturn(response);

        mockMvc.perform(get("/api/clients/{id}", "client-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("client-1")));

        verify(service).findById("client-1");
        verifyNoMoreInteractions(service);
    }

    @Test
    void createClientExpediteur_returnsCreatedClient() throws Exception {
        ClientExpediteurRequestDTO request = buildRequest();
        ClientExpediteurResponseDTO response = buildResponse("created");
        when(service.save(any(ClientExpediteurRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("created")));

        verify(service).save(any(ClientExpediteurRequestDTO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void updateClientExpediteur_returnsUpdatedClient() throws Exception {
        ClientExpediteurRequestDTO request = buildRequest();
        ClientExpediteurResponseDTO response = buildResponse("client-1");
        when(service.update(eq("client-1"), any(ClientExpediteurRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/api/clients/{id}", "client-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("client-1")));

        verify(service).update(eq("client-1"), any(ClientExpediteurRequestDTO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void deleteClientExpediteur_returnsOk() throws Exception {
        doNothing().when(service).delete("client-1");

        mockMvc.perform(delete("/api/clients/{id}", "client-1"))
                .andExpect(status().isOk());

        verify(service).delete("client-1");
        verifyNoMoreInteractions(service);
    }

    private ClientExpediteurRequestDTO buildRequest() {
        ClientExpediteurRequestDTO request = new ClientExpediteurRequestDTO();
        request.setNom("Nom");
        request.setPrenom("Prenom");
        request.setEmail("client@example.com");
        request.setTelephone("0606060606");
        request.setAdresse("Adresse");
        return request;
    }

    private ClientExpediteurResponseDTO buildResponse(String id) {
        ClientExpediteurResponseDTO response = new ClientExpediteurResponseDTO();
        response.setId(id);
        response.setNom("Nom");
        response.setPrenom("Prenom");
        response.setEmail("client@example.com");
        response.setTelephone("0606060606");
        response.setAdresse("Adresse");
        return response;
    }
}


