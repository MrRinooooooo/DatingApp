package com.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.MessaggioDTO;
import com.app.services.MessaggioService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/chat")
public class MessaggioController {

    @Autowired
    private MessaggioService messaggioService;

    @PostMapping("/{matchId}/messaggi")
    public ResponseEntity<String> inviaMessaggio(
        @PathVariable Long matchId,
        @RequestBody MessaggioDTO dto,
        @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(401).body("Utente non autenticato");
        }

        try {
            messaggioService.inviaMessaggio(matchId, dto, userDetails.getUsername());
            return ResponseEntity.ok("Messaggio inviato");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Errore interno");
        }
    }

    @GetMapping("/{matchId}/messaggi")
    public ResponseEntity<List<MessaggioDTO>> getMessaggi(
        @PathVariable Long matchId,
        @AuthenticationPrincipal UserDetails userDetails) {

        if (userDetails == null) {
            return ResponseEntity.status(401).build();
        }

        try {
            List<MessaggioDTO> messaggi = messaggioService.getMessaggiByMatch(matchId, userDetails.getUsername());
            return ResponseEntity.ok(messaggi);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
