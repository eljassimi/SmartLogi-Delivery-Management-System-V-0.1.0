package com.smartlogi.dto.destinataire;

import lombok.Data;

@Data
public class DestinataireResponseDTO {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
}
