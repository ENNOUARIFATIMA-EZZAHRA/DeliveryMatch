package com.DeliveryMatch.dto;

public class ConducteurDTO {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private float noteMoyenne;

    // Getters & setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getPrenom() { return prenom; }
    public void setPrenom(String prenom) { this.prenom = prenom; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public float getNoteMoyenne() { return noteMoyenne; }
    public void setNoteMoyenne(float noteMoyenne) { this.noteMoyenne = noteMoyenne; }
} 