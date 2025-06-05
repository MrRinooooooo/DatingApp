package com.app.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> segnalaUtente(@RequestBody ReportDTO reportDTO) { 
        try {
            reportService.creaReport(reportDTO);
            return ResponseEntity.ok("Segnalazione inviata con successo");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Errore del server");
        }
    } 

    @GetMapping("/admin/report")
    public ResponseEntity<List<ReportDTO>> getReportsByUtente(@RequestParam Long utenteId) {
        try {
            List<ReportDTO> reports = reportService.getReportsByUtente(utenteId);
            return ResponseEntity.ok(reports);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}