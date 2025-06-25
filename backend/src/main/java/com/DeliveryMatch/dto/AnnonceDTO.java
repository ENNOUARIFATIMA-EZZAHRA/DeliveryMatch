package com.DeliveryMatch.dto;

import lombok.Getter;

public class AnnonceDTO {
    private Integer id;
    private String lieuDepart;
    private String destination;
    private String etapes;
    private String dimensionsMax;
    private String typeMarchandise;
    @Getter
    private float capacite;
    private String status;
    private String dateDepart; // format yyyy-MM-dd
    @Getter
    private Integer conducteurId;

    // Frontend field names for compatibility
    private float capaciteDisponible;
    private String description;
    private String typeVehicule;

    private Float noteMoyenne;

    // Getters & setters
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

    public void setCapacite(float capacite) { this.capacite = capacite; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDateDepart() { return dateDepart; }
    public void setDateDepart(String dateDepart) { this.dateDepart = dateDepart; }

    public void setConducteurId(Integer conducteurId) { this.conducteurId = conducteurId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getTypeVehicule() { return typeVehicule; }
    public void setTypeVehicule(String typeVehicule) { this.typeVehicule = typeVehicule; }

    public Float getNoteMoyenne() { return noteMoyenne; }
    public void setNoteMoyenne(Float noteMoyenne) { this.noteMoyenne = noteMoyenne; }
} 