package com.app.services;

import com.app.dto.MatchDTO;
import com.app.entities.Match;
import com.app.entities.Utente;
import com.app.repositories.MatchRepository;
import com.app.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchService {
    
    @Autowired
    private MatchRepository matchRepository;
    
    @Autowired
    private UtenteRepository utenteRepository;
    
    /**
     * Ottieni tutti i match di un utente - VERSIONE DTO (risolve problema JSON)
     */
    public List<MatchDTO> getMatchByUtente(String emailUtente) {
        
        System.out.println("=== MATCH SERVICE - GET MATCHES DTO ===");
        System.out.println("Email utente: " + emailUtente);
        
        try {
            // Trova l'utente che richiede i match
            Utente utente = utenteRepository.findByEmail(emailUtente)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
            
            System.out.println("Utente ID: " + utente.getId());
            
            // Trova tutti i match dell'utente
            List<Match> matches = matchRepository.findMatchesByUtenteId(utente.getId());
            
            System.out.println("Matches trovati: " + matches.size());
            
            // Converti in DTO per evitare problemi di serializzazione JSON
            List<MatchDTO> matchDTOs = matches.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
            
            System.out.println("MatchDTOs creati: " + matchDTOs.size());
            
            return matchDTOs;
            
        } catch (DataAccessException e) {
            throw new RuntimeException("Errore nel recupero dei match dal database", e);
        } catch (EntityNotFoundException e) {
            throw new RuntimeException("Utente non trovato", e);
        }
    }
    
    /**
     * Ottieni dettagli di un match specifico - VERSIONE DTO
     */
    public MatchDTO getMatchDetails(Long matchId, String emailUtente) {
        
        System.out.println("=== MATCH SERVICE - GET MATCH DETAILS DTO ===");
        System.out.println("Match ID: " + matchId);
        System.out.println("Email utente: " + emailUtente);
        
        try {
            // Trova l'utente
            Utente utente = utenteRepository.findByEmail(emailUtente)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
            
            // Trova il match specifico
            Match match = matchRepository.findById(matchId).orElse(null);
            
            if (match != null) {
                // Verifica che l'utente sia coinvolto nel match
                boolean isUserInMatch = match.getUtente1Id().getId().equals(utente.getId()) || 
                                       match.getUtente2Id().getId().equals(utente.getId());
                
                if (isUserInMatch) {
                    System.out.println("Match trovato e autorizzato");
                    return convertToDTO(match);
                } else {
                    System.out.println("Utente non autorizzato per questo match");
                    return null;
                }
            } else {
                System.out.println("Match non trovato");
                return null;
            }
            
        } catch (DataAccessException e) {
            throw new RuntimeException("Errore nel recupero del match dal database", e);
        }
    }
    
    /**
     * Verifica se un match esiste tra due utenti
     */
    public boolean existsMatchBetweenUsers(Long utente1Id, Long utente2Id) {
        
        System.out.println("=== MATCH SERVICE - CHECK MATCH EXISTS ===");
        System.out.println("Utente1 ID: " + utente1Id + " - Utente2 ID: " + utente2Id);
        
        try {
            boolean exists = matchRepository.existsMatchBetweenUsers(utente1Id, utente2Id);
            System.out.println("Match esiste: " + exists);
            return exists;
            
        } catch (DataAccessException e) {
            throw new RuntimeException("Errore nella verifica del match", e);
        }
    }
    
    /**
     * Crea un nuovo match tra due utenti
     */
    @Transactional
    public Match createMatch(Utente utente1, Utente utente2) {
        
        System.out.println("=== MATCH SERVICE - CREATE MATCH ===");
        System.out.println("Utente1: " + utente1.getId() + " - Utente2: " + utente2.getId());
        
        try {
            // Verifica che non esista già un match
            boolean matchExists = existsMatchBetweenUsers(utente1.getId(), utente2.getId());
            
            if (matchExists) {
                System.out.println("Match già esistente!");
                return null;
            }
            
            // Crea il nuovo match
            Match match = new Match();
            match.setUtente1Id(utente1);
            match.setUtente2Id(utente2);
            match.setTimestamp(java.time.LocalDateTime.now());
            
            Match savedMatch = matchRepository.save(match);
            
            System.out.println("Match creato con ID: " + savedMatch.getId());
            
            return savedMatch;
            
        } catch (DataAccessException e) {
            throw new RuntimeException("Errore nella creazione del match", e);
        }
    }
    
    /**
     * Elimina un match (unmatch)
     */
    @Transactional
    public boolean deleteMatch(Long matchId, String emailUtente) {
        
        System.out.println("=== MATCH SERVICE - DELETE MATCH ===");
        System.out.println("Match ID: " + matchId);
        System.out.println("Email utente: " + emailUtente);
        
        try {
            // Trova l'utente
            Utente utente = utenteRepository.findByEmail(emailUtente)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
            
            // Trova il match
            Match match = matchRepository.findById(matchId).orElse(null);
            
            if (match != null) {
                // Verifica che l'utente sia coinvolto nel match
                boolean isUserInMatch = match.getUtente1Id().getId().equals(utente.getId()) || 
                                       match.getUtente2Id().getId().equals(utente.getId());
                
                if (isUserInMatch) {
                    matchRepository.delete(match);
                    System.out.println("Match eliminato con successo");
                    return true;
                } else {
                    System.out.println("Utente non autorizzato per eliminare questo match");
                    return false;
                }
            } else {
                System.out.println("Match non trovato");
                return false;
            }
            
        } catch (DataAccessException e) {
            throw new RuntimeException("Errore nell'eliminazione del match", e);
        }
    }
    
    /**
     * Conta i match totali di un utente
     */
    public Long countMatchByUtente(String emailUtente) {
        
        System.out.println("=== MATCH SERVICE - COUNT MATCHES ===");
        System.out.println("Email utente: " + emailUtente);
        
        try {
            Utente utente = utenteRepository.findByEmail(emailUtente)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));
            
            Long count = matchRepository.countMatchesByUtenteId(utente.getId());
            
            System.out.println("Match totali: " + count);
            
            return count;
            
        } catch (DataAccessException e) {
            throw new RuntimeException("Errore nel conteggio dei match", e);
        }
    }
    
    // ========== METODI PRIVATI ==========
    
    /**
     * Converte un Match in MatchDTO per evitare problemi di serializzazione JSON
     */
    private MatchDTO convertToDTO(Match match) {
        MatchDTO dto = new MatchDTO();
        dto.setId(match.getId());
        dto.setTimestamp(match.getTimestamp());
        
        // Dati utente 1
        if (match.getUtente1Id() != null) {
            dto.setUtente1Id(match.getUtente1Id().getId());
            dto.setUtente1Nome(match.getUtente1Id().getNome());
            dto.setUtente1Email(match.getUtente1Id().getEmail());
        }
        
        // Dati utente 2
        if (match.getUtente2Id() != null) {
            dto.setUtente2Id(match.getUtente2Id().getId());
            dto.setUtente2Nome(match.getUtente2Id().getNome());
            dto.setUtente2Email(match.getUtente2Id().getEmail());
        }
        
        return dto;
    }
}