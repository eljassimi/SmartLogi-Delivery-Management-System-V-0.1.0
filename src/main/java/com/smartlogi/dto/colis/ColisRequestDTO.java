package com.smartlogi.dto.colis;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

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
    private String livreurId;
    private String zoneId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateCreation;
}
