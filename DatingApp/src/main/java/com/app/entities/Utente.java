package com.app.entities;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table (name = "utente")

public class Utente {
	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", nullable = false, columnDefinition = "varchar(255)")
	private String nome;
	
	@Column(name = "email", nullable = false, columnDefinition = "varchar(255)")
	private String email;
	
	@Column(name = "password", nullable = false, columnDefinition = "varchar(60)")
	private String password;
	
	@Column(name = "genere", nullable = false, columnDefinition = "varchar(255)")
	private String genere;
	
	@Column(name = "data_nascita", nullable = false)
	private LocalDate dataNascita;
	
	@Column(name = "bio", nullable = false, columnDefinition = "TEXT")
	private String bio;
	
	@Column(name = "interessi", nullable = false, columnDefinition = "TEXT")
	private String interessi;
	
	@Embedded
	private Posizione posizione;
	
	@Column(name = "foto_profilo", nullable = false, columnDefinition = "varchar(255)")
	private String fotoProfilo;
	
	@Column(name = "tipo_account", nullable = false, columnDefinition = "varchar(255)")
	private String tipoAccount;
	
	@Column(name = "data_registrazione", nullable = false)
	private LocalDate dataRegistrazione;
	
	 @OneToOne(mappedBy = "utente")
	 private Preferenze preferenze;
	 
	 @OneToMany(mappedBy = "utenteSwipe")
	 private List<Swipe> swipeEffettuati;
	 
	 @OneToMany(mappedBy = "utenteTargetSwipe")
	 private List<Swipe> swipeRicevuti;
	 
	 @OneToMany(mappedBy = "utente1")
	 private List<Match> matchUtente1;
	 
	 @OneToMany(mappedBy = "utente2")
	 private List<Match> matchUtente2;
	 
	 @OneToMany(mappedBy = "mittente")
	 private List<Messaggio> mittente;
	 
	 @OneToMany(mappedBy = "utente")
	 private List<Notifica> notificaUtente;
	 
	 @OneToMany(mappedBy = "segnalante")
	 private List<Report> Segnalante;
	 
	 @OneToMany(mappedBy = "segnalato")
	 private List<Report> Segnalato;
	
	public Utente() {
		
	}
	
	//includiamo anche Posizione
	public Utente(String nome, String email, String password, String genere, LocalDate dataNascita, String bio, String interessi, Posizione posizione, String fotoProfilo, String tipoAccount, LocalDate dataRegistrazione) {
		this.nome = nome;
		this.email = email;
		this.password = password;
		this.genere = genere;
		this.dataNascita = dataNascita;
		this.bio = bio;
		this.interessi = interessi;
		this.posizione = posizione;
		this.fotoProfilo = fotoProfilo;
		this.tipoAccount = tipoAccount;
		this.dataRegistrazione = dataRegistrazione;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGenere() {
		return genere;
	}

	public Preferenze getPreferenze() {
		return preferenze;
	}

	public void setPreferenze(Preferenze preferenze) {
		this.preferenze = preferenze;
	}

	public List<Swipe> getSwipeEffettuati() {
		return swipeEffettuati;
	}

	public void setSwipeEffettuati(List<Swipe> swipeEffettuati) {
		this.swipeEffettuati = swipeEffettuati;
	}

	public List<Swipe> getSwipeRicevuti() {
		return swipeRicevuti;
	}

	public void setSwipeRicevuti(List<Swipe> swipeRicevuti) {
		this.swipeRicevuti = swipeRicevuti;
	}

	public List<Match> getMatchUtente1() {
		return matchUtente1;
	}

	public void setMatchUtente1(List<Match> matchUtente1) {
		this.matchUtente1 = matchUtente1;
	}

	public List<Match> getMatchUtente2() {
		return matchUtente2;
	}

	public void setMatchUtente2(List<Match> matchUtente2) {
		this.matchUtente2 = matchUtente2;
	}

	public List<Messaggio> getMittente() {
		return mittente;
	}

	public void setMittente(List<Messaggio> mittente) {
		this.mittente = mittente;
	}

	public List<Notifica> getNotificaUtente() {
		return notificaUtente;
	}

	public void setNotificaUtente(List<Notifica> notificaUtente) {
		this.notificaUtente = notificaUtente;
	}

	public List<Report> getSegnalante() {
		return Segnalante;
	}

	public void setSegnalante(List<Report> segnalante) {
		Segnalante = segnalante;
	}

	public List<Report> getSegnalato() {
		return Segnalato;
	}

	public void setSegnalato(List<Report> segnalato) {
		Segnalato = segnalato;
	}

	public void setGenere(String genere) {
		this.genere = genere;
	}

	public LocalDate getDataNascita() {
		return dataNascita;
	}

	public void setDataNascita(LocalDate dataNascita) {
		this.dataNascita = dataNascita;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public String getInteressi() {
		return interessi;
	}

	public void setInteressi(String interessi) {
		this.interessi = interessi;
	}

	public Posizione getPosizione() {
		return posizione;
	}

	public void setPosizione(Posizione posizione) {
		this.posizione = posizione;
	}

	public String getFotoProfilo() {
		return fotoProfilo;
	}

	public void setFotoProfilo(String fotoProfilo) {
		this.fotoProfilo = fotoProfilo;
	}

	public String getTipoAccount() {
		return tipoAccount;
	}

	public void setTipoAccount(String tipoAccount) {
		this.tipoAccount = tipoAccount;
	}

	public LocalDate getDataRegistrazione() {
		return dataRegistrazione;
	}

	public void setDataRegistrazione(LocalDate dataRegistrazione) {
		this.dataRegistrazione = dataRegistrazione;
	}
	
	@Override
	public String toString() {
	    return "Utente{" +
	            "id=" + id +
	            ", nome='" + nome + '\'' +
	            ", email='" + email + '\'' +
	            ", password='[PROTETTA]'" +
	            ", genere='" + genere + '\'' +
	            ", dataNascita=" + dataNascita +
	            ", bio='" + bio + '\'' +
	            ", interessi='" + interessi + '\'' +
	            ", posizione=" + posizione +
	            ", fotoProfilo='" + fotoProfilo + '\'' +
	            ", tipoAccount='" + tipoAccount + '\'' +
	            ", dataRegistrazione=" + dataRegistrazione +
	            '}';
	}
	
	
}