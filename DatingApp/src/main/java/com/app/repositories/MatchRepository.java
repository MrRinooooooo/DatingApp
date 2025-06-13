package com.app.repositories;


import com.app.entities.Match;
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
    @Query("SELECT m FROM Match m WHERE m.utente1Id = :utenteId OR m.utente2Id = :utenteId ORDER BY m.timestamp DESC")
    List<Match> findMatchesByUtenteId(@Param("utenteId") Long utenteId);
    
    /**
     * Verifica se esiste un match tra due utenti (in qualsiasi direzione)
     * METODO CORRETTO - sostituisce quello problematico
     */
    @Query("SELECT CASE WHEN COUNT(m) > 0 THEN true ELSE false END FROM Match m " +
           "WHERE (m.utente1Id = :utente1Id AND m.utente2Id = :utente2Id) " +
           "OR (m.utente1Id = :utente2Id AND m.utente2Id = :utente1Id)")
    boolean existsMatchBetweenUsers(@Param("utente1Id") Long utente1Id, 
                                   @Param("utente2Id") Long utente2Id);
    
    /**
     * Trova un match specifico tra due utenti
     */
    @Query("SELECT m FROM Match m WHERE " +
           "(m.utente1Id = :utente1Id AND m.utente2Id = :utente2Id) " +
           "OR (m.utente1Id = :utente2Id AND m.utente2Id = :utente1Id)")
    Optional<Match> findMatchBetweenUsers(@Param("utente1Id") Long utente1Id, 
                                         @Param("utente2Id") Long utente2Id);
    
    /**
     * Conta i match totali di un utente
     */
    @Query("SELECT COUNT(m) FROM Match m WHERE m.utente1Id = :utenteId OR m.utente2Id = :utenteId")
    Long countMatchesByUtenteId(@Param("utenteId") Long utenteId);
    
    // ========== METODI LEGACY (puoi tenerli se li usi altrove) ==========
    
    /**
     * Metodo legacy - trova match (meno efficiente)
     * NOTA: Per usarlo correttamente devi passare lo stesso ID due volte
     */
    
    List<Match> findByUtente1IdOrUtente2Id(Long utente1Id, Long utente2Id);
}