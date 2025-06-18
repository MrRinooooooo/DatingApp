package com.app.repositories;

import com.app.entities.Match;
import com.app.entities.Messaggio;
import com.app.entities.Utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessaggioRepository extends JpaRepository<Messaggio, Long> {
    
    // Trova tutti i messaggi di un match ordinati per timestamp crescente
    List<Messaggio> findByMatchOrderByTimestampAsc(Match match);
    
    // Trova tutti i messaggi di un match ordinati per timestamp decrescente
    List<Messaggio> findByMatchOrderByTimestampDesc(Match match);
    
    // Conta i messaggi non letti per un match
    long countByMatchAndStatoAndMittenteNot(Match match, String stato, Utente mittente);
}