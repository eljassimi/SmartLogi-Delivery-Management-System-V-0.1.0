package com.smartlogi.dto.produit;

import lombok.Data;

@Data
public class ProduitResponseDTO {
    private String id;
    private String nom;
    private String categorie;
    private Double poids;
    private Double prix;
}
