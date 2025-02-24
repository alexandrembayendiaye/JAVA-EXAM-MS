package com.groupeisi.sessions.entities;

import com.groupeisi.cours.entities.Cours;
import com.groupeisi.enseignants.entities.Enseignant;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "sessions")
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String nom;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String description;
    private boolean archive = false;

    @ManyToOne
    @JoinColumn(name = "enseignant_id") // Assurez-vous que la colonne est bien présente
    private Enseignant enseignant;

    // Relation Many-to-One avec Cours
    @ManyToOne
    @JoinColumn(name = "cours_id")
    private Cours cours;
}