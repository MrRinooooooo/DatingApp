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

import com.app.dto.CustomUserDto;
import com.app.dto.SwipeDTO;
import com.app.dto.UtenteDiscoverDTO;
import com.app.services.PreferenzeService;
import com.app.services.SwipeService;
import com.app.utils.SecurityUtils;

@RestController
@RequestMapping("/api")
public class SwipeController {

    @Autowired
    private SwipeService swipeService;
    
    @Autowired
    private PreferenzeService preferenzeService;

    // GET /api/utenti/discover → Profili da swipare
    @GetMapping("/utenti/discover")    
	public ResponseEntity<?> listPreferenze() {		
		String currentUserEmail = SecurityUtils.getCurrentUserEmail();			
		return preferenzeService.getUtentiByPreferenze(currentUserEmail, 0, 20);		
	}

    // POST /api/swipe → Esegui swipe    
    @PostMapping("/swipe")
    public ResponseEntity<String> eseguiSwipe(@RequestBody SwipeDTO swipeDTO,
                                            @AuthenticationPrincipal CustomUserDto userPrincipal) {
        
        System.out.println("=== SWIPE DEBUG ===");        
        System.out.println("UserId: " + userPrincipal.getUserId());
        System.out.println("User: " + userPrincipal.getUsername());
        System.out.println("Target: " + swipeDTO.getUtenteTargetId());
        System.out.println("Tipo: " + swipeDTO.getTipo());
        
      //  try {         LE ECCEZIONI VENGONO GESTITE SINGOLARMENTE NEL SERVICE
            String risultato = swipeService.eseguiSwipe(swipeDTO, userPrincipal.getUserId());
            return ResponseEntity.ok(risultato);
      /*  } catch (Exception e) {
            System.err.println("Errore swipe: " + e.getMessage());
            return ResponseEntity.badRequest().body("Errore: " + e.getMessage());
        }*/
    }
    
}