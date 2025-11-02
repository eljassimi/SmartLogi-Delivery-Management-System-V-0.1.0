package com.smartlogi.dto.destinataire;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DestinataireRequestDTO {
    @NotNull @Size(min = 2, max = 100)
    private String nom;
    @NotNull @Size(min = 2, max = 100)
    private String prenom;
    @NotNull @Email
    private String email;
    @NotNull @Size(min = 8, max = 20)
    private String telephone;
    @NotNull
    private String adresse;
}
