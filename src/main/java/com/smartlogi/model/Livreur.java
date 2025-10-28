package com.smartlogi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

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

    @OneToMany(mappedBy = "livreur")
    @JsonManagedReference(value = "livreur-colis")
    private List<Colis> colis;
}
