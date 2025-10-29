package com.smartlogi.model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class ColisProduitId implements Serializable {

    private String colisId;
    private String produitId;

    public ColisProduitId() {}

    public ColisProduitId(String colisId, String produitId) {
        this.colisId = colisId;
        this.produitId = produitId;
    }
}
