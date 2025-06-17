package com.DeliveryMatch.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePass;
}
