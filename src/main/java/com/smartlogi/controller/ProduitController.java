package com.smartlogi.controller;

import com.smartlogi.dto.produit.ProduitRequestDTO;
import com.smartlogi.dto.produit.ProduitResponseDTO;
import com.smartlogi.service.ProduitService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
public class ProduitController {

    private final ProduitService produitService;

    public ProduitController(ProduitService produitService){
        this.produitService = produitService;
    }

    @GetMapping("/{produitId}")
    public ProduitResponseDTO getById(@PathVariable String produitId) {
        return produitService.findById(produitId);
    }
    @GetMapping
    public List<ProduitResponseDTO> getAllProduits(){
        return  produitService.findAll();
    }
    @PostMapping
    public ProduitResponseDTO createProduit(@Valid @RequestBody ProduitRequestDTO request){
        return produitService.save(request);
    }

    @PutMapping("/{produitId}")
    public ProduitResponseDTO updateProduit(@PathVariable String produitId, @Valid @RequestBody ProduitRequestDTO request){
        return produitService.update(produitId, request);
    }

    @DeleteMapping("/{produitId}")
    public void deleteProduit(@PathVariable String produitId){
        produitService.delete(produitId);
    }
}
