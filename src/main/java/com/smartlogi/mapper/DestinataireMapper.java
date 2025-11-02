package com.smartlogi.mapper;

import com.smartlogi.dto.destinataire.DestinataireRequestDTO;
import com.smartlogi.dto.destinataire.DestinataireResponseDTO;
import com.smartlogi.model.Destinataire;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DestinataireMapper {
	DestinataireResponseDTO toResponse(Destinataire entity);
	Destinataire toEntity(DestinataireRequestDTO request);
}


