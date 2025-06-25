package com.DeliveryMatch.controller;

import com.DeliveryMatch.dto.LoginRequest;
import com.DeliveryMatch.dto.UserDTO;
import com.DeliveryMatch.model.User;
import com.DeliveryMatch.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    @Autowired
    private AuthService authService;

    // Registration endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        try {
            User created = authService.register(userDTO);
            
            // Create LoginRequest for automatic authentication after registration
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setEmail(created.getEmail());

            loginRequest.setMotDePass(userDTO.getMotDePass());
            
            String token = authService.authenticate(loginRequest);
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            
            // Create user response without sensitive data
            Map<String, Object> userResponse = new HashMap<>();
            userResponse.put("id", created.getId());
            userResponse.put("nom", created.getNom());
            userResponse.put("prenom", created.getPrenom());
            userResponse.put("email", created.getEmail());
            userResponse.put("role", created.getRole());
            userResponse.put("dateInscription", created.getDateInscription());
            
            response.put("user", userResponse);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Login endpoint
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            String token = authService.authenticate(loginRequest);
            User user = authService.getCurrentUser(loginRequest.getEmail());
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            
            // Create user response without sensitive data
            Map<String, Object> userResponse = new HashMap<>();
            userResponse.put("id", user.getId());
            userResponse.put("nom", user.getNom());
            userResponse.put("prenom", user.getPrenom());
            userResponse.put("email", user.getEmail());
            userResponse.put("role", user.getRole());
            userResponse.put("dateInscription", user.getDateInscription());
            
            response.put("user", userResponse);
            
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
