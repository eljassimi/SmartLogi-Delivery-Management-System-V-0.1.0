package com.smartlogi.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class ClientExpediteur {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String adresse;

    @OneToMany(mappedBy = "clientExpediteur")
    @JsonManagedReference(value = "client-colis")
    private List<Colis> colis;

}
