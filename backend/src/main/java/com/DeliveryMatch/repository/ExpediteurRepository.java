package com.DeliveryMatch.repository;

import com.DeliveryMatch.model.Expediteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpediteurRepository extends JpaRepository<Expediteur, Integer> {
    Optional<Expediteur> findByEmail(String email);
}
