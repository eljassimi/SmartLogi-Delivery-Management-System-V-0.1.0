package com.smartlogi.repository;

import com.smartlogi.model.ColisProduit;
import com.smartlogi.model.ColisProduitId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColisProduitRepository extends JpaRepository<ColisProduit, ColisProduitId> {
}
