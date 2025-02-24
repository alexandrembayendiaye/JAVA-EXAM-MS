package com.groupeisi.sessions.repository;

import com.groupeisi.sessions.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    Optional<Session> findById(Long aLong);
    Optional<Session> findByNom(String nom);
}
