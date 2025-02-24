package com.groupeisi.matieres.dto.requests;

import lombok.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MatiereDtoRequest {
    private Long id;
    @NotBlank(message = "Le nom de la matière est obligatoire.")
    private String nom;

    private String description;

    private boolean archive;

   @NotNull(message = "L'ID du cours est obligatoire.")
   private Long coursId;
}