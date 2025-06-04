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
}	

