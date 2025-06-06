package com.app.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long>{

	Optional<Utente> findByUsername(String email); // restituisce un utente con quell'email, se esiste
	
	boolean existsByUsername(String email); // true se esiste un utente con quell'email
	
}
