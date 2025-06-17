package com.app.services;
import com.app.services.FirebaseService;
import com.app.entities.Match;
import com.app.entities.Messaggio;
import com.app.entities.Utente;
import com.app.dto.MessaggioDTO;
import com.app.repositories.MatchRepository;
import com.app.repositories.MessaggioRepository;
import com.app.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessaggioService {
    
    @Autowired
    private MessaggioRepository messaggioRepository;
    
    @Autowired
    private MatchRepository matchRepository;
    
    @Autowired
    private UtenteRepository utenteRepository;
    
    @Autowired
    private FirebaseService firebaseService;
    
    // ========== GET MESSAGGI PER MATCH ==========
    public List<MessaggioDTO> getMessaggiByMatch(Long matchId, String emailUtente) {
        
        System.out.println("=== MESSAGGIO SERVICE DEBUG ===");
        System.out.println("Match ID: " + matchId);
        System.out.println("Email utente: " + emailUtente);
        
        try {
            // Trova l'utente che sta facendo la richiesta
            Utente utente = utenteRepository.findByUsername(emailUtente)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
            
            // Trova il match
            Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new EntityNotFoundException("Match non trovato"));
            
            // Verifica che l'utente faccia parte del match
            if (!match.getUtente1Id().equals(utente.getId()) && 
                !match.getUtente2Id().equals(utente.getId())) {
                throw new IllegalArgumentException("Non sei autorizzato a vedere questa chat");
            }
            
            System.out.println("Utente autorizzato: " + utente.getId());
            
            // Recupera tutti i messaggi del match ordinati per timestamp
            List<Messaggio> messaggi = messaggioRepository.findByMatchIdOrderByTimestampAsc(matchId);
            
            System.out.println("Messaggi trovati: " + messaggi.size());
            
            // SETTIAMO I TUTTI I MESSAGGI RICEVUTI COME LETTI
            
            List<Messaggio> messaggiDaAggiornare = new ArrayList<>();

            for (Messaggio m : messaggi) {
                if (!m.getMittenteId().equals(utente.getId()) && !m.getStato().equals("letto")) {
                    messaggiDaAggiornare.add(m);
                }
            }

            for (Messaggio m : messaggiDaAggiornare) {
                m.setStato("letto");
            }

            messaggioRepository.saveAll(messaggiDaAggiornare);
            
            
            return messaggi.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
                
        } catch (DataAccessException e) {
            throw new RuntimeException("Errore recupero messaggi", e);
        }
    }
    
    // ========== INVIA MESSAGGIO ==========
    public void inviaMessaggio(Long matchId, MessaggioDTO dto, String emailMittente) {
        
        System.out.println("=== INVIA MESSAGGIO SERVICE DEBUG ===");
        System.out.println("Match ID: " + matchId);
        System.out.println("Email mittente: " + emailMittente);
        System.out.println("Contenuto: " + dto.getContenuto());
        
        try {
            // Trova il mittente
            Utente mittente = utenteRepository.findByUsername(emailMittente)
                .orElseThrow(() -> new EntityNotFoundException("Utente mittente non trovato"));
            
            // Trova il match
            Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new EntityNotFoundException("Match non trovato"));
            
            // Verifica che il mittente faccia parte del match
            if (!match.getUtente1Id().equals(mittente.getId()) &&
                !match.getUtente2Id().equals(mittente.getId())) {
                throw new IllegalArgumentException("Non sei autorizzato a scrivere in questa chat");
            }
            
            // Valida il contenuto
            if (dto.getContenuto() == null || dto.getContenuto().trim().isEmpty()) {
                throw new IllegalArgumentException("Il messaggio non può essere vuoto");
            }
            
            if (dto.getContenuto().length() > 1000) {
                throw new IllegalArgumentException("Il messaggio è troppo lungo (max 1000 caratteri)");
            }
            
            // Crea il messaggio
            Messaggio messaggio = new Messaggio();
            messaggio.setMatchId(matchId);
            messaggio.setMittenteId(mittente.getId());
            messaggio.setContenuto(dto.getContenuto().trim());
            messaggio.setTimestamp(LocalDateTime.now());
            messaggio.setStato("inviato");
            
            messaggioRepository.save(messaggio);
            System.out.println("Messaggio salvato con successo!");
            
            // Trovo il destinatario del messaggio
            Long destinatarioId = match.getUtente1Id().equals(mittente.getId()) ?
            		match.getUtente2Id() : match.getUtente1Id();
            
            // invio notifica Firebase al destinatario
            firebaseService.inviaNotificaMessaggio(destinatarioId, mittente.getNome(), dto.getContenuto());
            
        } catch (DataAccessException e) {
            throw new RuntimeException("Errore salvataggio messaggio", e);
        }
    }
    
    // ========== METODI PRIVATI ==========
    
    private MessaggioDTO convertToDTO(Messaggio entity) {
        MessaggioDTO dto = new MessaggioDTO();
        dto.setId(entity.getId());
        dto.setMatchId(entity.getMatchId());
        dto.setMittenteId(entity.getMittenteId());
        dto.setContenuto(entity.getContenuto());
        dto.setTimestamp(entity.getTimestamp());
        dto.setStato(entity.getStato());
        return dto;
    }
}