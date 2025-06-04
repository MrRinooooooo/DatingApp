package com.app.demo.entities;
import java.time.LocalDateTime;

import jakarta.persistence.*;

	@Entity
@Table(name = "messaggio")
	
public class Messaggio {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long  id;
	
	@Column(name = "matchId", columnDefinition="bigint")
	private Long matchId;
	
	@Column(name = "mittenteId", columnDefinition = "bigint")
	private Long mittenteId;
	
	@Column(name = "contenuto", columnDefinition = "varchar(255)")
	private String contenuto;
	
	@Column(name = "timestamp", columnDefinition = "datetime")
	private LocalDateTime timestamp;
	
	@Column(name = "stato", columnDefinition = "varchar(255)")
	private String stato;
		

	public Long  getId() {
		return id;
	}
	public void setId(Long  id) {
		this.id = id;
	}
	
	public  Long getMatchId() {
		return matchId;
	}
	public void setMatchId( Long matchId) {
		this.matchId = matchId;
	}
	
	public  Long getMittenteId() {
		return mittenteId;
	}
	public void setMittenteId( Long mittenteId) {
		this.mittenteId =mittenteId;
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
