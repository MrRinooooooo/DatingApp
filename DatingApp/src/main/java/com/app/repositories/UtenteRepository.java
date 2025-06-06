package com.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long>{

	Optional<Utente> findByEmail(String email); // restituisce un utente con quell'email, se esiste
	

	@Query("SELECT u FROM Utente u JOIN u.preferenze p WHERE " +
		       "(:genere_preferito IS NULL OR u.genere = :genere_preferito) AND " +
		       "(:eta_minima IS NULL OR p.minEta <= :eta_minima) AND " +
		       "(:eta_massima IS NULL OR p.maxEta >= :eta_massima) AND " +
		       "(:distanza_massima IS NULL OR p.distanzaMax <= :distanza_massima)")
		List<Utente> findByPreferenze(@Param("genere_preferito") String generePreferito,
		                              @Param("eta_minima") Integer etaMinima,
		                              @Param("eta_massima") Integer etaMassima,
		                              @Param("distanza_massima") Double distanzaMax);


	boolean existsByEmail(String email); // true se esiste un utente con quell'email
	

}
