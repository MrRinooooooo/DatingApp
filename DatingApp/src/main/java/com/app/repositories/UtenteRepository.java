package com.app.repositories;

import com.app.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Long> {
    
    // Metodo esistente
    Optional<Utente> findByEmail(String email);
    
    // Verifica se esiste un utente con questa email
    boolean existsByEmail(String email);
    
    // NUOVO: Trova utenti disponibili per swipe (esclusi se stesso e già swipati)
    @Query("SELECT u FROM Utente u WHERE u.id != :utenteId " +
           "AND (:utentiEsclusi IS NULL OR u.id NOT IN :utentiEsclusi)")
    List<Utente> findUtentiDaSwipare(@Param("utenteId") Long utenteId, 
                                   @Param("utentiEsclusi") List<Long> utentiEsclusi);
}