package com.DeliveryMatch.controller;

import com.DeliveryMatch.dto.LoginRequest;
import com.DeliveryMatch.model.User;
import com.DeliveryMatch.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    // Endpoint pour l'inscription (register)
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        // TODO: Ajouter la logique d'inscription
        return ResponseEntity.ok().build();
    }

    // Endpoint pour la connexion (login)
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // TODO: Ajouter la logique de connexion
        return ResponseEntity.ok().build();
    }
}
