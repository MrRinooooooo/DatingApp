package com.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entities.Abbonamento;
import com.app.entities.Utente;

@Repository
public interface AbbonamentoRepository extends JpaRepository <Abbonamento, Long> {

    @Query("SELECT a FROM Abbonamento a WHERE a.utente = :utente ORDER BY a.dataFine DESC")
    List<Abbonamento> findByUtenteIdOrderByDataFine(@Param("utente") Utente utente);
	
    Optional<Abbonamento> findFirstByUtenteOrderByDataFineDesc(Utente utente);
}
