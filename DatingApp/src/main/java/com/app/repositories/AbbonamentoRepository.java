package com.app.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entities.Abbonamento;

@Repository
public interface AbbonamentoRepository extends JpaRepository <Abbonamento, Long> {

    @Query("SELECT a FROM Abbonamento a WHERE a.utenteId = :utenteId ORDER BY a.dataFine ASC")
    List<Abbonamento> findByUtenteIdOrderByDataFine(@Param("utenteId") Long utenteId);
	
    Optional<Abbonamento> findFirstByUtenteIdOrderByDataFineDesc(Long utenteId);
}
