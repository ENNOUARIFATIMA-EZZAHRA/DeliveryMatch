package com.DeliveryMatch.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;


@Entity
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String lieuDepart;
    private String destination;
    private String etapes;
    private String dimensionsMax;
    private String typeMarchandise;
    private float capacite;
    private Date dateDepart;
    private String status;

    @ManyToOne
    @JoinColumn(name = "conducteur_id")
    private Conducteur conducteur;

    @OneToMany(mappedBy = "annonce", cascade = CascadeType.ALL)
    private List<Demande> demandes;

    // Getters, Setters, Constructeurs
}

