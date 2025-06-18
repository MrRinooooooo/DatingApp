package com.app.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public class MessaggioDTO {
	
	@Schema(description = "ID dell'utente", example = "1")
    private Long id;
        
	@Schema(description = "ID del match", example = "1")
    private Long matchId;
    
	@Schema(description = "ID del mittente", example = "1")
    private Long mittenteId;
    
	@Schema(description = "messaggio inviato dall'user", example = " Ciao bambola!!!!!!!")
    private String contenuto;
    
	@Schema(description = "data corrente ", example = " 2025/12/12")
    private LocalDateTime timestamp;
    
	@Schema(description = "stato del messaggio", example = "visualizzato/non visualizzato")
    private String stato;
    
    // ========== COSTRUTTORI ==========
    public MessaggioDTO() {}
    
    public MessaggioDTO(Long matchId, String contenuto) {
        this.matchId = matchId;
        this.contenuto = contenuto;
    }
    
    // ========== GETTER E SETTER ==========
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getMatchId() { return matchId; }
    public void setMatchId(Long matchId) { this.matchId = matchId; }
    
    public Long getMittenteId() { return mittenteId; }
    public void setMittenteId(Long mittenteId) { this.mittenteId = mittenteId; }
    
    public String getContenuto() { return contenuto; }
    public void setContenuto(String contenuto) { this.contenuto = contenuto; }
    
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    
    public String getStato() { return stato; }
    public void setStato(String stato) { this.stato = stato; }
    
    @Override
    public String toString() {
        return "MessaggioDTO{" +
                "id=" + id +
                ", matchId=" + matchId +
                ", mittenteId=" + mittenteId +
                ", contenuto='" + contenuto + '\'' +
                ", timestamp=" + timestamp +
                ", stato='" + stato + '\'' +
                '}';
    }
}