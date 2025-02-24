package com.groupeisi.enseignants.dto.requests;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EnseignantDtoRequest {
    private Long id;
    @NotBlank(message = "Le prénom est obligatoire")
    private String prenom;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @Email(message = "Veuillez fournir un email professionnel valide")
    @NotBlank(message = "L'email professionnel est obligatoire")
    private String emailPro;

    private String numeroTelephone;
    private String adresse;
    private boolean archive = false;
}
