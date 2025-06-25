package com.DeliveryMatch.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

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

    @NotNull(message = "Name is required")
    @NotBlank(message = "Name cannot be empty")
    @Column(nullable = false)
    private String nom;

    @NotNull(message = "First name is required")
    @NotBlank(message = "First name cannot be empty")
    @Column(nullable = false)
    private String prenom;

    @NotNull(message = "Email is required")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "Password is required")
    @NotBlank(message = "Password cannot be empty")
    @Column(nullable = false)
    @JsonIgnore
    private String motDePass;
    
    @Column(nullable = false)

    private LocalDateTime dateInscription;

    private Date dateInscription;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private String status = "ENABLED"; // or SUSPENDED
    
    @Column(nullable = false)
    private boolean verified = false;

    public LocalDateTime getDateInscription() { return dateInscription; }
    public void setDateInscription(LocalDateTime dateInscription) { this.dateInscription = dateInscription; }
}
