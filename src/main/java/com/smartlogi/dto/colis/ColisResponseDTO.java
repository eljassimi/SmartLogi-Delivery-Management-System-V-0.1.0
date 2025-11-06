package com.smartlogi.dto.colis;

import lombok.Data;

import java.time.LocalDate;

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
    private String zoneId;
    private LocalDate dateCreation;
}
