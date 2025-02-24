package com.groupeisi.enseignants.repository;

import com.groupeisi.enseignants.entities.Enseignant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
    @Override
    Optional<Enseignant> findById(Long id);
    Optional<Enseignant> findByEmailPro(String emailPro);
}
