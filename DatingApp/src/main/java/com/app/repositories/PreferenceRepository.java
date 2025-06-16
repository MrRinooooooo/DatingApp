package com.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Preferenze;
import com.app.entities.Utente;

public interface PreferenceRepository extends JpaRepository<Preferenze, Long> {
	
	// Trova le preferenze di un utente in base al suo ID
	Optional<Preferenze> findByUtenteId(Long UtenteId);
	
	//@Query("SELECT p FROM Preferenze p WHERE p.utente.id = :utenteId")
	//Optional<Preferenze> findByUtenteId(@Param("utenteId") Long utenteId);
	
	
	
}
