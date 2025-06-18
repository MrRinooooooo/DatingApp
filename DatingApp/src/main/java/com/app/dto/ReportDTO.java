package com.app.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public class ReportDTO {
	@Schema(description = "ID del report", example = "13")
    private Long id; // ID report (per admin)
	
	@Schema(description = "ID dellìuser segnalato", example = "12")
    private Long segnalatoId; // Chi viene segnalato
    
	@Schema(description = "ID dell'user segnalato", example = "12")
    private Long segnalanteId; // Chi ha segnalato (per admin)
	
	@Schema(description = "motivo del report", example = "pedofilia")
    private String motivo; // Motivo segnalazione
    
	@Schema(description = "data odierna", example = "2025/06/12")
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