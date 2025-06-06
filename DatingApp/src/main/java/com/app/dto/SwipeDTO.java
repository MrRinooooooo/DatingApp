package com.app.dto;

public class SwipeDTO {
    
    private Long utenteTargetId;  // ID dell'utente su cui fai swipe
    private String tipo;          // "LIKE", "PASS", "SUPER_LIKE"
    
    // ========== COSTRUTTORI ==========
    public SwipeDTO() {}
    
    public SwipeDTO(Long utenteTargetId, String tipo) {
        this.utenteTargetId = utenteTargetId;
        this.tipo = tipo;
    }
    
    // ========== GETTER E SETTER ==========
    public Long getUtenteTargetId() {
        return utenteTargetId;
    }
    
    public void setUtenteTargetId(Long utenteTargetId) {
        this.utenteTargetId = utenteTargetId;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    // ========== VALIDAZIONE ==========
    public boolean isValid() {
        return utenteTargetId != null && 
               tipo != null && 
               (tipo.equals("LIKE") || tipo.equals("PASS") || tipo.equals("SUPER_LIKE"));
    }
    
    @Override
    public String toString() {
        return "SwipeDTO{" +
                "utenteTargetId=" + utenteTargetId +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}