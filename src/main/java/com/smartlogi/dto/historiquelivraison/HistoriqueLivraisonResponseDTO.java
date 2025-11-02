package com.smartlogi.dto.historiquelivraison;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class HistoriqueLivraisonResponseDTO {
    private String id;
    private String colisId;
    private String statut;
    private LocalDateTime dateChangement;
    private String commentaire;
}
