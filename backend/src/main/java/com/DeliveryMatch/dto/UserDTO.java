package com.DeliveryMatch.dto;

import com.DeliveryMatch.model.UserRole;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Integer id;
    
    @NotNull(message = "Name is required")
    @NotBlank(message = "Name cannot be empty")
    private String nom;
    
    @NotNull(message = "First name is required")
    @NotBlank(message = "First name cannot be empty")
    private String prenom;
    
    @NotNull(message = "Email is required")
    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;
    
    @NotNull(message = "Password is required")
    @NotBlank(message = "Password cannot be empty")
    private String motDePass;

    private Date dateInscription;

    
    @NotNull(message = "Role is required")
    private UserRole role;
}
