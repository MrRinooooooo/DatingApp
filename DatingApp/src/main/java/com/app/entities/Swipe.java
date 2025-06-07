package com.app.entities;

import java.time.LocalDateTime;

//prova

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "swipe")

public class Swipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "utente_id")
	private Utente utenteSwipe;
	
	@ManyToOne
	@JoinColumn(name = "utente_target_id")
	private Utente utenteTargetSwipe;
	
	@Column(name = "tipo", nullable = false, columnDefinition = "varchar(255)")
	private String tipo;
	
	@Column(name = "timestamp", nullable = false, columnDefinition = "datetime")
	private LocalDateTime timestamp;
	
	public Swipe() {
		
	}
	
	public Swipe(Long id, Utente utenteSwipe, Utente utenteTargetSwipe, String tipo, LocalDateTime timestamp) {
		this.id = id;
		this.utenteSwipe = utenteSwipe;
		this.utenteTargetSwipe = utenteTargetSwipe;
		this.tipo = tipo;
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Utente getUtenteSwipe() {
		return utenteSwipe;
	}

	public void setUtenteSwipe(Utente utenteSwipe) {
		this.utenteSwipe = utenteSwipe;
	}

	public Utente getUtenteTargetSwipe() {
		return utenteTargetSwipe;
	}

	public void setUtenteTargetSwipe(Utente utenteTargetSwipe) {
		this.utenteTargetSwipe = utenteTargetSwipe;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public String toString() {
		return "Swipe [utente_id=" + utenteSwipe + ", utente_target_swipe=" + utenteTargetSwipe + ", tipo=" + tipo + ", data=" + timestamp
				 + "]";
	}	
}

