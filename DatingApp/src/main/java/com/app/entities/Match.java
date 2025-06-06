package com.app.entities;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class Match {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "utente1Id", referencedColumnName = "id")
	private Utente utente1Id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "utente2Id", referencedColumnName = "id")
	private Utente utente2Id;

	@Column(name = "timestamp", columnDefinition = "datetime")
	private LocalDateTime timestamp;

	// ========== COSTRUTTORI ==========
	public Match() {
		// Costruttore vuoto per JPA
	}

	public Match(Utente utente1, Utente utente2) {
		this.utente1Id = utente1;
		this.utente2Id = utente2;
		this.timestamp = LocalDateTime.now();
	}

	// ========== GETTER E SETTER ==========
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Utente getUtente1Id() {
		return utente1Id;
	}

	public void setUtente1Id(Utente utente1Id) {
		this.utente1Id = utente1Id;
	}

	public Utente getUtente2Id() {
		return utente2Id;
	}

	public void setUtente2Id(Utente utente2Id) {
		this.utente2Id = utente2Id;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	// ========== UTILITY ==========
	@Override
	public String toString() {
		return "Match{" +
				"id=" + id +
				", utente1=" + (utente1Id != null ? utente1Id.getId() : null) +
				", utente2=" + (utente2Id != null ? utente2Id.getId() : null) +
				", timestamp=" + timestamp +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Match match = (Match) o;
		return id != null && id.equals(match.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}