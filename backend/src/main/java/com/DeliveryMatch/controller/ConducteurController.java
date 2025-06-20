package com.DeliveryMatch.controller;

import com.DeliveryMatch.model.Annonce;
import com.DeliveryMatch.model.Demande;
import com.DeliveryMatch.service.AnnonceService;
import com.DeliveryMatch.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/conducteur")
public class ConducteurController {
    @Autowired
    private AnnonceService annonceService;
    @Autowired
    private DemandeService demandeService;

    // Publier une nouvelle annonce
    @PostMapping("/annonces")
    public ResponseEntity<Annonce> ajouterAnnonce(@RequestBody Annonce annonce) {
        return ResponseEntity.ok(annonceService.ajouterAnnonce(annonce));
    }

    // Voir toutes mes annonces (par conducteurId)
    @GetMapping("/annonces/{conducteurId}")
    public ResponseEntity<List<Annonce>> mesAnnonces(@PathVariable Integer conducteurId) {
        return ResponseEntity.ok(annonceService.listerAnnonces().stream()
                .filter(a -> a.getConducteur() != null && a.getConducteur().getId().equals(conducteurId))
                .toList());
    }

    // Voir les demandes reçues pour une annonce
    @GetMapping("/annonce/{annonceId}/demandes")
    public ResponseEntity<List<Demande>> demandesParAnnonce(@PathVariable Integer annonceId) {
        return ResponseEntity.ok(
            annonceService.trouverParId(annonceId)
                .map(Annonce::getDemandes)
                .orElse(List.of())
        );
    }

    // Accepter une demande
    @PostMapping("/demande/{demandeId}/accepter")
    public ResponseEntity<Demande> accepterDemande(@PathVariable Integer demandeId) {
        return ResponseEntity.ok(demandeService.accepterDemande(demandeId));
    }

    // Refuser une demande
    @PostMapping("/demande/{demandeId}/refuser")
    public ResponseEntity<Demande> refuserDemande(@PathVariable Integer demandeId) {
        return ResponseEntity.ok(demandeService.refuserDemande(demandeId));
    }

    // Consulter l'historique des trajets (toutes les annonces du conducteur)
    @GetMapping("/historique/{conducteurId}")
    public ResponseEntity<List<Annonce>> historiqueAnnonces(@PathVariable Integer conducteurId) {
        return ResponseEntity.ok(annonceService.listerAnnonces().stream()
                .filter(a -> a.getConducteur() != null && a.getConducteur().getId().equals(conducteurId))
                .toList());
    }
}
