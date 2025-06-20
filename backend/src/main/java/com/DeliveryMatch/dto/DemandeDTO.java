package com.DeliveryMatch.dto;

import java.util.Date;
import lombok.Data;

@Data
public class DemandeDTO {
    private Integer id;
    private Integer expediteurId;
    private Integer annonceId;
    private String status;
    private Date dateDemande;
    private String dimensionsColis;
    private float poids;
}
