package com.DeliveryMatch.model;

public class DemandeDTO {
    private Integer id;
    private String dimensionsColis;
    private float poids;
    private String status;
    private String dateDemande;
    private Integer expediteurId;
    private Integer annonceId;


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getDimensionsColis() { return dimensionsColis; }
    public void setDimensionsColis(String dimensionsColis) { this.dimensionsColis = dimensionsColis; }
    public float getPoids() { return poids; }
    public void setPoids(float poids) { this.poids = poids; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDateDemande() { return dateDemande; }
    public void setDateDemande(String dateDemande) { this.dateDemande = dateDemande; }
    public Integer getExpediteurId() { return expediteurId; }
    public void setExpediteurId(Integer expediteurId) { this.expediteurId = expediteurId; }
    public Integer getAnnonceId() { return annonceId; }
    public void setAnnonceId(Integer annonceId) { this.annonceId = annonceId; }
} 