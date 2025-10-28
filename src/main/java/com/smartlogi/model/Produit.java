package com.smartlogi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nom;
    private String categorie;
    private Double poids;
    private Double prix;

    @OneToMany(mappedBy = "produit")
    @JsonManagedReference(value = "produit-colis")
    private Set<ColisProduit> colis = new HashSet<>();
}
