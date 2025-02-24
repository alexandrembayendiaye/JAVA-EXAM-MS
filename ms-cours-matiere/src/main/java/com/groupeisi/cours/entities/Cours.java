package com.groupeisi.cours.entities;
import com.groupeisi.matieres.entities.Matiere;
import com.groupeisi.sessions.entities.Session;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "cours")
public class Cours {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @Column(nullable = false)
    private String description;

    private boolean archive = false;

     //Relation One-to-Many avec Matiere
     @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL, orphanRemoval = true)
     private List<Matiere> matieres;

    // Relation One-to-Many avec Session
      @OneToMany(mappedBy = "cours", cascade = CascadeType.ALL, orphanRemoval = true)
      private List<Session> sessions;
}
