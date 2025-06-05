package com.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.app.entities.Preferenze;
import com.app.entities.Utente;

public interface PreferenceRepository extends JpaRepository<Preferenze, Long> {
	
	@Query("SELECT p FROM Preferenze p WHERE p.utente.id = :utenteId")
	Optional<Preferenze> findByUtenteId(@Param("utenteId") Long utenteId);

	
}
