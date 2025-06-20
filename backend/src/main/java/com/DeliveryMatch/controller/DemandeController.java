package com.DeliveryMatch.controller;

import com.DeliveryMatch.model.Demande;
import com.DeliveryMatch.model.Expediteur;
import com.DeliveryMatch.model.Annonce;
import com.DeliveryMatch.repository.DemandeTransportRepository;
import com.DeliveryMatch.repository.ExpediteurRepository;
import com.DeliveryMatch.repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/demandes")
public class DemandeController {

    @Autowired
    private DemandeTransportRepository demandeRepository;

    @Autowired
    private ExpediteurRepository expediteurRepository;

    @Autowired
    private AnnonceRepository annonceRepository;


    public static class DemandeRequest {
        private Integer annonceId;
        private String dimensionsColis;
        private float poids;


        public Integer getAnnonceId() { return annonceId; }
        public void setAnnonceId(Integer annonceId) { this.annonceId = annonceId; }
        public String getDimensionsColis() { return dimensionsColis; }
        public void setDimensionsColis(String dimensionsColis) { this.dimensionsColis = dimensionsColis; }
        public float getPoids() { return poids; }
        public void setPoids(float poids) { this.poids = poids; }
    }


    @PostMapping("/envoyer")
    @PreAuthorize("hasRole('EXPEDITEUR')")
    public ResponseEntity<?> envoyerDemande(@RequestBody DemandeRequest request, Authentication authentication) {
        try {

            String email = authentication.getName();
            Expediteur expediteur = expediteurRepository.findByEmail(email).orElse(null);
            if (expediteur == null) {
                return ResponseEntity.badRequest().body("Expéditeur non trouvé");
            }


            Annonce annonce = annonceRepository.findById(request.getAnnonceId()).orElse(null);
            if (annonce == null) {
                return ResponseEntity.badRequest().body("Annonce non trouvée");
            }


            Demande demande = new Demande();
            demande.setExpediteur(expediteur);
            demande.setAnnonce(annonce);
            demande.setDimensionsColis(request.getDimensionsColis());
            demande.setPoids(request.getPoids());
            demande.setStatus("EN_ATTENTE");
            demande.setDateDemande(new java.util.Date());

            demandeRepository.save(demande);

            return ResponseEntity.ok("Demande envoyée avec succès");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erreur: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/accepter")
    @PreAuthorize("hasAnyRole('CONDUCTEUR', 'ADMINISTRATEUR')")
    public ResponseEntity<?> accepterDemande(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            System.err.println("Erreur: Demande non trouvée ou ID invalide (id=" + id + ")");
            return ResponseEntity.badRequest().body("Erreur: Demande non trouvée ou ID invalide");
        }

        return ResponseEntity.ok("Demande acceptée avec succès");
    }

    @PutMapping("/{id}/refuser")
    @PreAuthorize("hasRole('CONDUCTEUR')")
    public ResponseEntity<?> refuserDemande(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            System.err.println("Erreur: Demande non trouvée ou ID invalide (id=" + id + ")");
            return ResponseEntity.badRequest().body("Erreur: Demande non trouvée ou ID invalide");
        }
        
        return ResponseEntity.ok("Demande refusée");
    }

    @GetMapping("/mes-demandes")
    @PreAuthorize("hasRole('CONDUCTEUR')")
    public ResponseEntity<?> getMesDemandes(Authentication authentication) {
        String email = authentication.getName(); 
        return ResponseEntity.ok("Demandes pour : " + email);
    }

    @GetMapping("/historique")
    @PreAuthorize("hasRole('EXPEDITEUR')")
    public List<Demande> historiqueDemandes(Authentication authentication) {
        String email = authentication.getName();
        Expediteur expediteur = expediteurRepository.findByEmail(email).orElse(null);
        if (expediteur == null) return List.of();
        return demandeRepository.findByExpediteurId(expediteur.getId());
    }
}

