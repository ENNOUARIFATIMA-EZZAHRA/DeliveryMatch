package com.DeliveryMatch.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;

@Entity
@Data
public class Demande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String dimensionsColis;
    private float poids;
    private String status;
    private Date dateDemande;

    @ManyToOne
    @JoinColumn(name = "expediteur_id")
    private Expediteur expediteur;

    @ManyToOne
    @JoinColumn(name = "annonce_id")
    private Annonce annonce;

    // Getters, Setters, Constructeurs
}

