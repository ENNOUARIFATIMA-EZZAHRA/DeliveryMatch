package com.DeliveryMatch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;
import jakarta.persistence.DiscriminatorValue;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("CONDUCTEUR")
@Data
@EqualsAndHashCode(callSuper = true)
public class Conducteur extends User {

    private float noteMoyenne;

    @OneToMany(mappedBy = "conducteur")
    @JsonIgnore
    private List<Annonce> annonces;

    // Constructeur par défaut requis par JPA
    public Conducteur() {
        super();
    }


    public float getNoteMoyenne() { return noteMoyenne; }
    public void setNoteMoyenne(float noteMoyenne) { this.noteMoyenne = noteMoyenne; }
    public List<Annonce> getAnnonces() { return annonces; }
    public void setAnnonces(List<Annonce> annonces) { this.annonces = annonces; }
}

