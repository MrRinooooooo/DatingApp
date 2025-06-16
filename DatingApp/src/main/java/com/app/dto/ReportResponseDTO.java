package com.app.dto;

import java.time.LocalDateTime;

/**
 * DTO usato per restituire una segnalazione (es. in lista o dettaglio).
 * Può essere usato nelle risposte visibili solo ad admin o moderatori.
 */
public class ReportResponseDTO {

    private Long id;               // ID della segnalazione
    private Long segnalanteId;     // ID utente che ha segnalato
    private Long segnalatoId;      // ID utente segnalato
    private String motivo;         // Motivo della segnalazione
    private LocalDateTime timestamp; // Data/ora della segnalazione

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSegnalanteId() {
        return segnalanteId;
    }

    public void setSegnalanteId(Long segnalanteId) {
        this.segnalanteId = segnalanteId;
    }

    public Long getSegnalatoId() {
        return segnalatoId;
    }

    public void setSegnalatoId(Long segnalatoId) {
        this.segnalatoId = segnalatoId;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
