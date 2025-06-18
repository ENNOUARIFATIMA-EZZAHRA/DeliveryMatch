package com.DeliveryMatch.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Conducteur extends User {

    private float noteMoyenne;
    private String adresse;
    private String telephone;

    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL)
    private List<Annonce> annonces;

    // Constructeur par défaut requis par JPA
    public Conducteur() {
        super();
    }
}

