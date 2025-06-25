package com.DeliveryMatch.repository;

import com.DeliveryMatch.model.Annonce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Integer> {

    @Query("SELECT a FROM Annonce a WHERE LOWER(a.destination) LIKE LOWER(CONCAT('%', :destination, '%')) AND DATE(a.dateDepart) = :dateDepart")
    List<Annonce> searchByDestinationAndDateDepart(@Param("destination") String destination, @Param("dateDepart") java.sql.Date dateDepart);

    List<Annonce> findByDestinationContainingIgnoreCaseAndDateDepart(
        String destination, Date dateDepart);
    
    List<Annonce> findByConducteurId(Integer conducteurId);

}

