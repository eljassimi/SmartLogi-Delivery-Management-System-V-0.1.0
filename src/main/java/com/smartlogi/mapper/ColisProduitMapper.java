package com.smartlogi.mapper;

import com.smartlogi.dto.colisproduit.ColisProduitRequestDTO;
import com.smartlogi.dto.colisproduit.ColisProduitResponseDTO;
import com.smartlogi.model.Colis;
import com.smartlogi.model.ColisProduit;
import com.smartlogi.model.ColisProduitId;
import com.smartlogi.model.Produit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ColisProduitMapper {

	@Mapping(source = "colis.id", target = "colisId")
	@Mapping(source = "produit.id", target = "produitId")
	ColisProduitResponseDTO toResponse(ColisProduit entity);

	default ColisProduit toEntity(ColisProduitRequestDTO request) {
		if (request == null) return null;
		ColisProduit entity = new ColisProduit();
		entity.setId(new ColisProduitId(request.getColisId(), request.getProduitId()));
		entity.setColis(mapColis(request.getColisId()));
		entity.setProduit(mapProduit(request.getProduitId()));
		entity.setQuantite(request.getQuantite());
		entity.setPrix(request.getPrix());
		entity.setDateAjout(request.getDateAjout());
		return entity;
	}

	default Colis mapColis(String id) {
		if (id == null) return null;
		Colis c = new Colis();
		c.setId(id);
		return c;
	}

	default Produit mapProduit(String id) {
		if (id == null) return null;
		Produit p = new Produit();
		p.setId(id);
		return p;
	}
}


