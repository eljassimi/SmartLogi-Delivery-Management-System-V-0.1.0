package com.smartlogi.dto.colisproduit;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class ColisProduitRequestDTO {
    @NotNull
    private String colisId;
    @NotNull
    private String produitId;
    @NotNull @Min(1)
    private Integer quantite;
    @NotNull
    private Double prix;
    @NotNull
    private LocalDate dateAjout;
}
