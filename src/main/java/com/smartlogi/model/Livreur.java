package com.smartlogi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Livreur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String telephone;
    private String vehicule;
    private String zoneAssignee;
}
