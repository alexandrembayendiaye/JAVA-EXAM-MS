package com.groupeisi.matieres.repository;

import com.groupeisi.matieres.entities.Matiere;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MatiereRepository extends JpaRepository<Matiere, Long> {
    Optional<Matiere> findById(Long id);
    Optional<Matiere> findByNom(String nom);
}