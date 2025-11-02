package com.smartlogi.mapper;

import com.smartlogi.dto.livreur.LivreurRequestDTO;
import com.smartlogi.dto.livreur.LivreurResponseDTO;
import com.smartlogi.model.Livreur;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LivreurMapper {
	LivreurResponseDTO toResponse(Livreur entity);
	Livreur toEntity(LivreurRequestDTO request);
}


