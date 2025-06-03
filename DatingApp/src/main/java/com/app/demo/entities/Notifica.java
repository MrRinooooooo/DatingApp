package com.app.demo.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "notifica")
public class Notifica {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "utenteId", referencedColumnName = "id")
	private Long utenteId;
	
	@Column(name = "tipo", columnDefinition="varchar(100)") // nuovo match, nuovo messaggio, super_like ricevuto
	private String tipo;
	
	@Column(name = "contenuto", columnDefinition="varchar(255)")
	private String contenuto;
	
	@Column(name = "timestamp", columnDefinition="datetime")
	private LocalDateTime data;
	
	@Column(name = "letta", columnDefinition="boolean")
	private boolean letta;
	
	public Notifica() {
		// Default constructor
	}
	
	public Notifica(Long utenteId, String tipo, String contenuto, LocalDateTime data, boolean letta) {
		this.utenteId = utenteId;
		this.tipo = tipo;
		this.contenuto = contenuto;
		this.data = data;
		this.letta = letta;
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

	public LocalDateTime getData() {
		return data;
	}

	public void setData(LocalDateTime data) {
		this.data = data;
	}

	public boolean isLetta() {
		return letta;
	}

	public void setLetta(boolean letta) {
		this.letta = letta;
	}

	@Override
	public String toString() {
		return "Notifica [utenteId=" + utenteId + ", tipo=" + tipo + ", contenuto=" + contenuto + ", data=" + data
				+ ", letta=" + letta + "]";
	}
	
	
	
	
	
	

}
