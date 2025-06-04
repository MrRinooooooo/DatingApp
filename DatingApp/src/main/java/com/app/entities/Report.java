package com.app.demo.entities;

import java.time.LocalDateTime;
import java.util.Locale.Category;

import jakarta.persistence.*;

@Entity
@Table(name = "report")
public class Report {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "segnalatoId", referencedColumnName = "id")
	private  Utente segnalato;	

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "segnalanteId", referencedColumnName = "id")
	private  Utente segnalante;	
		
	@Column(name = "motivo", columnDefinition = "Text")
	private String motivo;	
	
	@Column(name = "timestamp", columnDefinition = "datetime")
	private LocalDateTime timestamp;
		
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Utente getSegnalato() {
		return segnalato;
	}
	public void setSegnalato(Utente segnalato) {
		this.segnalato = segnalato;
	}
	public Utente getSegnalante() {
		return segnalante;
	}
	public void setSegnalante(Utente segnalante) {
		this.segnalante = segnalante;
	}

	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String causa) {
		this.motivo = causa;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}	
	public void setTimestamp(LocalDateTime data) {
		this.timestamp = data;
	}	
}
