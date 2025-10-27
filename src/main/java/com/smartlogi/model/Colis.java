package com.smartlogi.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Colis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double poids;
    private String statut;
    private String priorite;
    private String villeDestination;

    @ManyToOne
    private ClientExpediteur clientExpediteur;

    @ManyToOne
    private Destinataire destinataire;
}
