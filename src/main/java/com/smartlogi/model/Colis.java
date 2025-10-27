package com.smartlogi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToOne
    private Livreur livreur;

    @OneToMany(mappedBy = "colis")
    @JsonManagedReference
    private Set<ColisProduit> produits = new HashSet<>();

    @OneToMany(mappedBy = "colis")
    private List<HistoriqueLivraison> historiqueLivraisons = new ArrayList<>();
}
