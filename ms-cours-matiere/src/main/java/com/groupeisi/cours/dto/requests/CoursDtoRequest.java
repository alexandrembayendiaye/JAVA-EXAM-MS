package com.groupeisi.cours.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CoursDtoRequest {
    private Long id;

    @NotBlank(message = "Le nom du cours est obligatoire!")
    private String nom;

    @NotBlank(message = "La description du cours est obligatoire!")
    private String description;

    @NotNull(message = "Le champ archive ne peut pas être null.")
    private boolean archive = false;
}
