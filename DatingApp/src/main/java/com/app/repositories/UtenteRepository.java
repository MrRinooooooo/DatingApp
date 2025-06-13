package com.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Utente;

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
	
	List<Utente> findByTipoAccount (String tipoAccount);
}

