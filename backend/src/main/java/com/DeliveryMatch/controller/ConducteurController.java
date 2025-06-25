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

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/conducteur")
public class ConducteurController {
    @Autowired
    private AnnonceService annonceService;
    @Autowired
    private DemandeService demandeService;

    // Publier une nouvelle annonce
    @PostMapping("/annonces")
    public ResponseEntity<AnnonceDTO> ajouterAnnonce(@RequestBody AnnonceDTO annonceDTO) {
        Annonce annonce = annonceService.createAnnonce(annonceDTO);
        return ResponseEntity.ok(toAnnonceDTO(annonce));
    }

    // Voir toutes mes annonces (par conducteurId)
    @GetMapping("/annonces/{conducteurId}")
    public ResponseEntity<List<AnnonceDTO>> mesAnnonces(@PathVariable Integer conducteurId) {
        List<AnnonceDTO> annonces = annonceService.listerAnnonces().stream()
            .filter(a -> a.getConducteur() != null && a.getConducteur().getId().equals(conducteurId))
            .map(this::toAnnonceDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(annonces);
    }

    // Voir les demandes reçues pour une annonce
    @GetMapping("/annonce/{annonceId}/demandes")
    public ResponseEntity<List<DemandeDTO>> demandesParAnnonce(@PathVariable Integer annonceId) {
        return annonceService.trouverParId(annonceId)
            .map(annonce -> annonce.getDemandes().stream().map(this::toDemandeDTO).collect(Collectors.toList()))
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.ok(List.of()));
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
        if (annonce.getConducteur() != null) {
            dto.setConducteurId(annonce.getConducteur().getId());
            dto.setNoteMoyenne(annonce.getConducteur().getNoteMoyenne());
        } else {
            dto.setNoteMoyenne(null);
        }
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
