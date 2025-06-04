package com.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO per la richiesta di login
 */
public class LoginRequest {
 
    @NotBlank(message = "Email è obbligatoria")
    @Email(message = "Formato email non valido")
    private String email;
 
    @NotBlank(message = "Password è obbligatoria")
    private String password;
 
    // Costruttori
    public LoginRequest() {}
 
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
 
    // Getter e Setter
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
 
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}