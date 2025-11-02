package com.smartlogi.dto.colisproduit;

import java.time.LocalDate;
import lombok.Data;

@Data
public class ColisProduitResponseDTO {
    private String colisId;
    private String produitId;
    private Integer quantite;
    private Double prix;
    private LocalDate dateAjout;
}
