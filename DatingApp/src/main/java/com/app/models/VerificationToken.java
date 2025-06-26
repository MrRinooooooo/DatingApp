package com.app.models;

import java.time.LocalDateTime;

import com.app.entities.Utente;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    private LocalDateTime expiryDate;
    
    @OneToOne
    private Utente utente;

    public VerificationToken() {}

    public VerificationToken(String token, Utente utente) {
        this.token = token;
        this.utente = utente;
        this.expiryDate = LocalDateTime.now().plusHours(24);
    }

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}
}
