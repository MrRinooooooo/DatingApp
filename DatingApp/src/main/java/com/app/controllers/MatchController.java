package com.app.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.MatchDTO;
import com.app.dto.MessaggioDTO;
import com.app.entities.Match;
import com.app.services.MessaggioService;
import com.app.services.MatchService;

@RestController
@RequestMapping("/api")
public class MatchController {

    @Autowired
    private MessaggioService messaggioService;
    
    @Autowired
    private MatchService matchService;

    // ========== ENDPOINT MATCH ==========
    
    // GET /api/match → ottieni tutti i match dell'utente
    @GetMapping("/match")
    public ResponseEntity<?> getMatches(@AuthenticationPrincipal UserDetails userDetails) {
        
        System.out.println("=== GET MATCHES DEBUG ===");
        System.out.println("User: " + userDetails.getUsername());
        
        try {
            List<MatchDTO> matches = matchService.getMatchByUtente(userDetails.getUsername());
            return ResponseEntity.ok(matches);
        } catch (Exception e) {
            System.err.println("Errore recupero matches: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Errore nel recupero dei match: " + e.getMessage());
        }
    }
    
    // GET /api/match/{id} → ottieni dettagli di un match specifico
    @GetMapping("/match/{matchId}")
    public ResponseEntity<?> getMatchDetails(@PathVariable Long matchId,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        
        System.out.println("=== GET MATCH DETAILS DEBUG ===");
        System.out.println("Match ID: " + matchId);
        System.out.println("User: " + userDetails.getUsername());
        
        try {
            MatchDTO match = matchService.getMatchDetails(matchId, userDetails.getUsername());
            if (match != null) {
                return ResponseEntity.ok(match);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Errore recupero match details: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Errore: " + e.getMessage());
        }
    }
    
    // DELETE /api/match/{id} → elimina un match (unmatch)
    @DeleteMapping("/match/{matchId}")
    public ResponseEntity<?> deleteMatch(@PathVariable Long matchId,
                                        @AuthenticationPrincipal UserDetails userDetails) {
        
        System.out.println("=== DELETE MATCH DEBUG ===");
        System.out.println("Match ID: " + matchId);
        System.out.println("User: " + userDetails.getUsername());
        
        try {
            boolean deleted = matchService.deleteMatch(matchId, userDetails.getUsername());
            if (deleted) {
                return ResponseEntity.ok("Match eliminato con successo");
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.err.println("Errore eliminazione match: " + e.getMessage());
            return ResponseEntity.internalServerError().body("Errore: " + e.getMessage());
        }
    }
    
    // ========== ENDPOINT MESSAGGI ==========
    
    // GET /api/match/{id}/messaggi → legge chat con un match
    @GetMapping("/match/{matchId}/messaggi")
    public ResponseEntity<List<MessaggioDTO>> getMessaggi(@PathVariable Long matchId,
                                                          @AuthenticationPrincipal UserDetails userDetails) {

        System.out.println("=== GET MESSAGGI DEBUG ===");
        System.out.println("Match ID: " + matchId);
        System.out.println("User: " + userDetails.getUsername());

        try {
            List<MessaggioDTO> messaggi = messaggioService.getMessaggiByMatch(matchId, userDetails.getUsername());
            return ResponseEntity.ok(messaggi);
        } catch (Exception e) {
            System.err.println("Errore recupero messaggi: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // POST /api/match/{id}/messaggi → invia messaggio
    @PostMapping("/match/{matchId}/messaggi")
    public ResponseEntity<String> inviaMessaggio(@PathVariable Long matchId,
                                                @RequestBody MessaggioDTO messaggioDTO,
                                                @AuthenticationPrincipal UserDetails userDetails) {

        System.out.println("=== INVIA MESSAGGIO DEBUG ===");
        System.out.println("Match ID: " + matchId);
        System.out.println("User: " + userDetails.getUsername());
        System.out.println("Messaggio: " + messaggioDTO.getContenuto());

        try {
            messaggioService.inviaMessaggio(matchId, messaggioDTO, userDetails.getUsername());
            return ResponseEntity.ok("Messaggio inviato con successo");
        } catch (Exception e) {
            System.err.println("Errore invio messaggio: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}