package com.smartlogi.dto.livreur;

import lombok.Data;

@Data
public class LivreurResponseDTO {
    private String id;
    private String nom;
    private String prenom;
    private String telephone;
    private String vehicule;
    private String zoneAssigneeId;
}
