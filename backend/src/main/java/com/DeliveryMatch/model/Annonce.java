package com.DeliveryMatch.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "conducteur_id")
    private Conducteur conducteur;

    @Column(name = "lieu_depart")
    private String lieuDepart;
    
    private String destination;
    private String etapes;

    @Column(name = "dimensions_max")
    private String dimensionsMax;

    @Column(name = "type_marchandise")
    private String typeMarchandise;
    
    private float capacite;
    
    @Column(name = "type_vehicule")
    private String typeVehicule;
    
    @Column(name = "description")
    private String description;

    @Column(name = "date_depart")
    private LocalDateTime dateDepart;
    
    private String status;

    @OneToMany(mappedBy = "annonce")
    @JsonIgnore
    private List<Demande> demandes;

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public Conducteur getConducteur() { return conducteur; }
    public void setConducteur(Conducteur conducteur) { this.conducteur = conducteur; }
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
    public String getTypeVehicule() { return typeVehicule; }
    public void setTypeVehicule(String typeVehicule) { this.typeVehicule = typeVehicule; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getDateDepart() { return dateDepart; }
    public void setDateDepart(LocalDateTime dateDepart) { this.dateDepart = dateDepart; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<Demande> getDemandes() { return demandes; }
    public void setDemandes(List<Demande> demandes) { this.demandes = demandes; }


}

