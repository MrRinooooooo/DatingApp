package com.app.repostitories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.entities.Utente;

public interface UtenteRepository extends JpaRepository<Utente, Long>{

}
