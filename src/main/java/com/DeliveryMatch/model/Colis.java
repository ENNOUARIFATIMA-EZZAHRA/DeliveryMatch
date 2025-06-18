package com.DeliveryMatch.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "colis")
public class Colis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;
    private Double poids;
    private String dimensions;
    private String adresseDepart;
    private String adresseArrivee;
    private String statut;

    @ManyToOne
    @JoinColumn(name = "expediteur_id")
    private User expediteur;
} 