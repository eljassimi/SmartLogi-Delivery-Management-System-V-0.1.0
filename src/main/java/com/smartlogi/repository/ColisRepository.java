package com.smartlogi.repository;

import com.smartlogi.model.Colis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ColisRepository extends JpaRepository<Colis, String>, JpaSpecificationExecutor<Colis> {
}
