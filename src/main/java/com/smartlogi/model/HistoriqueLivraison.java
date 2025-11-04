package com.smartlogi.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
@Schema(hidden = true)
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
