package com.DeliveryMatch.repository;

import com.DeliveryMatch.model.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConducteurRepository extends JpaRepository<Conducteur, Integer> {
    @Query("select count (*) nombre_annonce from annonce where id_conducteur=''")

}
