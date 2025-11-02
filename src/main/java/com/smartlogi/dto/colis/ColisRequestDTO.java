package com.smartlogi.dto.colis;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ColisRequestDTO {
    @NotNull @Size(min = 2, max = 200)
    private String description;
    @NotNull
    private Double poids;
    @NotNull @Size(min = 3, max = 30)
    private String statut;
    @NotNull @Size(min = 3, max = 30)
    private String priorite;
    @NotNull @Size(min = 2, max = 60)
    private String villeDestination;
    @NotNull
    private String clientExpediteurId;
    @NotNull
    private String destinataireId;
    @NotNull
    private String livreurId;
}
