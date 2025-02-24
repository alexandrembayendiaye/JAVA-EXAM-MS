package com.groupeisi.sessions.dto.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SessionDtoRequest {
    private Long id;

    @NotBlank(message = "Le nom de la session est obligatoire.")
    private String nom;

    @NotNull(message = "La date de début est obligatoire.")
    private LocalDateTime dateDebut;

    @NotNull(message = "La date de fin est obligatoire.")
    private LocalDateTime dateFin;

    private String description;
    private boolean archive = false;

    @NotNull(message = "L'ID du cours est obligatoire.")
    private Long coursId;
}
