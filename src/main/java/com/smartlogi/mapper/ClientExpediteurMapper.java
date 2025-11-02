package com.smartlogi.mapper;

import com.smartlogi.dto.clientexpediteur.ClientExpediteurRequestDTO;
import com.smartlogi.dto.clientexpediteur.ClientExpediteurResponseDTO;
import com.smartlogi.model.ClientExpediteur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClientExpediteurMapper {
	ClientExpediteurResponseDTO toResponse(ClientExpediteur entity);
	ClientExpediteur toEntity(ClientExpediteurRequestDTO request);
}


