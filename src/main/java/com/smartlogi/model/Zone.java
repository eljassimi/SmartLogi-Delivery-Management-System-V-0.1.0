package com.smartlogi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Schema(hidden = true)
public class Zone {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    
    private String nom;
    private String codePostal;

    @OneToMany(mappedBy = "zone")
    @JsonManagedReference(value = "zone-colis")
    private List<Colis> colis;

    @OneToMany(mappedBy = "zoneAssignee")
    @JsonManagedReference(value = "zone-livreur")
    private List<Livreur> livreurs;
}


