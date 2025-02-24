package com.groupeisi.enseignants.dto.responses;


import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EnseignantDtoResponse {
    private Long id;
    private String prenom;
    private String nom;
    private String emailPro;
    private String numeroTelephone;
    private String adresse;
    private boolean archive;
}
