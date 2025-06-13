package com.app.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.ReportDTO;
import com.app.services.ReportService;

@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @PostMapping("/report")    
    
    /*
    public ResponseEntity<String> segnalaUtente(@RequestBody ReportDTO reportDTO, 
                                               @AuthenticationPrincipal UserDetails userDetails) {
        
        System.out.println("=== REPORT CONTROLLER DEBUG ===");
        System.out.println("UserDetails: " + userDetails);
        System.out.println("Email autenticata: " + (userDetails != null ? userDetails.getUsername() : "NULL"));
        System.out.println("ReportDTO ricevuto: " + reportDTO);
        
        // Verifica autenticazione
        if (userDetails == null) {
            System.err.println("ERRORE: UserDetails è null - utente non autenticato");
            return ResponseEntity.status(401).body("Utente non autenticato");
        }
        
        try {
            // Passa l'email dell'utente autenticato al service
            reportService.creaReport(reportDTO, userDetails.getUsername());
            
            return ResponseEntity.ok("Segnalazione inviata con successo");
            
        } catch (IllegalArgumentException e) {
            // Errori di validazione o business logic
            System.err.println("Errore validazione: " + e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
            
        } catch (Exception e) {
            // Errori generici
            System.err.println("Errore durante la creazione del report: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Errore del server: " + e.getMessage());
        }
    }
*/
    public ResponseEntity<String> segnalaUtente(@RequestBody ReportDTO reportDTO,
            @AuthenticationPrincipal UserDetails userDetails) {

if (userDetails == null) {
return ResponseEntity.status(401).body("Utente non autenticato");
}

try {
reportService.creaReport(reportDTO, userDetails.getUsername());
return ResponseEntity.ok("Report inviato con successo");
} catch (IllegalArgumentException ex) {
return ResponseEntity.badRequest().body(ex.getMessage());  // messaggio specifico
} catch (Exception ex) {
ex.printStackTrace();
return ResponseEntity.status(500).body("Errore interno del server");
}
}    
    
    @GetMapping("/admin/report")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReportDTO>> getReportsByUtente(@RequestParam Long utenteId) {
        try {
            List<ReportDTO> reports = reportService.getReportsByUtente(utenteId);
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            System.err.println("Errore recupero report: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    
    @GetMapping("/admin/report/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReportDTO>> getAllReports() {
        try {
            List<ReportDTO> reports = reportService.getAllReports();
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            System.err.println("Errore recupero tutti i report: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }
    
    
    
}