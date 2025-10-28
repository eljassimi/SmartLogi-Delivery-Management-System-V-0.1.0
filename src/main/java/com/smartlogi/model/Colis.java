package com.smartlogi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference(value = "client-colis")
    @JoinColumn(name = "client_expediteur_id")
    private ClientExpediteur clientExpediteur;

    @ManyToOne
    @JsonBackReference(value = "destinataire-colis")
    @JoinColumn(name = "destinataire_id")
    private Destinataire destinataire;

    @ManyToOne
    @JsonBackReference(value = "livreur-colis")
    @JoinColumn(name = "livreur_id")
    private Livreur livreur;

    @OneToMany(mappedBy = "colis")
    @JsonManagedReference(value = "colis-produit")
    private Set<ColisProduit> produits = new HashSet<>();

    @OneToMany(mappedBy = "colis")
    @JsonManagedReference(value = "colis-historique")
    private List<HistoriqueLivraison> historiqueLivraisons;
}
