package com.app.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifica")
public class Notifica {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@Column(name = "utente_id", columnDefinition= "bigint(11)")
	private Long utenteId;
	
	@Column(name = "tipo", columnDefinition="varchar(100)") // nuovo match, nuovo messaggio, super_like ricevuto
	private String tipo;
	
	@Column(name = "contenuto", columnDefinition="varchar(255)")
	private String contenuto;
	
	@Column(name = "timestamp", columnDefinition="datetime")
	private LocalDateTime timestamp;
	
	@Column(name = "letta", columnDefinition="boolean")
	private boolean letta;
	
	public Notifica() {
		// Default constructor
	}
	
	public Notifica(Long utenteId, String tipo, String contenuto) {
		this.utenteId = utenteId;
		this.tipo = tipo;
		this.contenuto = contenuto;
		this.timestamp = LocalDateTime.now();
		this.letta = false;
	}
	
	public Long getUtenteId() {
		return utenteId;
	}

	public void setUtenteId(Long utenteId) {
		this.utenteId = utenteId;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public LocalDateTime getDataTimestamp() {
		return timestamp;
	}

	public void setData(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isLetta() {
		return letta;
	}

	public void setLetta(boolean letta) {
		this.letta = letta;
	}

	@Override
	public String toString() {
		return "Notifica [utenteId=" + utenteId + ", tipo=" + tipo + ", contenuto=" + contenuto + ", data=" + timestamp
				+ ", letta=" + letta + "]";
	}	

}
