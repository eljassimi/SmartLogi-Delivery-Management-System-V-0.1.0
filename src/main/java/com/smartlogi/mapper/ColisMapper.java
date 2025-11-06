package com.smartlogi.mapper;

import com.smartlogi.dto.colis.ColisRequestDTO;
import com.smartlogi.dto.colis.ColisResponseDTO;
import com.smartlogi.model.ClientExpediteur;
import com.smartlogi.model.Colis;
import com.smartlogi.model.Destinataire;
import com.smartlogi.model.Livreur;
import com.smartlogi.model.Zone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ColisMapper {

	@Mapping(source = "clientExpediteur.id", target = "clientExpediteurId")
	@Mapping(source = "destinataire.id", target = "destinataireId")
	@Mapping(source = "livreur.id", target = "livreurId")
	@Mapping(source = "zone.id", target = "zoneId")
	ColisResponseDTO toResponse(Colis entity);

	@Mapping(source = "clientExpediteurId", target = "clientExpediteur")
	@Mapping(source = "destinataireId", target = "destinataire")
	@Mapping(source = "livreurId", target = "livreur")
	@Mapping(source = "zoneId", target = "zone")
	Colis toEntity(ColisRequestDTO request);

	default ClientExpediteur mapClientExpediteur(String id) {
		if (id == null) return null;
		ClientExpediteur e = new ClientExpediteur();
		e.setId(id);
		return e;
	}

	default Destinataire mapDestinataire(String id) {
		if (id == null) return null;
		Destinataire e = new Destinataire();
		e.setId(id);
		return e;
	}

	default Livreur mapLivreur(String id) {
		if (id == null) return null;
		Livreur e = new Livreur();
		e.setId(id);
		return e;
	}

	default Zone mapZone(String id) {
		if (id == null) return null;
		Zone z = new Zone();
		z.setId(id);
		return z;
	}
}


