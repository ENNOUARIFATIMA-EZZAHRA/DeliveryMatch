package com.DeliveryMatch.repository;

import com.DeliveryMatch.model.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {
    List<Annonce> findByDestinationContainingIgnoreCaseAndDateDepartAndTypeMarchandiseContainingIgnoreCase(
        String destination, Date dateDepart, String typeMarchandise);
}

