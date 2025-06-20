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


    @GetMapping("/search")
    public List<Annonce> searchAnnonces(
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateDepart
    ) {

        if ((destination == null || destination.isEmpty()) && dateDepart == null) {
            return annonceRepository.findAll();
        }

        return annonceRepository.findByDestinationContainingIgnoreCaseAndDateDepart(
                destination == null ? "" : destination,
                dateDepart
        );
    }


    @PostMapping
    public Annonce createAnnonce(@RequestBody Annonce annonce) {
        return annonceRepository.save(annonce);
    }
}
