package com.DeliveryMatch.dto;

import com.DeliveryMatch.model.UserRole;
import lombok.Data;
import java.util.Date;

@Data
public class UserDTO {
    private Integer id;
    private String nom;
    private String prenom;
    private String email;
    private String motDePass;
    private Date dateInscription;
    private UserRole role;
}
