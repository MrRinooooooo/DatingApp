package com.app.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Utente;
import com.app.enums.Genere;

public interface UtenteRepository extends JpaRepository<Utente, Long>{

    // Metodo esistente
	Optional<Utente> findByUsername(String email);
	
	@Query("SELECT u FROM Utente u WHERE u.username = :email")
	Utente findByUsernameSecure(String email);
    
    // Verifica se esiste un utente con questa email
    boolean existsByUsername(String email);
    
    // NUOVO: Trova utenti disponibili per swipe (esclusi se stesso + già swipati)
    @Query("SELECT u FROM Utente u WHERE u.id != :utenteId " +
           "AND (:utentiEsclusi IS NULL OR u.id NOT IN :utentiEsclusi)")
    List<Utente> findUtentiDaSwipare(@Param("utenteId") Long utenteId,
                                    @Param("utentiEsclusi") List<Long> utentiEsclusi);	

	@Query("SELECT u FROM Utente u JOIN u.preferenze p WHERE " +
		       "(:genere_preferito IS NULL OR u.genere = :genere_preferito) AND " +
		       "(:eta_minima IS NULL OR p.minEta <= :eta_minima) AND " +
		       "(:eta_massima IS NULL OR p.maxEta >= :eta_massima) AND " +
		       "(:distanza_massima IS NULL OR p.distanzaMax <= :distanza_massima)")
		List<Utente> findByPreferenze(@Param("genere_preferito") String generePreferito,
		                              @Param("eta_minima") Integer etaMinima,
		                              @Param("eta_massima") Integer etaMassima,
		                              @Param("distanza_massima") Double distanzaMax);
	
	// Visualizza lista utenti "premium"
	List<Utente> findByTipoAccount (String tipoAccount);
	
	// Visualizza utenti per preferenze	
	//(Haversine formula implementata per calcolare la distanza in base alle coordinate gps)	
	@Query(value = """
	    SELECT u.*,
	      (6371 * acos(
	        cos(radians(:lat)) * cos(radians(u.latitudine)) *
	        cos(radians(u.longitudine) - radians(:lon)) +
	        sin(radians(:lat)) * sin(radians(u.latitudine))
	      )) AS distanza
	    FROM utente u WHERE u.id <> :utenteId
	    AND u.id NOT IN (
	    SELECT s.utente_target_id FROM swipe s WHERE s.utente_id = :utenteId)   
	      AND (:genere IS NULL OR u.genere = :genere)
	      AND (:minEta IS NULL OR u.data_nascita <= :dataMin)
	      AND (:maxEta IS NULL OR u.data_nascita >= :dataMax)
	      AND (:distanzaMax IS NULL OR 
	           (6371 * acos(
	             cos(radians(:lat)) * cos(radians(u.latitudine)) *
	             cos(radians(u.longitudine) - radians(:lon)) +
	             sin(radians(:lat)) * sin(radians(u.latitudine))
	           )) <= :distanzaMax)
	    ORDER BY 
	      CASE WHEN u.tipo_account = 'PREMIUM' THEN 0 ELSE 1 END,
	      distanza ASC
	    """, nativeQuery = true)
	List<Utente> findUtentiByPreferenze(
	    @Param("utenteId") Long utenteId,
	    @Param("genere") String genere,
	    @Param("minEta") Integer minEta,
	    @Param("maxEta") Integer maxEta,
	    @Param("dataMin") LocalDate dataMin,
	    @Param("dataMax") LocalDate dataMax,
	    @Param("distanzaMax") Double distanzaMax,
	    @Param("lat") Double lat,
	    @Param("lon") Double lon,
	    Pageable pageable
	);
	
}

