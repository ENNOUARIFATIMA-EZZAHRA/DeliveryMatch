package com.DeliveryMatch.model;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Expediteur extends User {
    private String adresse;
    private String telephone;
} 