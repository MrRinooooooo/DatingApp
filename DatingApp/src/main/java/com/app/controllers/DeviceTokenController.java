package com.app.controllers;

import com.app.services.UtenteService;
import com.app.services.FirebaseService;
import com.app.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/api/device")

@Tag(name = "DeviceTokenController", description = "(Gestione notifiche push, implementazione Firebase)")
public class DeviceTokenController {

    @Autowired
    private UtenteService utenteService;
    
    @Autowired
    private FirebaseService firebaseService;

    // Aggiorna device token per notifiche push
    @PostMapping("/token")
    public ResponseEntity<?> updateDeviceToken(@RequestBody Map<String, String> request) {
        try {
            String currentUserEmail = SecurityUtils.getCurrentUserEmail();
            if (currentUserEmail == null) {
                return ResponseEntity.status(401).body("Utente non autenticato");
            }
            
            String deviceToken = request.get("deviceToken");
            if (deviceToken == null || deviceToken.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Device token richiesto");
            }
            
            utenteService.updateDeviceToken(currentUserEmail, deviceToken);
            
            return ResponseEntity.ok(Map.of(
                "message", "Device token aggiornato con successo",
                "success", true
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", "Errore aggiornamento device token: " + e.getMessage(),
                "success", false
            ));
        }
    }

    // Abilita/Disabilita notifiche
    @PostMapping("/notifications/toggle")
    public ResponseEntity<?> toggleNotifications(@RequestBody Map<String, Boolean> request) {
        try {
            String currentUserEmail = SecurityUtils.getCurrentUserEmail();
            if (currentUserEmail == null) {
                return ResponseEntity.status(401).body("Utente non autenticato");
            }
            
            Boolean enabled = request.get("enabled");
            if (enabled == null) {
                return ResponseEntity.badRequest().body("Parametro 'enabled' richiesto");
            }
            
            utenteService.toggleNotifications(currentUserEmail, enabled);
            
            return ResponseEntity.ok(Map.of(
                "message", "Notifiche " + (enabled ? "abilitate" : "disabilitate"),
                "success", true,
                "notificationsEnabled", enabled
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", "Errore gestione notifiche: " + e.getMessage(),
                "success", false
            ));
        }
    }

    // Test notifica (solo per sviluppo)
    @PostMapping("/test-notification")
    public ResponseEntity<?> testNotification() {
        try {
            String currentUserEmail = SecurityUtils.getCurrentUserEmail();
            if (currentUserEmail == null) {
                return ResponseEntity.status(401).body("Utente non autenticato");
            }
            
            Long currentUserId = utenteService.getCurrentUser().getId();
            firebaseService.inviaNotificaMatch(currentUserId, "Utente Test");
            
            return ResponseEntity.ok(Map.of(
                "message", "Notifica di test inviata",
                "success", true
            ));
            
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                "error", "Errore invio notifica test: " + e.getMessage(),
                "success", false
            ));
        }
    }
}