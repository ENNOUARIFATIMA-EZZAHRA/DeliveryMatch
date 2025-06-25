package com.DeliveryMatch.controller;

import com.DeliveryMatch.model.Annonce;
<<<<<<< HEAD
import com.DeliveryMatch.dto.AnnonceDTO;
=======
import com.DeliveryMatch.model.Conducteur;
>>>>>>> 96f55b51b676be3fe770b04e465878f6136a671c
import com.DeliveryMatch.model.User;
import com.DeliveryMatch.repository.AnnonceRepository;
import com.DeliveryMatch.repository.UserRepository;
import com.DeliveryMatch.security.JwtTokenProvider;
<<<<<<< HEAD
import com.DeliveryMatch.service.AnnonceService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

=======
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
>>>>>>> 96f55b51b676be3fe770b04e465878f6136a671c
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/annonces")
@CrossOrigin(origins = "*")
public class AnnonceController {

    @Autowired
    private AnnonceService annonceService;

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
<<<<<<< HEAD
=======

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }
>>>>>>> 96f55b51b676be3fe770b04e465878f6136a671c

    @Autowired
    private UserRepository userRepository;
//getAllAnnonces
    @GetMapping
    public List<AnnonceDTO> getAllAnnonces() {
        return annonceService.getAllAnnonces().stream()
            .map(this::toDTO)
            .collect(Collectors.toList());
    }
    //searchAnnonces
    @GetMapping("/search")
    public List<AnnonceDTO> searchAnnonces(@RequestParam(required = false) String destination) {
        List<Annonce> annonces;
        if (destination == null || destination.isEmpty()) {
            annonces = annonceRepository.findAll();
        } else {
            annonces = annonceRepository.findAll().stream()
                .filter(a -> a.getDestination() != null && a.getDestination().toLowerCase().contains(destination.toLowerCase()))
                .collect(Collectors.toList());
        }
        return annonces.stream().map(this::toDTO).collect(Collectors.toList());
    }
//createAnnonce
    @PostMapping
    public ResponseEntity<AnnonceDTO> createAnnonce(@RequestBody AnnonceDTO annonceDTO) {
        Annonce createdAnnonce = annonceService.createAnnonce(annonceDTO);
        return ResponseEntity.ok(toDTO(createdAnnonce));
    }
//getAnnonceById
    @GetMapping("/{id}")
    public ResponseEntity<AnnonceDTO> getAnnonceById(@PathVariable Integer id) {
        return annonceService.getAnnonceById(id)
                .map(annonce -> ResponseEntity.ok(toDTO(annonce)))
                .orElse(ResponseEntity.notFound().build());
    }
//getAnnoncesByConducteur
    @GetMapping("/conducteur/{conducteurId}")
    public ResponseEntity<List<AnnonceDTO>> getAnnoncesByConducteur(@PathVariable Integer conducteurId) {
        List<AnnonceDTO> annonces = annonceService.getAllAnnonces().stream()
            .filter(annonce -> annonce.getConducteur() != null && annonce.getConducteur().getId().equals(conducteurId))
            .map(this::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(annonces);
    }
//getAnnoncesForExpediteur
    @GetMapping("/expediteur")
    public ResponseEntity<List<AnnonceDTO>> getAnnoncesForExpediteur() {
        List<AnnonceDTO> annonces = annonceService.getAllAnnonces().stream()
            .filter(annonce -> "EN_ATTENTE".equals(annonce.getStatus()) || "ACTIVE".equals(annonce.getStatus()))
            .map(this::toDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(annonces);
    }
//updateAnnonce
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAnnonce(@PathVariable Integer id, @RequestBody AnnonceDTO annonceDTO, HttpServletRequest request) {
        try {
            // Get current user from JWT token
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            
            String email = jwtTokenProvider.getEmailFromToken(token);
            User currentUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Check if announcement exists
            Annonce existingAnnonce = annonceRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Annonce not found"));
            
            // Check if user is the owner of the announcement
            if (existingAnnonce.getConducteur() == null || 
                !existingAnnonce.getConducteur().getId().equals(currentUser.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Collections.singletonMap("error", "Vous n'êtes pas autorisé à modifier cette annonce"));
            }
            
            // Check if announcement can be modified
            if (!"EN_ATTENTE".equals(existingAnnonce.getStatus()) && 
                !"ACTIVE".equals(existingAnnonce.getStatus())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap("error", "Cette annonce ne peut plus être modifiée"));
            }
            
            // Update announcement fields - handle both field name variations
            if (annonceDTO.getLieuDepart() != null) {
                existingAnnonce.setLieuDepart(annonceDTO.getLieuDepart());
            }
            if (annonceDTO.getDestination() != null) {
                existingAnnonce.setDestination(annonceDTO.getDestination());
            }
            if (annonceDTO.getEtapes() != null) {
                existingAnnonce.setEtapes(annonceDTO.getEtapes());
            }
            if (annonceDTO.getDimensionsMax() != null) {
                existingAnnonce.setDimensionsMax(annonceDTO.getDimensionsMax());
            }
            if (annonceDTO.getTypeMarchandise() != null) {
                existingAnnonce.setTypeMarchandise(annonceDTO.getTypeMarchandise());
            }
            if (annonceDTO.getCapacite() > 0) {
                existingAnnonce.setCapacite(annonceDTO.getCapacite());
            }
            if (annonceDTO.getStatus() != null) {
                existingAnnonce.setStatus(annonceDTO.getStatus());
            }
            if (annonceDTO.getDateDepart() != null) {
                existingAnnonce.setDateDepart(java.time.LocalDate.parse(annonceDTO.getDateDepart()).atStartOfDay());
            }
            
            Annonce updatedAnnonce = annonceRepository.save(existingAnnonce);
            return ResponseEntity.ok(toDTO(updatedAnnonce));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Erreur lors de la modification de l'annonce: " + e.getMessage()));
        }
    }
//deleteAnnonce
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAnnonce(@PathVariable Integer id, HttpServletRequest request) {
        try {
            // Get current user from JWT token
            String token = request.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
            }
            //check  user actual
            String email = jwtTokenProvider.getEmailFromToken(token);
            User currentUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));
            
            // Check if announcement exists
            Annonce existingAnnonce = annonceRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Annonce not found"));
            
            // Check if user is the owner of the announcement
            if (existingAnnonce.getConducteur() == null || 
                !existingAnnonce.getConducteur().getId().equals(currentUser.getId())) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body(Collections.singletonMap("error", "Vous n'êtes pas autorisé à supprimer cette annonce"));
            }
            
            annonceRepository.deleteById(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Annonce supprimée avec succès"));
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Erreur lors de la suppression de l'annonce: " + e.getMessage()));
        }
    }

<<<<<<< HEAD
    // Méthode utilitaire pour convertir Annonce en AnnonceDTO
    private AnnonceDTO toDTO(Annonce annonce) {
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
        }
        return dto;
=======
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
>>>>>>> 96f55b51b676be3fe770b04e465878f6136a671c
    }
}
