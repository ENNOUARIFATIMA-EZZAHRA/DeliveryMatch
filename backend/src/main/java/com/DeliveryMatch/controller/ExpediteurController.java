package com.DeliveryMatch.controller;

import com.DeliveryMatch.model.Annonce;
import com.DeliveryMatch.dto.AnnonceDTO;
import com.DeliveryMatch.model.Demande;
import com.DeliveryMatch.model.DemandeDTO;
import com.DeliveryMatch.service.AnnonceService;
import com.DeliveryMatch.service.DemandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/expediteur")
public class ExpediteurController {
    @Autowired
    private AnnonceService annonceService;
    @Autowired
    private DemandeService demandeService;

    // Voir les annonces filtrées
    @GetMapping("/annonces")
    public ResponseEntity<List<AnnonceDTO>> annoncesFiltrees(
            @RequestParam String destination,
            @RequestParam Date dateDepart,
            @RequestParam String typeMarchandise) {
        List<AnnonceDTO> annonces = annonceService.listerAnnonces().stream()
            .filter(a -> a.getDestination().equalsIgnoreCase(destination))
            .filter(a -> a.getDateDepart() != null && a.getDateDepart().toLocalDate().equals(dateDepart.toLocalDate()))
            .filter(a -> a.getTypeMarchandise().equalsIgnoreCase(typeMarchandise))
            .map(this::toAnnonceDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(annonces);
    }

    // Envoyer une demande de transport
    @PostMapping("/demandes")
    public ResponseEntity<DemandeDTO> envoyerDemande(@RequestBody DemandeDTO demandeDTO) {
        Demande demande = demandeService.createDemande(demandeDTO);
        return ResponseEntity.ok(toDemandeDTO(demande));
    }

    // Consulter l'historique des demandes de l'expéditeur
    @GetMapping("/historique/{expediteurId}")
    public ResponseEntity<List<DemandeDTO>> historiqueDemandes(@PathVariable Integer expediteurId) {
        List<DemandeDTO> demandes = demandeService.listerDemandesParExpediteur(expediteurId).stream()
            .map(this::toDemandeDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(demandes);
    }

    // Méthodes utilitaires pour convertir Annonce/Demande en DTO
    private AnnonceDTO toAnnonceDTO(Annonce annonce) {
        AnnonceDTO dto = new AnnonceDTO();
        dto.setId(annonce.getId());
        dto.setLieuDepart(annonce.getLieuDepart());
        dto.setDestination(annonce.getDestination());
        dto.setEtapes(annonce.getEtapes());
        dto.setDimensionsMax(annonce.getDimensionsMax());
        dto.setTypeMarchandise(annonce.getTypeMarchandise());
        dto.setCapacite(annonce.getCapacite());
        dto.setStatus(annonce.getStatus());
        dto.setDateDepart(annonce.getDateDepart() != null ? annonce.getDateDepart().toLocalDate().toString() : null);
        dto.setConducteurId(annonce.getConducteur() != null ? annonce.getConducteur().getId() : null);
        return dto;
    }
    private DemandeDTO toDemandeDTO(Demande demande) {
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
