package com.groupeisi.sessions.dto.responses;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SessionDtoResponse {

    private Long id;
    private String nom;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String description;
    private boolean archive;

    // On renvoie le nom du cours associé pour une meilleure lisibilité
    private String coursNom;

    // On renvoie le nom du cours associé pour une meilleure lisibilité
   // private String EnseignantNom;
}