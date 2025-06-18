package com.DeliveryMatch.controller;

import com.DeliveryMatch.model.Annonce;
import com.DeliveryMatch.repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.List;

@RestController
@RequestMapping("/api/annonces")
public class AnnonceController {

    @Autowired
    private AnnonceRepository annonceRepository;

    // Endpoint pour rechercher les annonces selon les critères
    @GetMapping("/search")
    public List<Annonce> searchAnnonces(
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateDepart,
            @RequestParam(required = false) String typeColis
    ) {
        // Si tous les critères sont vides, retourner tout
        if ((destination == null || destination.isEmpty()) && dateDepart == null && (typeColis == null || typeColis.isEmpty())) {
            return annonceRepository.findAll();
        }
        // Utiliser la méthode de recherche avec valeurs par défaut si null
        return annonceRepository.findByDestinationContainingIgnoreCaseAndDateDepartAndTypeMarchandiseContainingIgnoreCase(
                destination == null ? "" : destination,
                dateDepart,
                typeColis == null ? "" : typeColis
        );
    }
}
