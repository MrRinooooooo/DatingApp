package com.app.dto;

/**
 * DTO per la risposta di login/registrazione
 */
public class LoginResponse {
    private String token;
    private String message;
    private Long userId;
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