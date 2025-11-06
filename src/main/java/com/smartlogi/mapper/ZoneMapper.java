package com.smartlogi.mapper;

import com.smartlogi.dto.zone.ZoneRequestDTO;
import com.smartlogi.dto.zone.ZoneResponseDTO;
import com.smartlogi.model.Zone;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ZoneMapper {
    ZoneResponseDTO toResponse(Zone entity);
    Zone toEntity(ZoneRequestDTO request);
}


