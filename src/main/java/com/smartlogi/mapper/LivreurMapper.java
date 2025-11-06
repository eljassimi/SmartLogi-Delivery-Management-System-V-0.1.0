package com.smartlogi.mapper;

import com.smartlogi.dto.livreur.LivreurRequestDTO;
import com.smartlogi.dto.livreur.LivreurResponseDTO;
import com.smartlogi.model.Livreur;
import com.smartlogi.model.Zone;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LivreurMapper {
	@Mapping(source = "zoneAssignee.id", target = "zoneAssigneeId")
	LivreurResponseDTO toResponse(Livreur entity);
	
	@Mapping(source = "zoneAssigneeId", target = "zoneAssignee")
	Livreur toEntity(LivreurRequestDTO request);

	default Zone mapZone(String id) {
		if (id == null) return null;
		Zone z = new Zone();
		z.setId(id);
		return z;
	}
}


