package com.app.entities;
import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "match")
public class Match {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "utente1Id", columnDefinition="bigint")
	private Long utente1Id;
	
	@Column(name = "utente2Id", columnDefinition = "bigint")
	private Long utente2Id;
	
	@Column(name = "timestamp", columnDefinition = "datetime")
	private LocalDateTime timestamp;

	public Long  getId() {
		return id;
	}
	public void setId(Long  id) {
		this.id = id;
	}
	
	public Long getUtente1Id() {
		return utente1Id;
	}
	public void setUtente1Id(Long utente1Id) {
		this.utente1Id = utente1Id;
	}

	public Long getUtente2Id() {
		return utente2Id;
	}
	public void setUtente2Id(Long utente2Id) {
		this.utente2Id = utente2Id;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(LocalDateTime data) {
		this.timestamp = data;
	}
	
}	

