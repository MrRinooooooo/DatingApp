package com.app.services;
import com.app.entities.Swipe;
import com.app.entities.Utente;
import com.app.exceptions.LimitReachedException;
import com.app.entities.Match;
import com.app.dto.SwipeDTO;
import com.app.repositories.SwipeRepository;
import com.app.repositories.UtenteRepository;
import com.app.repositories.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class SwipeService {
    
    @Autowired
    private SwipeRepository swipeRepository;
    
    @Autowired
    private UtenteRepository utenteRepository;
    
    @Autowired
    private MatchRepository matchRepository;
    
    @Autowired
    private FirebaseService firebaseService;
    
    @Autowired
    private UtenteService utenteService;  
        
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
            
   //CONTROLLO LIMITE LIKE E SUPERLIKE GIORNALIERI
            boolean isPremium = utenteService.isPremium();
            LocalDateTime inizioGiorno = LocalDate.now().atStartOfDay();
            LocalDateTime fineGiorno = LocalDate.now().atTime(LocalTime.MAX);
            
   // Conta LIKE giornalieri
            long likeOggi = swipeRepository.contaSwipeGiornalieri(senderId, "LIKE", inizioGiorno, fineGiorno);
            long superLikeOggi = swipeRepository.contaSwipeGiornalieri(senderId, "SUPER_LIKE", inizioGiorno, fineGiorno);
            
            System.out.println("Like di oggi: "+likeOggi);
            System.out.println("SuperLike di oggi: "+superLikeOggi);
            
   // Controlli per utenti standard
            
            System.out.println(!isPremium);
            
            if (!isPremium) {
                if (dto.getTipo().equals("LIKE") && likeOggi >= 20) {
                    throw new LimitReachedException("Hai raggiunto il limite giornaliero di 20 like.");
                }
                if (dto.getTipo().equals("SUPER_LIKE") && superLikeOggi >= 0) {
                    throw new LimitReachedException("I super like sono disponibili solo con abbonamento Premium.");
                }
            } else {
                if (dto.getTipo().equals("SUPER_LIKE") && superLikeOggi >= 5) {
                    throw new LimitReachedException("Hai raggiunto il limite giornaliero di 5 super like.");
                }
            }
        
            // Crea il nuovo swipe
            Swipe swipe = new Swipe();
            swipe.setUtenteSwipeId(utenteSwipe.getId());
            swipe.setUtenteTargetSwipeId(utenteTarget.getId());
            swipe.setTipo(dto.getTipo());
            swipe.setTimestamp(LocalDateTime.now());
            
            swipeRepository.save(swipe);
            System.out.println("Swipe salvato!");
            
            // Se è un LIKE, controlla se c'è reciprocità
            if ("LIKE".equals(dto.getTipo()) || "SUPER_LIKE".equals(dto.getTipo())) {
                String risultato = dto.getTipo() + controllaMatch(utenteSwipe.getId(), utenteTarget.getId());

                // Se NON è un match ma è un SUPER_LIKE, invia una notifica al target
                if ("SUPER_LIKE".equals(dto.getTipo()) && !risultato.startsWith("È UN MATCH")) {
                    firebaseService.inviaNotificaSuperLike(utenteTarget.getId(), utenteSwipe.getNome());
                }

               return risultato;
            }
            
            // Se lo swipe di tipo LIKE/SUPER_LIKE è reciproco creo un MATCH
            
            // Invio una notifica push ai due utenti coinvolti che è stao creato il Match
            
            return "Swipe salvato: " + dto.getTipo();
            
        } catch (DataAccessException e) {
            throw new RuntimeException("Errore salvataggio swipe", e);
        }
    }
    
    // ========== CONTROLLA MATCH ==========
    @Transactional
    private String controllaMatch(Long utente1Id, Long utente2Id) {
    	
    	Utente utente2 = utenteRepository.findById(utente2Id).get();
        
        System.out.println("=== CONTROLLO MATCH ===");
        System.out.println("Utente1: " + utente1Id + " -> Utente2: " + utente2Id);
        
        // Usa il metodo aggiornato del SwipeRepository
        boolean matchReciprico = swipeRepository.existsByUtenteSwipeIdAndUtenteTargetSwipeIdAndTipoIn(
            utente2Id, utente1Id, List.of("LIKE", "SUPER_LIKE"));
        
        if (matchReciprico) {
            System.out.println("MATCH TROVATO! Creazione match...");
            
            // Usa il metodo aggiornato del MatchRepository
            boolean matchEsiste = matchRepository.existsMatchBetweenUsers(
                utente1Id, utente2Id);
            
            if (!matchEsiste) {
                // Crea il match
                Match match = new Match();
                match.setUtente1Id(utente1Id);
                match.setUtente2Id(utente2Id);
                match.setTimestamp(LocalDateTime.now());
                
                Match savedMatch = matchRepository.save(match);
                System.out.println("Match creato con ID: " + savedMatch.getId());
                
                // invio notifiche Firebase ai due utenti
                Utente utente1 = utenteRepository.findById(utente2Id).get();
                firebaseService.inviaNotificaMatch(utente1Id, utente2.getNome());
                firebaseService.inviaNotificaMatch(utente2Id, utente1.getNome());
                
                return "È UN MATCH! Ora puoi chattare con " + utente2.getNome();
            } else {
                System.out.println("Match già esistente");
            }
        }
        
        return " inviato a " + utente2.getNome();
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
    
} 