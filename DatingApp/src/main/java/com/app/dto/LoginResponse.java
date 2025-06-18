package com.app.dto;
import io.swagger.v3.oas.annotations.media.Schema;
/**
 * DTO per la risposta di login/registrazione
 */
public class LoginResponse {
	
	 @Schema(description = "codice del token dell'utente", example = "asdfasd123eadadsf32165a465fasdfasfda")
    private String token;
	 
	 @Schema(description = "risposta del login ", example = "utente loggato con successo/credenziali errate")
    private String message;
	 
	 @Schema(description = "id dell'utente", example = "1")
    private Long userId;
    
	 @Schema(description = "tipo account dell'utente", example = "STANDARD/PREMIUM")
    private String accountType;
 
    // Costruttori
    public LoginResponse() {}
 
    public LoginResponse(String token, String message, Long userId, String accountType) {
        this.token = token;
        this.message = message;
        this.userId = userId;
        this.accountType = accountType;
    }
 
    // Getter e Setter
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
 
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
 
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
 
    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }
}