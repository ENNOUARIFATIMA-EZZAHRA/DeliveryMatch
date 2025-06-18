package com.DeliveryMatch.repository;

import com.DeliveryMatch.model.Colis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColisRepository extends JpaRepository<Colis, Integer> {
} 