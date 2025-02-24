package com.groupeisi.matieres.entities;

import com.groupeisi.cours.entities.Cours;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "matieres")
public class Matiere {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    private String description;

    private boolean archive = false;

    // Relation Many-to-One avec Cours
    @ManyToOne
    @JoinColumn(name = "cours_id")
    private Cours cours;
}
