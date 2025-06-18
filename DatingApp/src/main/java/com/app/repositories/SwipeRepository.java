package com.app.repositories;

import com.app.entities.Swipe;
import com.app.entities.Utente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface SwipeRepository extends JpaRepository<Swipe, Long> {

    /**
     * Trova tutti gli ID degli utenti già swipati da un utente
     */
    @Query("SELECT s.utenteTargetSwipe.id FROM Swipe s WHERE s.utenteSwipe.id = :utenteId")
    List<Long> findUtenteTargetIdsByUtenteSwipeId(@Param("utenteId") Long utenteId);

    /**
     * Verifica se esiste già uno swipe tra due utenti
     * METODO AGGIORNATO - compatibile con SwipeService
     */
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Swipe s WHERE s.utenteSwipe.id = :utenteSwipeId AND s.utenteTargetSwipe.id = :utenteTargetId")
    boolean existsByUtenteSwipeIdAndUtenteTargetSwipeId(@Param("utenteSwipeId") Long utenteSwipeId, 
                                                        @Param("utenteTargetId") Long utenteTargetId);

    /**
     * Verifica se esiste uno swipe reciproco di tipo LIKE o SUPER_LIKE
     * METODO AGGIORNATO - compatibile con SwipeService
     */
    @Query("SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END FROM Swipe s WHERE s.utenteSwipe.id = :utenteSwipeId AND s.utenteTargetSwipe.id = :utenteTargetId AND s.tipo IN :tipi")
    boolean existsByUtenteSwipeIdAndUtenteTargetSwipeIdAndTipoIn(@Param("utenteSwipeId") Long utenteSwipeId, 
                                                                @Param("utenteTargetId") Long utenteTargetId, 
                                                                @Param("tipi") List<String> tipi);

    /**
     * Trova tutti gli Utenti che hanno swipato un utenteId
     */

    @Query("SELECT s.utenteSwipe FROM Swipe s WHERE s.utenteTargetSwipe.id = :utenteId AND s.tipo IN ('LIKE', 'SUPER_LIKE') ORDER BY s.timestamp DESC")
    List<Utente> findUtentiWhoLikedMe(@Param("utenteId") Long utenteId);
    
    /**
     * Trova uno swipe specifico tra due utenti
     */
    @Query("SELECT s FROM Swipe s WHERE s.utenteSwipe.id = :utenteSwipeId AND s.utenteTargetSwipe.id = :utenteTargetId")
    Optional<Swipe> findByUtenteSwipeIdAndUtenteTargetSwipeId(@Param("utenteSwipeId") Long utenteSwipeId, 
                                                              @Param("utenteTargetId") Long utenteTargetId);

    /**
     * Trova tutti gli swipe fatti da un utente
     */
    @Query("SELECT s FROM Swipe s WHERE s.utenteSwipe.id = :utenteId ORDER BY s.timestamp DESC")
    List<Swipe> findByUtenteSwipeId(@Param("utenteId") Long utenteId);

    /**
     * Trova tutti gli swipe ricevuti da un utente
     */
    @Query("SELECT s FROM Swipe s WHERE s.utenteTargetSwipe.id = :utenteId ORDER BY s.timestamp DESC")
    List<Swipe> findByUtenteTargetSwipeId(@Param("utenteId") Long utenteId);

    /**
     * Trova tutti gli swipe di un tipo specifico
     */
    @Query("SELECT s FROM Swipe s WHERE s.tipo = :tipo ORDER BY s.timestamp DESC")
    List<Swipe> findByTipo(@Param("tipo") String tipo);
    
    // METODO PER CONTEGGIO GIORNALIERO LIKE / SUPER_LIKE    
    @Query("SELECT COUNT(s) FROM Swipe s WHERE s.utenteSwipe.id = :utenteId AND s.tipo = :tipo AND s.timestamp BETWEEN :start AND :end")
    long contaSwipeGiornalieri(
        @Param("utenteId") Long utenteId,
        @Param("tipo") String tipo,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
    
}