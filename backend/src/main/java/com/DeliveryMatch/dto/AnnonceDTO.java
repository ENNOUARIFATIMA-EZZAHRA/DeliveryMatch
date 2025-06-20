package com.DeliveryMatch.dto;

import java.sql.Date;
import lombok.Data;

@Data
public class AnnonceDTO {
    private Integer id;
    private String lieuDepart;
    private String destination;
    private String etapes;
    private String dimensionsMax;
    private String typeMarchandise;
    private float capacite;
    private Date dateDepart;
    private String status;
    private Integer conducteurId;
}
