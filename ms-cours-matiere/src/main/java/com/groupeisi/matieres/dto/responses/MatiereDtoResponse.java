package com.groupeisi.matieres.dto.responses;

import com.groupeisi.cours.dto.responses.CoursDtoResponse;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MatiereDtoResponse {
    private Long id;
    private String nom;
    private String description;
    private boolean archive;
    // On renvoie le nom du cours associé pour une meilleure lisibilité
    private String coursNom;
}