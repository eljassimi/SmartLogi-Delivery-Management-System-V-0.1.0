package com.smartlogi.dto.produit;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProduitRequestDTO {
    @NotNull @Size(min = 2, max = 100)
    private String nom;
    @NotNull @Size(min = 2, max = 100)
    private String categorie;
    @NotNull
    private Double poids;
    @NotNull
    private Double prix;
}
