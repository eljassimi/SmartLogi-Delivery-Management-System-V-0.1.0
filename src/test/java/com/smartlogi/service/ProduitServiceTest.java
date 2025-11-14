package com.smartlogi.service;

import com.smartlogi.dto.produit.ProduitRequestDTO;
import com.smartlogi.dto.produit.ProduitResponseDTO;
import com.smartlogi.exception.ResourceNotFoundException;
import com.smartlogi.mapper.ProduitMapper;
import com.smartlogi.model.Produit;
import com.smartlogi.repository.ProduitRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProduitServiceTest {

    @Mock
    private ProduitRepository produitRepository;

    @Mock
    private ProduitMapper produitMapper;

    @InjectMocks
    private ProduitService produitService;

    @Test
    void findAll_returnsMappedResponses() {
        Produit produit1 = buildProduit("1");
        Produit produit2 = buildProduit("2");
        ProduitResponseDTO response1 = buildResponse("1");
        ProduitResponseDTO response2 = buildResponse("2");

        when(produitRepository.findAll()).thenReturn(List.of(produit1, produit2));
        when(produitMapper.toResponse(produit1)).thenReturn(response1);
        when(produitMapper.toResponse(produit2)).thenReturn(response2);

        List<ProduitResponseDTO> result = produitService.findAll();

        assertEquals(List.of(response1, response2), result);
        verify(produitRepository).findAll();
        verify(produitMapper).toResponse(produit1);
        verify(produitMapper).toResponse(produit2);
        verifyNoMoreInteractions(produitRepository, produitMapper);
    }

    @Test
    void findById_returnsMappedResponse_whenProduitExists() {
        String id = "123";
        Produit produit = buildProduit(id);
        ProduitResponseDTO response = buildResponse(id);

        when(produitRepository.findById(id)).thenReturn(Optional.of(produit));
        when(produitMapper.toResponse(produit)).thenReturn(response);

        ProduitResponseDTO result = produitService.findById(id);

        assertSame(response, result);
        verify(produitRepository).findById(id);
        verify(produitMapper).toResponse(produit);
        verifyNoMoreInteractions(produitRepository, produitMapper);
    }

    @Test
    void findById_throwsException_whenProduitDoesNotExist() {
        String id = "missing";
        when(produitRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> produitService.findById(id));

        verify(produitRepository).findById(id);
        verifyNoMoreInteractions(produitRepository, produitMapper);
    }

    @Test
    void save_mapsAndPersistsProduit() {
        ProduitRequestDTO request = buildRequest();
        Produit produitToSave = buildProduit(null);
        Produit savedProduit = buildProduit("saved-id");
        ProduitResponseDTO response = buildResponse("saved-id");

        when(produitMapper.toEntity(request)).thenReturn(produitToSave);
        when(produitRepository.save(produitToSave)).thenReturn(savedProduit);
        when(produitMapper.toResponse(savedProduit)).thenReturn(response);

        ProduitResponseDTO result = produitService.save(request);

        assertSame(response, result);
        verify(produitMapper).toEntity(request);
        verify(produitRepository).save(produitToSave);
        verify(produitMapper).toResponse(savedProduit);
        verifyNoMoreInteractions(produitRepository, produitMapper);
    }

    @Test
    void update_updatesExistingProduit() {
        String id = "to-update";
        ProduitRequestDTO request = buildRequest();
        Produit existing = buildProduit(id);
        ProduitResponseDTO response = buildResponse(id);

        when(produitRepository.findById(id)).thenReturn(Optional.of(existing));
        when(produitRepository.save(existing)).thenReturn(existing);
        when(produitMapper.toResponse(existing)).thenReturn(response);

        ProduitResponseDTO result = produitService.update(id, request);

        assertSame(response, result);
        assertEquals(request.getNom(), existing.getNom());
        assertEquals(request.getCategorie(), existing.getCategorie());
        assertEquals(request.getPoids(), existing.getPoids());
        assertEquals(request.getPrix(), existing.getPrix());

        verify(produitRepository).findById(id);
        verify(produitRepository).save(existing);
        verify(produitMapper).toResponse(existing);
        verifyNoMoreInteractions(produitRepository, produitMapper);
    }

    @Test
    void update_throwsException_whenProduitDoesNotExist() {
        String id = "missing";
        ProduitRequestDTO request = buildRequest();
        when(produitRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> produitService.update(id, request));

        verify(produitRepository).findById(id);
        verifyNoMoreInteractions(produitRepository);
        verifyNoMoreInteractions(produitMapper);
    }

    @Test
    void delete_removesProduit_whenExists() {
        String id = "delete-me";
        when(produitRepository.existsById(id)).thenReturn(true);
        doNothing().when(produitRepository).deleteById(id);

        produitService.delete(id);

        verify(produitRepository).existsById(id);
        verify(produitRepository).deleteById(id);
        verifyNoMoreInteractions(produitRepository);
        verifyNoMoreInteractions(produitMapper);
    }

    @Test
    void delete_throwsException_whenProduitDoesNotExist() {
        String id = "missing";
        when(produitRepository.existsById(id)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> produitService.delete(id));

        verify(produitRepository).existsById(id);
        verify(produitRepository, never()).deleteById(id);
        verifyNoMoreInteractions(produitRepository);
        verifyNoMoreInteractions(produitMapper);
    }

    private Produit buildProduit(String id) {
        Produit produit = new Produit();
        produit.setId(id);
        produit.setNom("Produit Test");
        produit.setCategorie("Catégorie");
        produit.setPoids(1.0);
        produit.setPrix(10.0);
        return produit;
    }

    private ProduitResponseDTO buildResponse(String id) {
        ProduitResponseDTO response = new ProduitResponseDTO();
        response.setId(id);
        response.setNom("Produit Test");
        response.setCategorie("Catégorie");
        response.setPoids(1.0);
        response.setPrix(10.0);
        return response;
    }

    private ProduitRequestDTO buildRequest() {
        ProduitRequestDTO request = new ProduitRequestDTO();
        request.setNom("Produit Test");
        request.setCategorie("Catégorie");
        request.setPoids(1.0);
        request.setPrix(10.0);
        return request;
    }
}


