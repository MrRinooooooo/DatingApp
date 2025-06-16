package com.app.entities;

import java.time.LocalDate;
import java.util.List;

import com.app.enums.Genere;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
<<<<<<< e7qOGVJ7_filtri_preferenze
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
=======
import jakarta.persistence.FetchType;
>>>>>>> main
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table (name = "utente")

public class Utente {	

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome", columnDefinition = "varchar(255)")
	private String nome;
	
	@NotBlank
    @Email(message = "Formato email non valido")
	@Column(name = "username", nullable = false, unique = true, columnDefinition = "varchar(255)")
	private String username;
	
	@NotBlank
	@Column(name = "password", nullable = false, columnDefinition = "varchar(60)")
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "genere")
	private Genere genere;
	
	@Column(name = "data_nascita")
	private LocalDate dataNascita;
	
	@Column(name = "bio", columnDefinition = "TEXT")
	private String bio;
	
	@Column(name = "interessi", columnDefinition = "TEXT")
	private String interessi;
	
	@Embedded
	private Posizione posizione;
	
	@Column(name = "foto_profilo", columnDefinition = "varchar(255)")
	private String fotoProfilo;
	
	@Column(name = "tipo_account", nullable = false, columnDefinition = "varchar(255)")
	private String tipoAccount;
	
	@Column(name = "data_registrazione", nullable = false)
	private LocalDate dataRegistrazione;
	
	@Column(name = "notifiche_attive", columnDefinition = "bit(1)")
	private Boolean notificheAttive;
	
	@Column(name = "device_token", columnDefinition = "varchar(255)")
	private String deviceToken;

	@Column(name = "primo_accesso", nullable = false)
	private boolean primoAccesso;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "preferenze_id", referencedColumnName = "id")
    private Preferenze preferenze;
	
	@OneToMany(mappedBy = "utente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Abbonamento> abbonamenti;
	
	public Utente() {
		
	}

	public Utente(String email, String password) {
		this.username = email;
		this.password = password;
		this.tipoAccount = "STANDARD";
<<<<<<< e7qOGVJ7_filtri_preferenze
		this.dataRegistrazione = LocalDate.now();		
		this.nome="";
		this.genere= null;
		this.dataNascita = null;		
		this.bio="";
		this.interessi="";
		this.posizione = null;
		this.fotoProfilo="";
	}
	
	//includiamo anche Posizione
	public Utente(String nome, String email, String password, Genere genere, LocalDate dataNascita, String bio, String interessi, Posizione posizione, String fotoProfilo, String tipoAccount, LocalDate dataRegistrazione) {
=======
		this.dataRegistrazione = LocalDate.now();
		this.notificheAttive = true;
		this.deviceToken = null;
		this.primoAccesso = true;
	}
	
	//includiamo anche Posizione
	public Utente(String nome, String email, String password, String genere, LocalDate dataNascita, String bio, String interessi, Posizione posizione, String fotoProfilo, String tipoAccount, LocalDate dataRegistrazione, boolean primoAccesso) {
>>>>>>> main
		this.nome = nome;
		this.username = email;
		this.password = password;
		this.genere = genere;
		this.dataNascita = dataNascita;
		this.bio = bio;
		this.interessi = interessi;
		this.posizione = posizione;
		this.fotoProfilo = fotoProfilo;
		this.tipoAccount = tipoAccount;
		this.dataRegistrazione = dataRegistrazione;
		this.primoAccesso = primoAccesso;
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

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getUsername() {
		return username;
	}

	public void setEmail(String email) {
		this.username = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Genere getGenere() {
		return genere;
	}

	public void setGenere(Genere genere) {
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

	public Boolean getNotificheAttive() {
		return notificheAttive;
	}
	
	public void setNotificheAttive(Boolean notificheAttive) {
		this.notificheAttive = notificheAttive;
	}
	
	public String getDeviceToken() {
		return deviceToken;
	}
	
	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}

	public boolean isPrimoAccesso() {
		return primoAccesso;
	}

	public void setPrimoAccesso(boolean primoAccesso) {
		this.primoAccesso = primoAccesso;
	}
	
	public List<Abbonamento> getAbbonamenti() {
		return abbonamenti;
	}

	public void setAbbonamenti(List<Abbonamento> abbonamenti) {
		this.abbonamenti = abbonamenti;
	}

	@Override
	public String toString() {
	    return "Utente{" +
	            "id=" + id +
	            ", nome='" + nome + '\'' +
	            ", email='" + username + '\'' +
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