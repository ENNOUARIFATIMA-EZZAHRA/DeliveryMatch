package com.DeliveryMatch.controller;

import com.DeliveryMatch.model.Annonce;
import com.DeliveryMatch.model.Demande;
import com.DeliveryMatch.service.AnnonceService;
import com.DeliveryMatch.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/expediteur")
public class ExpediteurController {
    @Autowired
    private AnnonceService annonceService;
    @Autowired
    private DemandeService demandeService;

    // Voir les annonces filtrées
    @GetMapping("/annonces")
    public ResponseEntity<List<Annonce>> annoncesFiltrees(
            @RequestParam String destination,
            @RequestParam Date dateDepart,
            @RequestParam String typeMarchandise) {
        return ResponseEntity.ok(
            annonceService.listerAnnonces().stream()
                .filter(a -> a.getDestination().equalsIgnoreCase(destination))
                .filter(a -> a.getDateDepart().equals(dateDepart))
                .filter(a -> a.getTypeMarchandise().equalsIgnoreCase(typeMarchandise))
                .toList()
        );
    }

    // Envoyer une demande de transport
    @PostMapping("/demandes")
    public ResponseEntity<Demande> envoyerDemande(@RequestBody Demande demande) {
        return ResponseEntity.ok(demandeService.envoyerDemande(demande));
    }

    // Consulter l'historique des demandes de l'expéditeur
    @GetMapping("/historique/{expediteurId}")
    public ResponseEntity<List<Demande>> historiqueDemandes(@PathVariable Integer expediteurId) {
        return ResponseEntity.ok(demandeService.listerDemandesParExpediteur(expediteurId));
    }
}
