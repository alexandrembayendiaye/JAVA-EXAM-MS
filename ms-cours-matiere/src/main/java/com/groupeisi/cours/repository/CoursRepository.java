package com.groupeisi.cours.repository;

import com.groupeisi.cours.entities.Cours;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CoursRepository extends JpaRepository<Cours, Long> {
    Optional<Cours> findById(Long id);
    Optional<Cours> findByNom(String nom);
}
