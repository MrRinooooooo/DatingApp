package com.app.services;

import com.app.entities.Swipe;
import com.app.entities.Utente;
import com.app.entities.Match;
import com.app.dto.SwipeDTO;
import com.app.dto.UtenteDiscoverDTO;
import com.app.repositories.SwipeRepository;
import com.app.repositories.UtenteRepository;
import com.app.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SwipeService {
    
    @Autowired
    private SwipeRepository swipeRepository;
    
    @Autowired
    private UtenteRepository utenteRepository;
    
    @Autowired
    private MatchRepository matchRepository;
    
    // ========== GET PROFILI DA SWIPARE ==========
    public List<UtenteDiscoverDTO> getProfilDaSwipare(String emailUtente) {
        
        System.out.println("=== SWIPE SERVICE DISCOVER DEBUG ===");
        System.out.println("Email utente: " + emailUtente);
        
        try {
            // Trova l'utente che sta cercando profili
            Utente utente = utenteRepository.findByUsername(emailUtente)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
            
            System.out.println("Utente ID: " + utente.getId());
            
            // Trova tutti gli utenti già swipati da questo utente
            List<Long> utentiGiaSwipati = swipeRepository.findUtenteTargetIdsByUtenteSwipe(utente.getId());
            
            System.out.println("Utenti già swipati: " + utentiGiaSwipati.size());
            
            // Trova tutti gli utenti disponibili (esclusi: se stesso + già swipati)
            List<Utente> utentiDisponibili = utenteRepository.findUtentiDaSwipare(
                utente.getId(), utentiGiaSwipati);
            
            System.out.println("Utenti disponibili: " + utentiDisponibili.size());
            
            // Converti in DTO
            return utentiDisponibili.stream()
                .map(this::convertToDiscoverDTO)
                .collect(Collectors.toList());
                
        } catch (DataAccessException e) {
            throw new RuntimeException("Errore recupero profili da swipare", e);
        }
    }
    
    // ========== ESEGUI SWIPE ==========
    @Transactional 	// Esegue il salvataggio su database solo se va tutto bene altrimenti
    				// la transazione viene annullata ed il database ripristinato allo stato precedente
    public String eseguiSwipe(SwipeDTO dto, Long senderId) {
        
        System.out.println("=== SWIPE SERVICE ESEGUI DEBUG ===");
        System.out.println("Mittente ID: " + senderId);        
        System.out.println("Target ID: " + dto.getUtenteTargetId());
        System.out.println("Tipo swipe: " + dto.getTipo());
        
        // Validazione
        if (!dto.isValid()) {
            throw new IllegalArgumentException("Swipe non valido");
        }
        
        try {
            // Verifica che esiste l'utente che sta facendo swipe
            Utente utenteSwipe = utenteRepository.findById(senderId)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
            
            // Verifica che esiste l'utente target
            Utente utenteTarget = utenteRepository.findById(dto.getUtenteTargetId())
                .orElseThrow(() -> new EntityNotFoundException("Utente target non trovato"));
            
            if (utenteSwipe == utenteTarget) {
            	return "Non puoi creare uno swipe con te stesso";
            }
            
            // ✅ CORRETTO: Usa il metodo aggiornato del SwipeRepository
            // Verifica se esiste già uno swipe tra mittente e target
            boolean giaSwipato = swipeRepository.existsByUtenteSwipeIdAndUtenteTargetSwipeId(
                senderId, utenteTarget.getId());
                
            if (giaSwipato) {
                return "Hai già uno swipe con questo utente";
            }
            
            // Crea il nuovo swipe
            Swipe swipe = new Swipe();
            swipe.setUtenteSwipe(utenteSwipe);
            swipe.setUtenteTargetSwipe(utenteTarget);
            swipe.setTipo(dto.getTipo());
            swipe.setTimestamp(LocalDateTime.now());
            
            swipeRepository.save(swipe);
            System.out.println("Swipe salvato!");
            
            // Se è un LIKE, controlla se c'è reciprocità
            if ("LIKE".equals(dto.getTipo()) || "SUPER_LIKE".equals(dto.getTipo())) {
                return controllaMatch(utenteSwipe, utenteTarget);
            }            
            
            // Invio una notifica push ai due utenti coinvolti che è stao creato il Match
            
            return "Swipe salvato: " + dto.getTipo();
            
        } catch (DataAccessException e) {
            throw new RuntimeException("Errore salvataggio swipe", e);
        }
    }
    
    // ========== CONTROLLA MATCH ==========
    @Transactional
    private String controllaMatch(Utente utente1, Utente utente2) {
        
        System.out.println("=== CONTROLLO MATCH ===");
        System.out.println("Utente1: " + utente1.getId() + " -> Utente2: " + utente2.getId());
        
        // ✅ CORRETTO: Usa il metodo aggiornato del SwipeRepository
        boolean matchReciprico = swipeRepository.existsByUtenteSwipeIdAndUtenteTargetSwipeIdAndTipoIn(
            utente2.getId(), utente1.getId(), List.of("LIKE", "SUPER_LIKE"));
        
        if (matchReciprico) {
            System.out.println("MATCH TROVATO! Creazione match...");
            
            // ✅ CORRETTO: Usa il metodo aggiornato del MatchRepository
            boolean matchEsiste = matchRepository.existsMatchBetweenUsers(
                utente1.getId(), utente2.getId());
            
            if (!matchEsiste) {
                // Crea il match
                Match match = new Match();
                match.setUtente1Id(utente1);
                match.setUtente2Id(utente2);
                match.setTimestamp(LocalDateTime.now());
                
                Match savedMatch = matchRepository.save(match);
                System.out.println("Match creato con ID: " + savedMatch.getId());
                
                return "🎉 È UN MATCH! Ora puoi chattare con " + utente2.getNome();
            } else {
                System.out.println("Match già esistente");
            }
        }
        
        return "Like inviato a " + utente2.getNome();
    }
    
    // ========== METODI AGGIUNTIVI ==========
    
    /**
     * Ottieni tutti i match di un utente (metodo di supporto)
     */
    public List<Match> getMatchUtente(String emailUtente) {
        try {
            Utente utente = utenteRepository.findByUsername(emailUtente)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
            
            return matchRepository.findMatchesByUtenteId(utente.getId());
            
        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero dei match", e);
        }
    }
    
    /**
     * Ottieni tutti gli utenti che hanno fatto like all'utente corrente
     */
    public List<Swipe> getUtentiCheMiHannoLikato(String emailUtente) {
        try {
            Utente utente = utenteRepository.findByUsername(emailUtente)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
            
            return swipeRepository.findLikesByUtenteTargetId(utente.getId());
            
        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero dei likes ricevuti", e);
        }
    }
    
    // ========== METODI PRIVATI ==========
    
    private UtenteDiscoverDTO convertToDiscoverDTO(Utente utente) {
        UtenteDiscoverDTO dto = new UtenteDiscoverDTO();
        dto.setId(utente.getId());
        dto.setNome(utente.getNome());
        dto.setBio(utente.getBio());
        dto.setInteressi(utente.getInteressi());
        dto.setFotoProfilo(utente.getFotoProfilo());
        dto.setCitta(utente.getPosizione() != null ? utente.getPosizione().getCitta() : "");
        
        // Calcola età dalla data di nascita
        if (utente.getDataNascita() != null) {
            dto.setEta(Period.between(utente.getDataNascita(), LocalDateTime.now().toLocalDate()).getYears());
        }
        
        return dto;
    }
}