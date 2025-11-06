package com.smartlogi.dto.livreur;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LivreurRequestDTO {
    @NotNull @Size(min = 2, max = 100)
    private String nom;
    @NotNull @Size(min = 2, max = 100)
    private String prenom;
    @NotNull @Size(min = 8, max = 20)
    private String telephone;
    @NotNull @Size(min = 3, max = 50)
    private String vehicule;
    private String zoneAssigneeId;
}
