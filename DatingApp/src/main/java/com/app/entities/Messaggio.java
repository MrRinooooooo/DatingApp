package com.app.entities;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "messaggio")
	
public class Messaggio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long  id;
	
	@ManyToOne
	@JoinColumn(name = "match_id", nullable = false)
	private Match match;

	@ManyToOne
	@JoinColumn(name = "mittente_id", nullable = false)
	private Utente mittente;
	
	@Column(name = "contenuto", columnDefinition = "varchar(255)")
	private String contenuto;
	
	@Column(name = "timestamp", columnDefinition = "datetime")
	private LocalDateTime timestamp;
	
	@Column(name = "stato", columnDefinition = "varchar(255)")
	private String stato;
		

	public Long  getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public  Match getMatch() {
		return match;
	}
	public void setMatch( Match match) {
		this.match = match;
	}
	
	public  Utente getMittente() {
		return mittente;
	}
	public void setMittente( Utente mittente) {
		this.mittente =mittente;
	}
	
	public String getContenuto() {
		return contenuto;
	}
	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}	
	public void setTimestamp(LocalDateTime data) {
		this.timestamp = data;
	}
	
	public String getStato() {
		return stato;
	}
	
	public void setStato(String stato) {
		this.stato = stato;
	}

}
