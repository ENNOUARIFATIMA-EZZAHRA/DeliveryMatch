package com.DeliveryMatch.dto;

import lombok.Data;
import java.util.List;

@Data
public class TrajetDTO {
    private String lieuDepart;
    private List<String> etapesIntermediaires;
    private String destinationFinale;
    private Double dimensionsMaximales;
    private String typeMarchandise;
    private Double capaciteDisponible;
} 