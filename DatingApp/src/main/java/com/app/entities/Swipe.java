package com.app.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "swipe")

public class Swipe {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "utente_id", columnDefinition = "bigint(11)")
	private Long utenteSwipeId;
	
	@Column(name = "utente_target_id", columnDefinition = "bigint(11)")
	private Long utenteTargetSwipeId;
	
	@Column(name = "tipo", nullable = false, columnDefinition = "varchar(255)")
	private String tipo;
	
	@Column(name = "timestamp", nullable = false, columnDefinition = "datetime")
	private LocalDateTime timestamp;
	
	public Swipe() {
		
	}
	
	public Swipe(Long utenteSwipeId, Long utenteTargetSwipeId, String tipo, LocalDateTime timestamp) {
		this.utenteSwipeId = utenteSwipeId;
		this.utenteTargetSwipeId = utenteTargetSwipeId;
		this.tipo = tipo;
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUtenteSwipeId() {
		return utenteSwipeId;
	}

	public void setUtenteSwipeId(Long utenteSwipeId) {
		this.utenteSwipeId = utenteSwipeId;
	}

	public Long getUtenteTargetSwipeId() {
		return utenteTargetSwipeId;
	}

	public void setUtenteTargetSwipeId(Long utenteTargetSwipeId) {
		this.utenteTargetSwipeId = utenteTargetSwipeId;
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
		return "Swipe [utente_id=" + utenteSwipeId + ", utente_target_swipe=" + utenteTargetSwipeId + ", tipo=" + tipo + ", data=" + timestamp
				 + "]";
	}	
}

