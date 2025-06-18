package com.app.services;

import com.app.entities.Swipe;
import com.app.entities.Utente;
import com.app.exceptions.UserNotFoundException;
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
    
    @Autowired
    private FirebaseService firebaseService;
    
    // ========== GET PROFILI DA SWIPARE ==========
    public List<UtenteDiscoverDTO> getProfilDaSwipare(String emailUtente) {
        try {
            Utente utente = utenteRepository.findByUsername(emailUtente)
            		.orElseThrow(() -> new UserNotFoundException("Utente non trovato con email: " + emailUtente));


            List<Long> utentiGiaSwipati = swipeRepository.findUtenteTargetIdsByUtenteSwipeId(utente.getId());

            List<Utente> utentiDisponibili = utenteRepository.findUtentiDaSwipare(
                utente.getId(), utentiGiaSwipati);

            return utentiDisponibili.stream()
                .map(this::convertToDiscoverDTO)
                .collect(Collectors.toList());

        } catch (DataAccessException e) {
            throw new RuntimeException("Errore recupero profili da swipare", e);
        }
    }

    // ========== ESEGUI SWIPE ==========
    @Transactional
    public String eseguiSwipe(SwipeDTO dto, Long senderId) {
        if (!dto.isValid()) {
            throw new IllegalArgumentException("Swipe non valido");
        }

        try {
            Utente utenteSwipe = utenteRepository.findById(senderId)
            		.orElseThrow(() -> new UserNotFoundException("Utente con ID " + senderId + " non trovato"));

            Utente utenteTarget = utenteRepository.findById(dto.getUtenteTargetId())
            	    .orElseThrow(() -> new UserNotFoundException("Utente target con ID " + dto.getUtenteTargetId() + " non trovato"));


            if (utenteSwipe == utenteTarget) {
                return "Non puoi creare uno swipe con te stesso";
            }

            boolean giaSwipato = swipeRepository.existsByUtenteSwipeIdAndUtenteTargetSwipeId(
                senderId, utenteTarget.getId());

            if (giaSwipato) {
                return "Hai già uno swipe con questo utente";
            }

            Swipe swipe = new Swipe();
            swipe.setUtenteSwipeId(utenteSwipe.getId());
            swipe.setUtenteTargetSwipeId(utenteTarget.getId());
            swipe.setTipo(dto.getTipo());
            swipe.setTimestamp(LocalDateTime.now());

            swipeRepository.save(swipe);

            if ("LIKE".equals(dto.getTipo()) || "SUPER_LIKE".equals(dto.getTipo())) {
                String risultato = controllaMatch(utenteSwipe.getId(), utenteTarget.getId());

                if (!risultato.startsWith("È UN MATCH")) {
                    if ("SUPER_LIKE".equals(dto.getTipo())) {
                        firebaseService.inviaNotificaSuperLike(utenteTarget.getId(), utenteSwipe.getNome());
                    } else if ("LIKE".equals(dto.getTipo())) {
                        firebaseService.inviaNotificaLike(utenteTarget.getId(), utenteSwipe.getNome());
                    }
                }

                return risultato;
            }

            return "Swipe salvato: " + dto.getTipo();

        } catch (DataAccessException e) {
            throw new RuntimeException("Errore salvataggio swipe", e);
        }
    }

    // ========== CONTROLLA MATCH ==========
    @Transactional
    private String controllaMatch(Long utente1Id, Long utente2Id) {
        Utente utente2 = utenteRepository.findById(utente2Id).get();

        boolean matchReciprico = swipeRepository.existsByUtenteSwipeIdAndUtenteTargetSwipeIdAndTipoIn(
            utente2Id, utente1Id, List.of("LIKE", "SUPER_LIKE"));

        if (matchReciprico) {
            boolean matchEsiste = matchRepository.existsMatchBetweenUsers(utente1Id, utente2Id);

            if (!matchEsiste) {
                Match match = new Match();
                match.setUtente1Id(utente1Id);
                match.setUtente2Id(utente2Id);
                match.setTimestamp(LocalDateTime.now());

                Match savedMatch = matchRepository.save(match);

                Utente utente1 = utenteRepository.findById(utente2Id).get();
                firebaseService.inviaNotificaMatch(utente1Id, utente2.getNome());
                firebaseService.inviaNotificaMatch(utente2Id, utente1.getNome());

                return "È UN MATCH! Ora puoi chattare con " + utente2.getNome();
            }
        }

        return "Like inviato a " + utente2.getNome();
    }

    public List<Match> getMatchUtente(String emailUtente) {
        try {
            Utente utente = utenteRepository.findByUsername(emailUtente)
                .orElseThrow(() -> new EntityNotFoundException("Utente non trovato"));

            return matchRepository.findMatchesByUtenteId(utente.getId());

        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero dei match", e);
        }
    }

    public List<Swipe> getUtentiCheMiHannoLikato(String emailUtente) {
        try {
        	Utente utente = utenteRepository.findByUsername(emailUtente)
                    .orElseThrow(() -> new UserNotFoundException("Utente con email " + emailUtente + " non trovato"));

            return swipeRepository.findLikesByUtenteTargetId(utente.getId());

        } catch (Exception e) {
            throw new RuntimeException("Errore nel recupero dei likes ricevuti", e);
        }
    }

    // ========== UTILITY ==========
    private UtenteDiscoverDTO convertToDiscoverDTO(Utente utente) {
        UtenteDiscoverDTO dto = new UtenteDiscoverDTO();
        dto.setId(utente.getId());
        dto.setNome(utente.getNome());
        dto.setBio(utente.getBio());
        dto.setInteressi(utente.getInteressi());
        dto.setFotoProfilo(utente.getFotoProfilo());
        dto.setCitta(utente.getPosizione() != null ? utente.getPosizione().getCitta() : "");

        if (utente.getDataNascita() != null) {
            dto.setEta(Period.between(utente.getDataNascita(), LocalDateTime.now().toLocalDate()).getYears());
        }

        return dto;
    }
}
