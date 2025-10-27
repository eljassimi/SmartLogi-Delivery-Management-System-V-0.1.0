package com.smartlogi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    private Colis colis;

    @ManyToOne
    @MapsId("produitId")
    @JoinColumn(name = "id_produit")
    @JsonBackReference
    private Produit produit;

    private Integer quantite;
    private Double prix;
    private LocalDate dateAjout;
}
