package com.DeliveryMatch.controller;

import com.DeliveryMatch.model.Colis;
import com.DeliveryMatch.model.User;
import com.DeliveryMatch.repository.ColisRepository;
import com.DeliveryMatch.repository.UserRepository;
import com.DeliveryMatch.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/colis")
public class ColisController {

    @Autowired
    private ColisRepository colisRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    // إضافة طرد جديد - متاح للمرسل فقط
    @PostMapping
    @PreAuthorize("hasRole('EXPEDITEUR')")
    public ResponseEntity<?> createColis(@RequestBody Colis colis, @RequestHeader("Authorization") String token) {
        String email = jwtTokenProvider.getEmailFromToken(token.substring(7));
        User expediteur = userRepository.findByEmail(email).orElse(null);
        if (expediteur == null) {
            return ResponseEntity.badRequest().body("Utilisateur non trouvé");
        }
        colis.setExpediteur(expediteur);
        return ResponseEntity.ok(colisRepository.save(colis));
    }

    // عرض جميع الطرود - متاح للمدير فقط
    @GetMapping
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public List<Colis> getAllColis() {
        return colisRepository.findAll();
    }

    // عرض طرد محدد - متاح للمدير والمرسل صاحب الطرد
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR') or @colisService.isExpediteur(#id, authentication)")
    public ResponseEntity<?> getColis(@PathVariable Integer id) {
        return colisRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // تحديث حالة الطرد - متاح للمدير فقط
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<?> updateColisStatus(@PathVariable Integer id, @RequestBody String status) {
        return colisRepository.findById(id)
                .map(colis -> {
                    colis.setStatut(status);
                    return ResponseEntity.ok(colisRepository.save(colis));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // حذف طرد - متاح للمدير فقط
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<?> deleteColis(@PathVariable Integer id) {
        if (!colisRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        colisRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
} 