package com.smartlogi.dto.historiquelivraison;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class HistoriqueLivraisonRequestDTO {
    @NotNull
    private String colisId;
    @NotNull @Size(min=2, max=40)
    private String statut;
    @NotNull
    private LocalDateTime dateChangement;
    @NotNull @Size(min=2, max=200)
    private String commentaire;
}
