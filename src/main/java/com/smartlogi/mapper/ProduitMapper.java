package com.smartlogi.mapper;

import com.smartlogi.dto.produit.ProduitRequestDTO;
import com.smartlogi.dto.produit.ProduitResponseDTO;
import com.smartlogi.model.Produit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProduitMapper {
	ProduitResponseDTO toResponse(Produit entity);
	Produit toEntity(ProduitRequestDTO request);
}


