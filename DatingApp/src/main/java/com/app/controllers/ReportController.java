package com.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.app.dto.ReportRequestDTO;
import com.app.dto.ReportResponseDTO;
import com.app.services.ReportService;

@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private ReportService reportService;

    // ===== POST: Segnalazione utente =====
    @PostMapping("/report")
    public ResponseEntity<String> segnalaUtente(@RequestBody ReportRequestDTO dto,
                                                @AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(401).body("Utente non autenticato");
        }

        try {
            reportService.creaReport(dto, userDetails.getUsername());
            return ResponseEntity.ok("Report inviato con successo");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(500).body("Errore interno del server");
        }
    }

    // ===== GET: Tutte le segnalazioni ricevute da un utente =====
    @GetMapping("/admin/report")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReportResponseDTO>> getReportsByUtente(@RequestParam Long utenteId) {
        try {
            List<ReportResponseDTO> reports = reportService.getReportsByUtente(utenteId);
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            System.err.println("Errore recupero report: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    // ===== GET: Tutti i report =====
    @GetMapping("/admin/report/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReportResponseDTO>> getAllReports() {
        try {
            List<ReportResponseDTO> reports = reportService.getAllReports();
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            System.err.println("Errore recupero tutti i report: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
}
