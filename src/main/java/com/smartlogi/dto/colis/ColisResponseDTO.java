package com.smartlogi.dto.colis;

import lombok.Data;

@Data
public class ColisResponseDTO {
    private String id;
    private String description;
    private Double poids;
    private String statut;
    private String priorite;
    private String villeDestination;
    private String clientExpediteurId;
    private String destinataireId;
    private String livreurId;
}
