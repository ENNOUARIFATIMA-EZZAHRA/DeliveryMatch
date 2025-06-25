package com.DeliveryMatch.controller;

import com.DeliveryMatch.model.Demande;
import com.DeliveryMatch.model.Expediteur;
import com.DeliveryMatch.model.Annonce;
import com.DeliveryMatch.model.DemandeDTO;
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
import java.util.stream.Collectors;

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

//envoyerDemande
    @PostMapping("/envoyer")
    @PreAuthorize("hasRole('EXPEDITEUR')")
    public ResponseEntity<DemandeDTO> envoyerDemande(@RequestBody DemandeDTO demandeDTO, Authentication authentication) {

        Demande demande = new Demande();
        demande.setDimensionsColis(demandeDTO.getDimensionsColis());
        demande.setPoids(demandeDTO.getPoids());
        demande.setStatus("EN_ATTENTE");
        demande.setDateDemande(java.time.LocalDate.parse(demandeDTO.getDateDemande()).atStartOfDay());
        Demande saved = demandeRepository.save(demande);
        return ResponseEntity.ok(toDTO(saved));
    }
//accepterDemande
    @PutMapping("/{id}/accepter")
    @PreAuthorize("hasAnyRole('CONDUCTEUR', 'ADMINISTRATEUR')")
    public ResponseEntity<?> accepterDemande(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            System.err.println("Erreur: Demande non trouvée ou ID invalide (id=" + id + ")");
            return ResponseEntity.badRequest().body("Erreur: Demande non trouvée ou ID invalide");
        }

        return ResponseEntity.ok("Demande acceptée avec succès");
    }
//refuserDemande
    @PutMapping("/{id}/refuser")
    @PreAuthorize("hasRole('CONDUCTEUR')")
    public ResponseEntity<?> refuserDemande(@PathVariable Integer id) {
        if (id == null || id <= 0) {
            System.err.println("Erreur: Demande non trouvée ou ID invalide (id=" + id + ")");
            return ResponseEntity.badRequest().body("Erreur: Demande non trouvée ou ID invalide");
        }
        
        return ResponseEntity.ok("Demande refusée");
    }
//getMesDemandes
    @GetMapping("/mes-demandes")
    @PreAuthorize("hasRole('CONDUCTEUR')")
    public ResponseEntity<?> getMesDemandes(Authentication authentication) {
        String email = authentication.getName(); 
        return ResponseEntity.ok("Demandes pour : " + email);
    }
// historiqueDemandes
    @GetMapping("/historique")
    @PreAuthorize("hasRole('EXPEDITEUR')")
    public List<DemandeDTO> historiqueDemandes(Authentication authentication) {
        String email = authentication.getName();
        Expediteur expediteur = expediteurRepository.findByEmail(email).orElse(null);
        if (expediteur == null) return List.of();
        return demandeRepository.findByExpediteurId(expediteur.getId())
            .stream().map(this::toDTO).collect(Collectors.toList());
    }

    // Méthode utilitaire pour convertir Demande en DemandeDTO
    private DemandeDTO toDTO(Demande demande) {
        DemandeDTO dto = new DemandeDTO();
        dto.setId(demande.getId());
        dto.setDimensionsColis(demande.getDimensionsColis());
        dto.setPoids(demande.getPoids());
        dto.setStatus(demande.getStatus());
        dto.setDateDemande(demande.getDateDemande() != null ? demande.getDateDemande().toLocalDate().toString() : null);
        dto.setExpediteurId(demande.getExpediteur() != null ? demande.getExpediteur().getId() : null);
        dto.setAnnonceId(demande.getAnnonce() != null ? demande.getAnnonce().getId() : null);
        return dto;
    }
}

