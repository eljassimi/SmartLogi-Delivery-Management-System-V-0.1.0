package com.smartlogi.dto.zone;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ZoneRequestDTO {
    @NotNull
    @Size(min = 2, max = 100)
    private String nom;
    
    @NotNull
    @Size(min = 4, max = 10)
    private String codePostal;
}


