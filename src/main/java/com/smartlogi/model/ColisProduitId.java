package com.smartlogi.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ColisProduitId implements Serializable {

    private Long colisId;
    private Long produitId;

    public ColisProduitId() {}

    public ColisProduitId(Long colisId, Long produitId) {
        this.colisId = colisId;
        this.produitId = produitId;
    }
}
