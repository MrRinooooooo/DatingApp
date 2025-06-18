package com.app.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

public class MatchDTO {
	
	@Schema(description = "ID del MATCH", example = "1")
    private Long id;
	 
	@Schema(description = "ID dell'utente", example = "1")
    private Long utente1Id;
    
	 @Schema(description = "Nome dell'utente1", example = "Mario")
    private String utente1Nome;    
	 
	 @Schema(description = "email dell'utente1", example = "Mario_verdi@gmail.com")
    private String utente1Email;
    
	 @Schema(description = "ID dell'utente2", example = "2") 
    private Long utente2Id;
    
	 @Schema(description = "Nome dell'utente1", example = "Mario") 
    private String utente2Nome;
    
	 @Schema(description = "email dell'utente2", example = "Valentina_bianchi@gmail.com") 
    private String utente2Email;
    
	 @Schema(description = "data corrente", example = "2025/06/12") 
    private LocalDateTime timestamp;
    
    
    // Costruttori
    public MatchDTO() {}
    
    public MatchDTO(Long id, Long utente1Id, String utente1Nome, String utente1Email,
                   Long utente2Id, String utente2Nome, String utente2Email, LocalDateTime timestamp) {
        this.id = id;
        this.utente1Id = utente1Id;
        this.utente1Nome = utente1Nome;
        this.utente1Email = utente1Email;
        this.utente2Id = utente2Id;
        this.utente2Nome = utente2Nome;
        this.utente2Email = utente2Email;
        this.timestamp = timestamp;
    }
    
    // Getters e Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getUtente1Id() {
        return utente1Id;
    }
    
    public void setUtente1Id(Long utente1Id) {
        this.utente1Id = utente1Id;
    }
    
    public String getUtente1Nome() {
        return utente1Nome;
    }
    
    public void setUtente1Nome(String utente1Nome) {
        this.utente1Nome = utente1Nome;
    }
    
    public String getUtente1Email() {
        return utente1Email;
    }
    
    public void setUtente1Email(String utente1Email) {
        this.utente1Email = utente1Email;
    }
    
    public Long getUtente2Id() {
        return utente2Id;
    }
    
    public void setUtente2Id(Long utente2Id) {
        this.utente2Id = utente2Id;
    }
    
    public String getUtente2Nome() {
        return utente2Nome;
    }
    
    public void setUtente2Nome(String utente2Nome) {
        this.utente2Nome = utente2Nome;
    }
    
    public String getUtente2Email() {
        return utente2Email;
    }
    
    public void setUtente2Email(String utente2Email) {
        this.utente2Email = utente2Email;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}