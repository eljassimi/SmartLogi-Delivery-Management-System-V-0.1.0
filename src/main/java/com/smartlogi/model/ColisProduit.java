package com.smartlogi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
@Schema(hidden = true)
public class ColisProduit {

    @EmbeddedId
    private ColisProduitId id;

    @ManyToOne
    @JsonBackReference(value = "colis-produit")
    @MapsId("colisId")
    @JoinColumn(name = "id_colis")
    private Colis colis;

    @ManyToOne
    @JsonBackReference(value = "produit-colis")
    @MapsId("produitId")
    @JoinColumn(name = "id_produit")
    private Produit produit;

    private Integer quantite;
    private Double prix;
    private LocalDate dateAjout;
}
