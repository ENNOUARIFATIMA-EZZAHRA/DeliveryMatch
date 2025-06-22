package com.DeliveryMatch.controller;

import com.DeliveryMatch.model.Annonce;
import com.DeliveryMatch.model.Conducteur;
import com.DeliveryMatch.model.User;
import com.DeliveryMatch.repository.AnnonceRepository;
import com.DeliveryMatch.repository.UserRepository;
import com.DeliveryMatch.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/annonces")
@CrossOrigin(origins = "*")
public class AnnonceController {

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

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
    @PreAuthorize("hasRole('CONDUCTEUR')")
    public ResponseEntity<?> createAnnonce(@RequestBody Annonce annonce, HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token not provided");
        }
        String email = jwtTokenProvider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null || !(user instanceof Conducteur)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("User is not a driver or not found");
        }
        
        annonce.setConducteur((Conducteur) user);
        
        Annonce savedAnnonce = annonceRepository.save(annonce);
        return ResponseEntity.ok(savedAnnonce);
    }

    @GetMapping("/conducteur")
    public List<Annonce> getAnnoncesByConducteur(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token == null) {
            return Collections.emptyList();
        }
        String email = jwtTokenProvider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email).orElse(null);
        if (user != null) {
            return annonceRepository.findByConducteurId(user.getId());
        }
        return Collections.emptyList();
    }
}
