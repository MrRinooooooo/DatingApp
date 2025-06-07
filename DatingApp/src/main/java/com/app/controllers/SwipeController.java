package com.app.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.SwipeDTO;
import com.app.dto.UtenteDiscoverDTO;
import com.app.services.SwipeService;

@RestController
@RequestMapping("/api")
public class SwipeController {

    @Autowired
    private SwipeService swipeService;

    // GET /api/utenti/discover → Profili da swipare
    @GetMapping("/utenti/discover")
    public ResponseEntity<List<UtenteDiscoverDTO>> getProfilDaSwipare(
            @AuthenticationPrincipal UserDetails userDetails) {
        
        System.out.println("=== DISCOVER PROFILI DEBUG ===");
        System.out.println("User: " + userDetails.getUsername());
        
        try {
            List<UtenteDiscoverDTO> profili = swipeService.getProfilDaSwipare(userDetails.getUsername());
            return ResponseEntity.ok(profili);
        } catch (Exception e) {
            System.err.println("Errore discover profili: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // POST /api/swipe → Esegui swipe
    @PostMapping("/swipe")
    public ResponseEntity<String> eseguiSwipe(@RequestBody SwipeDTO swipeDTO,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        
        System.out.println("=== SWIPE DEBUG ===");
        System.out.println("User: " + userDetails.getUsername());
        //System.out.println("Id:" + userDetails.getId());
        System.out.println("Target: " + swipeDTO.getUtenteTargetId());
        System.out.println("Tipo: " + swipeDTO.getTipo());
        
        try {
            String risultato = swipeService.eseguiSwipe(swipeDTO, userDetails.getUsername());
            return ResponseEntity.ok(risultato);
        } catch (Exception e) {
            System.err.println("Errore swipe: " + e.getMessage());
            return ResponseEntity.badRequest().body("Errore: " + e.getMessage());
        }
    }

    
}