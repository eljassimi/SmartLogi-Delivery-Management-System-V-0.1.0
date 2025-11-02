package com.smartlogi.mapper;

import com.smartlogi.dto.historiquelivraison.HistoriqueLivraisonRequestDTO;
import com.smartlogi.dto.historiquelivraison.HistoriqueLivraisonResponseDTO;
import com.smartlogi.model.Colis;
import com.smartlogi.model.HistoriqueLivraison;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HistoriqueLivraisonMapper {

	@Mapping(source = "colis.id", target = "colisId")
	HistoriqueLivraisonResponseDTO toResponse(HistoriqueLivraison entity);

	@Mapping(source = "colisId", target = "colis")
	HistoriqueLivraison toEntity(HistoriqueLivraisonRequestDTO request);

	default Colis mapColis(String id) {
		if (id == null) return null;
		Colis c = new Colis();
		c.setId(id);
		return c;
	}
}


