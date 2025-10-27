package com.smartlogi.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class ColisProduit {

    @EmbeddedId
    private ColisProduitId id;

    @ManyToOne
    @MapsId("colisId")
    @JoinColumn(name = "id_colis")
    private Colis colis;

    @ManyToOne
    @MapsId("produitId")
    @JoinColumn(name = "id_produit")
    private Produit produit;

    private Integer quantite;
    private Double prix;
    private LocalDate dateAjout;
}
