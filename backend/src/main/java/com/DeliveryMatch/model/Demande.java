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
    private String status;
    private LocalDateTime dateDemande;

    @ManyToOne
    @JoinColumn(name = "expediteur_id")
    private Expediteur expediteur;

    @ManyToOne
    @JoinColumn(name = "annonce_id")
    private Annonce annonce;

    // Getters and setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getDimensionsColis() { return dimensionsColis; }
    public void setDimensionsColis(String dimensionsColis) { this.dimensionsColis = dimensionsColis; }
    public float getPoids() { return poids; }
    public void setPoids(float poids) { this.poids = poids; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public LocalDateTime getDateDemande() { return dateDemande; }
    public void setDateDemande(LocalDateTime dateDemande) { this.dateDemande = dateDemande; }
    public Expediteur getExpediteur() { return expediteur; }
    public void setExpediteur(Expediteur expediteur) { this.expediteur = expediteur; }
    public Annonce getAnnonce() { return annonce; }
    public void setAnnonce(Annonce annonce) { this.annonce = annonce; }
}

