package com.app.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * DTO per la richiesta di registrazione
 */
public class RegisterRequest {
 
    @NotBlank(message = "Nome è obbligatorio")
    @Size(min = 2, max = 50, message = "Nome deve essere tra 2 e 50 caratteri")
    private String nome;
 
    @NotBlank(message = "Email è obbligatoria")
    @Email(message = "Formato email non valido")
    private String email;
 
    @NotBlank(message = "Password è obbligatoria")
    @Size(min = 6, message = "Password deve essere di almeno 6 caratteri")
    private String password;
 
    @NotBlank(message = "Genere è obbligatorio")
    @Pattern(regexp = "MASCHIO|FEMMINA|ALTRO", message = "Genere deve essere MASCHIO, FEMMINA o ALTRO")
    private String genere;
 
    @NotNull(message = "Data di nascita è obbligatoria")
    @Past(message = "Data di nascita deve essere nel passato")
    private LocalDate dataNascita;
 
    private String bio;
    private String interessi;
    private String posizione;
 
    // Costruttori
    public RegisterRequest() {}
 
    // Getter e Setter
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
 
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
 
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
 
    public String getGenere() { return genere; }
    public void setGenere(String genere) { this.genere = genere; }
 
    public LocalDate getDataNascita() { return dataNascita; }
    public void setDataNascita(LocalDate dataNascita) { this.dataNascita = dataNascita; }
 
    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
 
    public String getInteressi() { return interessi; }
    public void setInteressi(String interessi) { this.interessi = interessi; }
 
    public String getPosizione() { return posizione; }
    public void setPosizione(String posizione) { this.posizione = posizione; }
}