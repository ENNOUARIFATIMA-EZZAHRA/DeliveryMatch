package com.DeliveryMatch.model;

import com.sun.security.auth.UnixNumericUserPrincipal;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Conducteur extends UnixNumericUserPrincipal {

    private float noteMoyenne;

    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL)
    private List<Annonce> annonces;

    public Conducteur(String name) {
        super(name);
    }

    public Conducteur(long name) {
        super(name);
    }

    // Getters, Setters, Constructeurs
}

