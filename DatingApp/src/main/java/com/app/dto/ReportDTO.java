package com.app.dto;

import java.time.LocalDateTime;

public class ReportDTO {

    private Long id; // ID report (per admin)
    private Long segnalatoId; // Chi viene segnalato
    private Long segnalanteId; // Chi ha segnalato (per admin)
    private String motivo; // Motivo segnalazione
    private LocalDateTime timestamp; // Data segnalazione (per admin)

    // ========== COSTRUTTORI ==========
    public ReportDTO() {}

    // Per creare segnalazione (utenti)
    public ReportDTO(Long segnalatoId, String motivo) {
        this.segnalatoId = segnalatoId;
        this.motivo = motivo;
    }

    // ========== GETTER E SETTER ==========
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getSegnalatoId() { return segnalatoId; }
    public void setSegnalatoId(Long segnalatoId) { this.segnalatoId = segnalatoId; }

    public Long getSegnalanteId() { return segnalanteId; }
    public void setSegnalanteId(Long segnalanteId) { this.segnalanteId = segnalanteId; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    // ========== DEBUG ==========
    @Override
    public String toString() {
        return "ReportDTO{" +
                "id=" + id +
                ", segnalatoId=" + segnalatoId +
                ", segnalanteId=" + segnalanteId +
                ", motivo='" + motivo + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}