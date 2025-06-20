package com.DeliveryMatch.repository;

import com.DeliveryMatch.model.Demande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DemandeTransportRepository extends JpaRepository<Demande, Integer> {
    List<Demande> findByExpediteurId(Integer expediteurId);
}
