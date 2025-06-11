package com.app.entities;

import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "report")
public class Report {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@Column(name = "segnalato_id", columnDefinition = "bigint(11)")
	private  Long segnalatoId;

	@Column(name = "segnalante_id", columnDefinition = "bigint(11)")
	private  Long segnalanteId;
		
	@Column(name = "motivo", columnDefinition = "Text")
	private String motivo;	
	
	@Column(name = "timestamp", columnDefinition = "datetime")
	private LocalDateTime timestamp;
		
	public Report() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public Report(Long segnalatoId, Long segnalanteId, String motivo, LocalDateTime timestamp) {
		super();
		this.segnalatoId = segnalatoId;
		this.segnalanteId = segnalanteId;
		this.motivo = motivo;
		this.timestamp = timestamp;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSegnalatoId() {
		return segnalatoId;
	}
	public void setSegnalatoId(Long segnalato) {
		this.segnalatoId = segnalato;
	}
	public Long getSegnalanteId() {
		return segnalanteId;
	}
	public void setSegnalanteId(Long segnalante) {
		this.segnalanteId = segnalante;
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
