package com.smartlogi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Schema(hidden = true)
public class Destinataire {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;

    @OneToMany(mappedBy = "destinataire")
    @JsonManagedReference(value = "destinataire-colis")
    private List<Colis> colis;

}
