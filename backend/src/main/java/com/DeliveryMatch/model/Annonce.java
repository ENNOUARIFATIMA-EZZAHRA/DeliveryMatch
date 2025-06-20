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

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getLieuDepart() { return lieuDepart; }
    public void setLieuDepart(String lieuDepart) { this.lieuDepart = lieuDepart; }
    public String getDestination() { return destination; }
    public void setDestination(String destination) { this.destination = destination; }
    public String getEtapes() { return etapes; }
    public void setEtapes(String etapes) { this.etapes = etapes; }
    public String getDimensionsMax() { return dimensionsMax; }
    public void setDimensionsMax(String dimensionsMax) { this.dimensionsMax = dimensionsMax; }
    public String getTypeMarchandise() { return typeMarchandise; }
    public void setTypeMarchandise(String typeMarchandise) { this.typeMarchandise = typeMarchandise; }
    public float getCapacite() { return capacite; }
    public void setCapacite(float capacite) { this.capacite = capacite; }
    public Date getDateDepart() { return dateDepart; }
    public void setDateDepart(Date dateDepart) { this.dateDepart = dateDepart; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Conducteur getConducteur() { return conducteur; }
    public void setConducteur(Conducteur conducteur) { this.conducteur = conducteur; }
    public List<Demande> getDemandes() { return demandes; }
    public void setDemandes(List<Demande> demandes) { this.demandes = demandes; }

    // Constructeurs
}

