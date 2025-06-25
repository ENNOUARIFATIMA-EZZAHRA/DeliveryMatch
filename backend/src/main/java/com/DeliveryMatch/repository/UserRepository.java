package com.DeliveryMatch.repository;

import com.DeliveryMatch.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    boolean existsByEmail(String email);

    // Count drivers
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'CONDUCTEUR'")
    long countConducteurs();

    // Count senders
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'EXPEDITEUR'")
    long countExpediteurs();

    // Count administrators
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = 'ADMINISTRATEUR'")
    long countAdmins();
}
