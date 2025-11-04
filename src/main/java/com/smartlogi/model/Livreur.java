package com.smartlogi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Schema(hidden = true)
public class Livreur {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nom;
    private String prenom;
    private String telephone;
    private String vehicule;
    private String zoneAssignee;

    @OneToMany(mappedBy = "livreur")
    @JsonManagedReference(value = "livreur-colis")
    private List<Colis> colis;
}
