package com.app.services;

import com.app.entities.Report;
import com.app.entities.Utente;
import com.app.dto.ReportDTO;
import com.app.repositories.ReportRepository;
import com.app.repositories.UtenteRepository;
import com.app.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    
    @Autowired
    private ReportRepository reportRepository;
    
    @Autowired
    private UtenteRepository utenteRepository;
    
    // ========== CREA SEGNALAZIONE ==========
    public void creaReport(ReportDTO dto) {
        
        // Validazioni
        validateReportDTO(dto);
        
        // Ottengo utente che fa la segnalazione
        String email = SecurityUtils.getCurrentUserEmail();
        
        try {
            Utente segnalante = utenteRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Utente segnalante non trovato"));
            
            // Ottengo utente da segnalare
            Utente segnalato = utenteRepository.findById(dto.getSegnalatoId())
                .orElseThrow(() -> new IllegalArgumentException("Utente da segnalare non trovato"));
            
            // Controllo che non stia segnalando se stesso
            if (segnalante.getId().equals(segnalato.getId())) {
                throw new IllegalArgumentException("Non puoi segnalare te stesso");
            }
            
            // Controllo che non abbia già segnalato questo utente
            boolean giaSegnalato = reportRepository.existsBySegnalante_IdAndSegnalato_Id(
                segnalante.getId(), segnalato.getId());
            if (giaSegnalato) {
                throw new IllegalArgumentException("Hai già segnalato questo utente");
            }
            
            // Creo il report
            Report report = new Report();
            report.setSegnalato(segnalato);        
            report.setSegnalante(segnalante);      
            report.setMotivo(dto.getMotivo());
            report.setTimestamp(LocalDateTime.now());
            
            reportRepository.save(report);
            
        } catch (DataAccessException e) {
            throw new RuntimeException("Errore salvataggio segnalazione", e);
        }
    }
    
    // ========== GET REPORT PER UTENTE ==========
    public List<ReportDTO> getReportsByUtente(Long utenteId) {
        try {
            List<Report> reports = reportRepository.findBySegnalato_Id(utenteId);
            return reports.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
                
        } catch (DataAccessException e) {
            throw new RuntimeException("Errore recupero segnalazioni utente", e);
        }
    }
    
    // ========== METODI PRIVATI ==========
    
    private void validateReportDTO(ReportDTO dto) {
        if (dto.getSegnalatoId() == null) {
            throw new IllegalArgumentException("ID utente da segnalare obbligatorio");
        }
        
        if (dto.getMotivo() == null || dto.getMotivo().trim().isEmpty()) {
            throw new IllegalArgumentException("Motivo segnalazione obbligatorio");
        }
        
        if (dto.getMotivo().length() > 1000) {
            throw new IllegalArgumentException("Motivo troppo lungo (max 1000 caratteri)");
        }
        
        // Normalizza motivo
        dto.setMotivo(dto.getMotivo().trim());
    }
    
    private ReportDTO convertToDTO(Report entity) {
        ReportDTO dto = new ReportDTO();
        dto.setId(entity.getId());
        dto.setSegnalatoId(entity.getSegnalato().getId());      
        dto.setSegnalanteId(entity.getSegnalante().getId());    
        dto.setMotivo(entity.getMotivo());
        dto.setTimestamp(entity.getTimestamp());
        return dto;
    }
}