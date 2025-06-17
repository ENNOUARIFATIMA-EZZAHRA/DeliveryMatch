package com.DeliveryMatch.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String dimensionsColis;
    private float poids;
    private String typeColis;
    private String status;
    private LocalDateTime dateDemande;

    @ManyToOne
    @JoinColumn(name = "expediteur_id")
    private Expediteur expediteur;

    @ManyToOne
    @JoinColumn(name = "annonce_id")
    private Annonce annonce;

    // Getters, Setters, Constructeurs
}

