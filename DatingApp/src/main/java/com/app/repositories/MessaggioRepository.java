package com.app.repositories;

import com.app.entities.Messaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface MessaggioRepository extends JpaRepository<Messaggio, Long> {
    
    // Trova tutti i messaggi di un match ordinati per timestamp crescente
    List<Messaggio> findByMatchIdOrderByTimestampAsc(Long matchId);
    
    // Trova tutti i messaggi di un match ordinati per timestamp decrescente
    List<Messaggio> findByMatchIdOrderByTimestampDesc(Long matchId);
    
    // Conta i messaggi non letti per un match
    long countByMatchIdAndStatoAndMittenteIdNot(Long matchId, String stato, Long mittenteId);
}