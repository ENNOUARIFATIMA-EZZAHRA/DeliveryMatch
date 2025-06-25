package com.DeliveryMatch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.List;
import jakarta.persistence.OneToMany;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@DiscriminatorValue("EXPEDITEUR")
@Data
@EqualsAndHashCode(callSuper = true)
public class Expediteur extends User {
    @OneToMany(mappedBy = "expediteur")
    @JsonIgnore
    private List<Demande> demandes;

    public List<Demande> getDemandes() { return demandes; }
    public void setDemandes(List<Demande> demandes) { this.demandes = demandes; }
} 