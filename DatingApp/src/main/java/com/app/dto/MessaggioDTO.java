package com.app.dto;

import java.time.LocalDateTime;

public class MessaggioDTO {
    
    private Long id;
    private Long matchId;
    private Long mittenteId;
    private String contenuto;
    private LocalDateTime timestamp;
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