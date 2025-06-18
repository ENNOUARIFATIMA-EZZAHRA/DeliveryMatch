package com.DeliveryMatch.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/demandes")
public class DemandeController {

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
        String email = authentication.getName(); // جاي من JWT
        return ResponseEntity.ok("Demandes pour : " + email);
    }

}

