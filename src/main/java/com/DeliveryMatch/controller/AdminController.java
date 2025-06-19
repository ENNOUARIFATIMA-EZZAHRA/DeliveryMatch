package com.DeliveryMatch.controller;

import com.DeliveryMatch.model.User;
import com.DeliveryMatch.model.Annonce;
import com.DeliveryMatch.model.Demande;
import com.DeliveryMatch.repository.UserRepository;
import com.DeliveryMatch.repository.AnnonceRepository;
import com.DeliveryMatch.repository.DemandeTransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnnonceRepository annonceRepository;
    @Autowired
    private DemandeTransportRepository demandeRepository;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public Map<String, Object> dashboard() {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("utilisateurs", userRepository.findAll());
        dashboard.put("annonces", annonceRepository.findAll());
        dashboard.put("demandes", demandeRepository.findAll());
        return dashboard;
    }
}
