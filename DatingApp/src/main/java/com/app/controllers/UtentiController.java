package com.app.controllers;

import com.app.dto.UtenteDiscoverDTO;
import com.app.entities.Utente;
import com.app.repositories.SwipeRepository;
import com.app.repositories.UtenteRepository;
import com.app.services.PhotoService;
import com.app.services.SwipeService;
import com.app.services.UtenteService;
import com.app.utils.SecurityUtils;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
 
/**
 * Controller per la gestione degli utenti.
 * Esempi di utilizzo dell'autenticazione JWT.
 */
@RestController
@RequestMapping("/api/utenti")
public class UtentiController {
 
    @Autowired
    private UtenteService utenteService;
    
    @Autowired
    private PhotoService photoService;
    
    @Autowired
    private UtenteRepository utenteRepository;
    
    @Autowired
    private SwipeRepository swipeRepository;
    
    @Autowired
    private SwipeService swipeService;
 
Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    
    /**
     * Endpoint per ottenere il profilo dell'utente corrente.
     * Accessibile solo agli utenti autenticati.
     * GET /api/utenti/me
     */

    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile() {
    	
        try {
            // Ottiene l'email dell'utente corrente dal SecurityContext
            String currentUserEmail = SecurityUtils.getCurrentUserEmail();
 
            if (currentUserEmail == null) {
                return ResponseEntity.badRequest().body("Utente non autenticato");
            }
 
            // Carica il profilo completo dell'utente
            Utente utente = utenteService.findByEmail(currentUserEmail);
 
            return ResponseEntity.ok(utente);
 
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Errore nel recupero del profilo: " + e.getMessage());
        }
    }
    
    /**
     * Endpoint per aggiornare il profilo dell'utente corrente.
     * Accessibile solo agli utenti autenticati.
     * PUT /api/utenti/me
     */
    
    @PutMapping("/me")
    public ResponseEntity<?> updateMyProfile(@RequestBody Utente utenteAggiornato) {    	
    	String currentUserEmail = SecurityUtils.getCurrentUserEmail(); 					// Estraggo la username dell'utente loggato dal JWT token    	
        return utenteService.updateProfile(currentUserEmail, utenteAggiornato); 		// Aggiorna il profilo dell'utente
    }
 
    /**
     * Endpoint per visualizzare il profilo pubblico di un altro utente.
     * Accessibile solo agli utenti autenticati.
     * GET /api/utenti/{id}
     */

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
        try {
            // Verifica che l'utente sia autenticato
            if (!SecurityUtils.isAuthenticated()) {
                return ResponseEntity.badRequest().body("Utente non autenticato");
            }
 
            // Ottiene il profilo pubblico dell'utente
            Utente utente = utenteService.getPublicProfile(id);
 
            return ResponseEntity.ok(utente);
 
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Errore nel recupero del profilo: " + e.getMessage());
        }
    }
 
    /**
     * Endpoint per funzionalità premium.
     * Accessibile solo agli utenti con account premium.
     * GET /api/utenti/premium/who-liked-me
     */
    
    @GetMapping("/premium/who-liked-me")
    @PreAuthorize("hasRole('PREMIUM')") // Annotation per verificare il ruolo
    public ResponseEntity<?> whoLikedMe() {
        try {
        	Utente utente = utenteService.getCurrentUser();
        	
        	// Restituisce solo gli utenti che hanno fatto like a questo utente
            List<Utente> utentiWhoLikesMe = swipeRepository.findUtentiWhoLikedMe(utente.getId());
            List<UtenteDiscoverDTO> utentiWhoLikesMeDTO = swipeService.getUtentiCheMiHannoLikato(utentiWhoLikesMe);

            return ResponseEntity.ok(utentiWhoLikesMeDTO);
 
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Errore: " + e.getMessage());
        }
    }
    
    /**
     * Endpoint per aggiungere una foto profilo dell'utente
     * Accessibile solo agli utenti autenticati.
     */
    
    @PostMapping("/me/foto") 
    public ResponseEntity<?> uploadPhoto(@RequestBody UtenteDiscoverDTO fotoAggiunta) {
    	try {
    		// verifica se l'utente è autenticato
    		String currentUserEmail = SecurityUtils.getCurrentUserEmail();
    		if (currentUserEmail == null) {
    			return ResponseEntity.badRequest().body("Utente non autenticato");
    		}
    		
    		Optional<Utente> fotoEsistente = utenteRepository.findByUsername(currentUserEmail);
    		
    		if (fotoEsistente.isPresent() && fotoEsistente.get().getFotoProfilo() != null) {
    			return ResponseEntity.badRequest().body("Foto gia presente");
    		}
    		//aggiungi foto
    		Utente utente = photoService.addPhoto(currentUserEmail, fotoAggiunta);
    		return ResponseEntity.ok("foto aggiunta con successo " + fotoAggiunta);
    	} catch (Exception e) {
    		return ResponseEntity.badRequest().body("Errore durante l'aggiunta dell'immagine profilo");
    	}
    }
    
    @DeleteMapping("/me/foto")
    public ResponseEntity<?> deletePhoto() {
        try {
            String currentUserEmail = SecurityUtils.getCurrentUserEmail();
            if (currentUserEmail == null) {
                return ResponseEntity.badRequest().body("Utente non autenticato");    
            }
            photoService.deletePhoto(currentUserEmail );
            return ResponseEntity.ok("Foto eliminata con successo");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Errore durante l'eliminazione");
        }
    } 
}