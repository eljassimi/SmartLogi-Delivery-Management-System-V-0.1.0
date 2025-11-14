package com.smartlogi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartlogi.dto.zone.ZoneRequestDTO;
import com.smartlogi.dto.zone.ZoneResponseDTO;
import com.smartlogi.service.ZoneService;
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

@WebMvcTest(controllers = ZoneController.class)
class ZoneControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ZoneService service;

    @Test
    void getAllZones_returnsList() throws Exception {
        ZoneResponseDTO response1 = buildResponse("zone-1");
        ZoneResponseDTO response2 = buildResponse("zone-2");
        when(service.findAll()).thenReturn(List.of(response1, response2));

        mockMvc.perform(get("/api/zones"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("zone-1")));

        verify(service).findAll();
        verifyNoMoreInteractions(service);
    }

    @Test
    void getZoneById_returnsZone() throws Exception {
        ZoneResponseDTO response = buildResponse("zone-1");
        when(service.findById("zone-1")).thenReturn(response);

        mockMvc.perform(get("/api/zones/{id}", "zone-1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("zone-1")));

        verify(service).findById("zone-1");
        verifyNoMoreInteractions(service);
    }

    @Test
    void createZone_returnsCreated() throws Exception {
        ZoneRequestDTO request = buildRequest();
        ZoneResponseDTO response = buildResponse("created");
        when(service.save(any(ZoneRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/zones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("created")));

        verify(service).save(any(ZoneRequestDTO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void updateZone_returnsUpdated() throws Exception {
        ZoneRequestDTO request = buildRequest();
        ZoneResponseDTO response = buildResponse("zone-1");
        when(service.update(eq("zone-1"), any(ZoneRequestDTO.class))).thenReturn(response);

        mockMvc.perform(put("/api/zones/{id}", "zone-1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("zone-1")));

        verify(service).update(eq("zone-1"), any(ZoneRequestDTO.class));
        verifyNoMoreInteractions(service);
    }

    @Test
    void deleteZone_returnsOk() throws Exception {
        doNothing().when(service).delete("zone-1");

        mockMvc.perform(delete("/api/zones/{id}", "zone-1"))
                .andExpect(status().isOk());

        verify(service).delete("zone-1");
        verifyNoMoreInteractions(service);
    }

    private ZoneRequestDTO buildRequest() {
        ZoneRequestDTO request = new ZoneRequestDTO();
        request.setNom("Zone");
        request.setCodePostal("20000");
        return request;
    }

    private ZoneResponseDTO buildResponse(String id) {
        ZoneResponseDTO response = new ZoneResponseDTO();
        response.setId(id);
        response.setNom("Zone");
        response.setCodePostal("20000");
        return response;
    }
}


