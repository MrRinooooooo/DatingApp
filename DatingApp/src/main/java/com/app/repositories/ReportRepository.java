package com.app.repositories;

import com.app.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    
    // Verifica se esiste già una segnalazione tra due utenti
    boolean existsBySegnalanteIdAndSegnalatoId(Long segnalanteId, Long segnalatoId);
    
    // Trova tutte le segnalazioni ricevute da un utente
    List<Report> findBySegnalatoId(Long segnalatoId);
    
    // Trova tutte le segnalazioni fatte da un utente
    List<Report> findBySegnalanteId(Long segnalanteId);
    
    // Trova tutte le segnalazioni con un motivo specifico
    List<Report> findByMotivoContainingIgnoreCase(String motivo);
}