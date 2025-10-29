package com.smartlogi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class HistoriqueLivraison {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JsonBackReference(value="colis-historique")
    @JoinColumn(name = "id_colis")
    private Colis colis;

    private String statut;
    private LocalDateTime dateChangement;
    private String commentaire;
}
