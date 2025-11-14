package com.smartlogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartlogi.dto.produit.ProduitRequestDTO;
import com.smartlogi.dto.produit.ProduitResponseDTO;
import com.smartlogi.service.ProduitService;
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

@WebMvcTest(controllers = ProduitController.class)
class ProduitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProduitService produitService;

    @Test
    void getById_returnsProduit() throws Exception {
        ProduitResponseDTO response = buildResponse("prod-1");
        when(produitService.findById("prod-1")).thenReturn(response);

        mockMvc.perform(get("/api/produits/{id}", "prod-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("prod-1")))
                .andExpect(jsonPath("$.nom", is("Produit A")));

        verify(produitService).findById("prod-1");
        verifyNoMoreInteractions(produitService);
    }

    @Test
    void getAllProduits_returnsList() throws Exception {
        ProduitResponseDTO response1 = buildResponse("prod-1");
        ProduitResponseDTO response2 = buildResponse("prod-2");
        when(produitService.findAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/api/produits"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("prod-1")))
                .andExpect(jsonPath("$[1].id", is("prod-2")));

        verify(produitService).findAll();
        verifyNoMoreInteractions(produitService);
    }

    @Test
    void createProduit_returnsCreatedProduit() throws Exception {
        ProduitRequestDTO request = buildRequest();
        ProduitResponseDTO response = buildResponse("created");
        when(produitService.save(any(ProduitRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/produits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("created")));

        verify(produitService).save(any(ProduitRequestDTO.class));
        verifyNoMoreInteractions(produitService);
    }

    @Test
    void updateProduit_returnsUpdatedProduit() throws Exception {
        ProduitRequestDTO request = buildRequest();
        ProduitResponseDTO response = buildResponse("prod-1");
        when(produitService.update(eq("prod-1"), any(ProduitRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/api/produits/{id}", "prod-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("prod-1")));

        verify(produitService).update(eq("prod-1"), any(ProduitRequestDTO.class));
        verifyNoMoreInteractions(produitService);
    }

    @Test
    void deleteProduit_returnsNoContent() throws Exception {
        doNothing().when(produitService).delete("prod-1");

        mockMvc.perform(delete("/api/produits/{id}", "prod-1"))
                .andExpect(status().isOk());

        verify(produitService).delete("prod-1");
        verifyNoMoreInteractions(produitService);
    }

    private ProduitRequestDTO buildRequest() {
        ProduitRequestDTO request = new ProduitRequestDTO();
        request.setNom("Produit A");
        request.setCategorie("CAT");
        request.setPoids(1.2);
        request.setPrix(10.5);
        return request;
    }

    private ProduitResponseDTO buildResponse(String id) {
        ProduitResponseDTO response = new ProduitResponseDTO();
        response.setId(id);
        response.setNom("Produit A");
        response.setCategorie("CAT");
        response.setPoids(1.2);
        response.setPrix(10.5);
        return response;
    }
}


