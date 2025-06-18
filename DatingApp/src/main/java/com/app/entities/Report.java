package com.app.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "report")
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@ManyToOne
	@JoinColumn(name = "segnalato_id", nullable = false)
	private Utente segnalato;

	@ManyToOne
	@JoinColumn(name = "segnalante_id", nullable = false)
	private Utente segnalante;
		
	@Column(name = "motivo", columnDefinition = "Text")
	private String motivo;	
	
	@Column(name = "timestamp", columnDefinition = "datetime")
	private LocalDateTime timestamp;
		
	public Report() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Report(Utente segnalato, Utente segnalante, String motivo, LocalDateTime timestamp) {
	    this.segnalato = segnalato;
	    this.segnalante = segnalante;
	    this.motivo = motivo;
	    this.timestamp = timestamp;
	}
	
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
