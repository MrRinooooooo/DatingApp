package com.app.repositories;

import com.app.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    
    
    List<Report> findBySegnalato_Id(Long segnalatoId);
    
    List<Report> findBySegnalante_Id(Long segnalanteId);
    
    boolean existsBySegnalante_IdAndSegnalato_Id(Long segnalanteId, Long segnalatoId);
}