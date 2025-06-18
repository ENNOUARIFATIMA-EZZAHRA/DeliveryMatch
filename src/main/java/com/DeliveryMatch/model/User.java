package com.DeliveryMatch.model;

import jakarta.persistence.*;
import java.util.Date;

import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "user_type")
@Data
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String nom;
    private String prenom;
    private String email;
    private String motDePass;
    
    private Date dateInscription;

    @Enumerated(EnumType.STRING)
    private UserRole role;
}
