package com.smartlogi.repository.specification;

import com.smartlogi.model.Colis;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public final class ColisSpecifications {

    private ColisSpecifications() {
    }

    public static Specification<Colis> hasStatut(String statut) {
        return (root, query, cb) -> cb.equal(cb.lower(root.get("statut")), statut.toLowerCase());
    }

    public static Specification<Colis> hasPriorite(String priorite) {
        return (root, query, cb) -> cb.equal(cb.lower(root.get("priorite")), priorite.toLowerCase());
    }

    public static Specification<Colis> hasVilleDestination(String ville) {
        return (root, query, cb) -> cb.like(cb.lower(root.get("villeDestination")), "%" + ville.toLowerCase() + "%");
    }

    public static Specification<Colis> belongsToZone(String zoneId) {
        return (root, query, cb) -> cb.equal(root.get("zone").get("id"), zoneId);
    }

    public static Specification<Colis> createdOnOrAfter(LocalDate date) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("dateCreation"), date);
    }

    public static Specification<Colis> createdOnOrBefore(LocalDate date) {
        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("dateCreation"), date);
    }
}
