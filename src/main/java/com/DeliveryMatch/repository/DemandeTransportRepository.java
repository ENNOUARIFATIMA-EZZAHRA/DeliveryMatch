package com.DeliveryMatch.repository;

import com.DeliveryMatch.model.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DemandeTransportRepository extends JpaRepository<Demande, Integer> {
}
