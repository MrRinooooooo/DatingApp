package com.app.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * DTO per la richiesta di login
 */
public class LoginRequest {
 
    @NotBlank(message = "Email è obbligatoria")
    @Email(message = "Formato email non valido")
    
    @Schema(description = "email dell'utente", example = "Mario_verdi@gmail.com")
    private String email;
 
    @NotBlank(message = "Password è obbligatoria")
    @Schema(description = "password dell'utente", example = "123456dsjdsaiboafsiu")
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