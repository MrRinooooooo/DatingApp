package com.app.repositories;


import com.app.entities.Match;
import com.app.entities.Utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    /**
     * Trova tutti i match di un utente (sia come utente1 che come utente2)
     * METODO CORRETTO - usa @Query per maggiore chiarezza
     */
    @Query("SELECT m FROM Match m WHERE m.utente1 = :utente OR m.utente2 = :utente ORDER BY m.timestamp DESC")
    List<Match> findMatchesByUtente(@Param("utente") Utente utente);
    
    /**
     * Verifica se esiste un match tra due utenti (in qualsiasi direzione)
     * METODO CORRETTO - sostituisce quello problematico
     */
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Match m " +
           "WHERE (m.utente1 = :utente1 AND m.utente2 = :utente2) " +
           "OR (m.utente1 = :utente2 AND m.utente2 = :utente1)")
    boolean existsMatchBetweenUsers(@Param("utente1") Utente utente1, 
                                   @Param("utente2") Utente utente2);
    
    /**
     * Trova un match specifico tra due utenti
     */
    @Query("SELECT m FROM Match m WHERE " +
           "(m.utente1 = :utente1 AND m.utente2 = :utente2) " +
           "OR (m.utente1 = :utente2 AND m.utente2 = :utente1)")
    Optional<Match> findMatchBetweenUsers(@Param("utente1") Utente utente1, 
                                         @Param("utente2") Utente utente2);
    
    /**
     * Conta i match totali di un utente
     */
    @Query("SELECT COUNT(m) FROM Match m WHERE m.utente1.id = :utenteId OR m.utente2.id = :utenteId")
    Long countMatchesByUtente(@Param("utenteId") Utente utenteId);
    
    // ========== METODI LEGACY (puoi tenerli se li usi altrove) ==========
    
    /**
     * Metodo legacy - trova match (meno efficiente)
     * NOTA: Per usarlo correttamente devi passare lo stesso ID due volte
     */
    
    List<Match> findByUtente1IdOrUtente2Id(Utente utente1Id, Utente utente2Id);
}