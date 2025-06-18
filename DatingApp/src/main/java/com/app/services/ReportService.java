package com.app.services;
import com.app.exceptions.UserNotFoundException;
import com.app.dto.ReportRequestDTO;
import com.app.dto.ReportResponseDTO;
import com.app.entities.Report;
import com.app.entities.Utente;
import com.app.repositories.ReportRepository;
import com.app.repositories.UtenteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    // ========== CREA REPORT ==========
    public void creaReport(ReportRequestDTO dto, String emailSegnalante) {

        System.out.println("=== DEBUG CREA REPORT ===");
        System.out.println("DTO: " + dto);
        System.out.println("Email utente loggato: " + emailSegnalante);

        validate(dto);

      
            Utente segnalante = utenteRepository.findByUsername(emailSegnalante)
            		.orElseThrow(() -> new UserNotFoundException("Utente con email " + emailSegnalante + " non trovato"));


            Utente segnalato = utenteRepository.findById(dto.getSegnalatoId())
            	    .orElseThrow(() -> new UserNotFoundException("Utente segnalato con ID " + dto.getSegnalatoId() + " non trovato"));


            if (segnalante.getId().equals(segnalato.getId())) {
                throw new IllegalArgumentException("Non puoi segnalare te stesso.");
            }

            boolean giàSegnalato = reportRepository.existsBySegnalanteIdAndSegnalatoId(
                    segnalante.getId(), segnalato.getId());

            if (giàSegnalato) {
                throw new IllegalArgumentException("Hai già segnalato questo utente.");
            }

            Report report = new Report();
            report.setSegnalanteId(segnalante.getId());
            report.setSegnalatoId(segnalato.getId());
            report.setMotivo(dto.getMotivo().trim());
            report.setTimestamp(LocalDateTime.now());

            reportRepository.save(report);

     
    }

    // ========== GET TUTTI I REPORT ==========
    public List<ReportResponseDTO> getAllReports() {
        try {
            List<Report> reports = reportRepository.findAll();
            return reports.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException("Errore nel recupero dei report", e);
        }
    }

    // ========== GET REPORTS PER UTENTE ==========
    public List<ReportResponseDTO> getReportsByUtente(Long utenteId) {
        try {
        	
        	boolean exists = utenteRepository.existsById(utenteId);
        	if (!exists) {
        	    throw new UserNotFoundException("Utente con ID " + utenteId + " non trovato");
        	}

        	
            List<Report> reports = reportRepository.findBySegnalatoId(utenteId);
            return reports.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw new RuntimeException("Errore nel recupero dei report utente", e);
        }
    }

    // ========== VALIDAZIONE INPUT ==========
    private void validate(ReportRequestDTO dto) {
        if (dto.getSegnalatoId() == null) {
            throw new IllegalArgumentException("ID utente da segnalare obbligatorio");
        }
        if (dto.getMotivo() == null || dto.getMotivo().trim().isEmpty()) {
            throw new IllegalArgumentException("Motivo obbligatorio");
        }
        if (dto.getMotivo().length() > 1000) {
            throw new IllegalArgumentException("Motivo troppo lungo (max 1000 caratteri)");
        }
    }

    // ========== CONVERSIONE ==========
    private ReportResponseDTO convertToResponseDTO(Report report) {
        ReportResponseDTO dto = new ReportResponseDTO();
        dto.setId(report.getId());
        dto.setSegnalanteId(report.getSegnalanteId());
        dto.setSegnalatoId(report.getSegnalatoId());
        dto.setMotivo(report.getMotivo());
        dto.setTimestamp(report.getTimestamp());
        return dto;
    }
}
