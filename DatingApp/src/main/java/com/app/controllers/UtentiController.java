package com.app.controllers;

import com.app.dto.UtenteDiscoverDTO;
import com.app.entities.Utente;
import com.app.repositories.UtenteRepository;
import com.app.services.PhotoService;
import com.app.services.UtenteService;
import com.app.utils.SecurityUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
 
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
        																				//   String currentUserEmail = SecurityUtils.getCurrentUserEmail();
																						//System.out.println(currentUserEmail);
            // Questa funzionalità è disponibile solo per utenti premium
            // L'annotation @PreAuthorize si occupa già del controllo
 
            // Logica per ottenere gli utenti che hanno messo "like" - Da implementare
 
            return ResponseEntity.ok("Lista degli utenti che ti hanno messo like - Funzionalità Premium");
 
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Errore: " + e.getMessage());
        }
    }
 
    /**
     * Esempio di endpoint che verifica manualmente i permessi
     * GET /api/utenti/premium/boost-profile
     */
    
    @GetMapping("/premium/boost-profile")
    public ResponseEntity<?> boostProfile() {
        try {
            // Verifica manuale se l'utente è premium
            if (!SecurityUtils.isPremiumUser()) {
                return ResponseEntity.status(403).body("Funzionalità disponibile solo per utenti Premium");
            }
 
            																			// String currentUserEmail = SecurityUtils.getCurrentUserEmail();
            																			//System.out.println(currentUserEmail);
            // Logica per il boost del profilo - Da implementare
 
            return ResponseEntity.ok("Profilo boostato con successo - Funzionalità Premium");
 
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Errore: " + e.getMessage());
        }
    }
    
    /**
     * Endpoint per aggiungere una foto profilo dell'utente
     * Accessibile solo agli utenti autenticati.
     */
    
    @GetMapping("/me/foto")
    public ResponseEntity<?> viewPhoto() {
    try {
		// verifica se l'utente è autenticato
		String currentUserEmail = SecurityUtils.getCurrentUserEmail();
		if (currentUserEmail == null) {
			return ResponseEntity.badRequest().body("Utente non autenticato");
		}
		
		Utente utente = utenteRepository.findByUsername(currentUserEmail)
				.orElseThrow(() -> new RuntimeException("Utente non trovato"));	//modo professionale invece di optional utente
		
		String nomeFoto = utente.getFotoProfilo(); //abbiamo preso la foto
	
		if(nomeFoto == null || nomeFoto.isEmpty()) {
		    return ResponseEntity.badRequest().body("nessuna immagine trovata");
		}
		
		Path filePath = Paths.get("uploads", nomeFoto);
		//condizione
		if(!Files.exists(filePath)) {
			return ResponseEntity.badRequest().body("File non trovato");
		}
		
		//leggiamo il file
		byte[] imageBytes = Files.readAllBytes(filePath);
		
		
		//restituiamo l'immagine che verrà letta come array di byte
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_JPEG)
				.body(imageBytes);
		
    } catch(Exception e) {
    	e.printStackTrace();
		return ResponseEntity.badRequest().body("Errore durante la visualizzazione dell'immagine profilo" + e.getMessage());
    }
    }
    
    @PostMapping("/me/foto") 
    //USIAMO REQUESTPART PER IL MULTIPART 
   
    public ResponseEntity<?> uploadPhoto( @RequestPart("foto_profilo") MultipartFile file) {
    	try {
    		// verifica se l'utente è autenticato
    		String currentUserEmail = SecurityUtils.getCurrentUserEmail();
    		if (currentUserEmail == null) {
    			return ResponseEntity.badRequest().body("Utente non autenticato");
    		}
    		
    		Utente utente = photoService.addPhoto(currentUserEmail, file);
    		return ResponseEntity.ok("foto aggiunta con successo " + file);
    	} catch (Exception e) {
    		e.printStackTrace();
    		return ResponseEntity.badRequest().body("Errore durante l'aggiunta dell'immagine profilo" + e.getMessage());
    	}
    }
    
    @DeleteMapping("/me/foto")
    public ResponseEntity<?> deletePhoto(@RequestParam String fileName) {
        try {
            String currentUserEmail = SecurityUtils.getCurrentUserEmail();
            if (currentUserEmail == null) {
                return ResponseEntity.badRequest().body("Utente non autenticato");    
            }
            photoService.deletePhoto(currentUserEmail, fileName );
            return ResponseEntity.ok("Foto eliminata con successo");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Errore durante l'eliminazione" + e.getMessage());
        }
    } 
}