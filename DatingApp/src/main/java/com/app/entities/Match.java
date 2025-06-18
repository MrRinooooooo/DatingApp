package com.app.entities;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "utente1_id", nullable = false)
	private Utente utente1;

	@ManyToOne
	@JoinColumn(name = "utente2_id", nullable = false)
	private Utente utente2;

	@Column(name = "timestamp", columnDefinition = "datetime")
	private LocalDateTime timestamp;

	// ========== COSTRUTTORI ==========
	public Match() {
		// Costruttore vuoto per JPA
	}

	public Match(Utente utente1, Utente utente2) {
		this.utente1 = utente1;
		this.utente2 = utente2;
		this.timestamp = LocalDateTime.now();
	}

	// ========== GETTER E SETTER ==========
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Utente getUtente1() {
		return utente1;
	}

	public void setUtente1(Utente utente1) {
		this.utente1 = utente1;
	}

	public Utente getUtente2() {
		return utente2;
	}

	public void setUtente2(Utente utente2) {
		this.utente2 = utente2;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
}