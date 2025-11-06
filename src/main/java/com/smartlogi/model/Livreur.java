package com.smartlogi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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

    @ManyToOne
    @JsonBackReference(value = "zone-livreur")
    @JoinColumn(name = "zone_assignee_id")
    private Zone zoneAssignee;

    @OneToMany(mappedBy = "livreur")
    @JsonManagedReference(value = "livreur-colis")
    private List<Colis> colis;
}
