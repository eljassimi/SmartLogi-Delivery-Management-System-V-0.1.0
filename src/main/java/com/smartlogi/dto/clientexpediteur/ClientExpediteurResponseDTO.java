package com.smartlogi.dto.clientexpediteur;

import lombok.Data;

@Data
public class ClientExpediteurResponseDTO {
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;
}
