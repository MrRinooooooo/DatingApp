package com.app.dto;

import java.time.LocalDate;

import com.app.entities.Posizione;
import com.app.enums.Genere;

public class ModificaUtenteDTO {

	private String nome;
	private String username;
	private String password;
	private Genere genere;
	private LocalDate dataNascita;
	private String bio;
	private String interessi;
	private String città;
	private String fotoProfilo;
	private Boolean notificheAttive;
	
	public ModificaUtenteDTO() {
		
	}
	
	public ModificaUtenteDTO(String nome, String username, String password, Genere genere, LocalDate dataNascita,
			String bio, String interessi, String città, String fotoProfilo, Boolean notificheAttive) {
		super();
		this.nome = nome;
		this.username = username;
		this.password = password;
		this.genere = genere;
		this.dataNascita = dataNascita;
		this.bio = bio;
		this.interessi = interessi;
		this.città = città;
		this.fotoProfilo = fotoProfilo;
		this.notificheAttive = notificheAttive;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
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
	public String getCittà() {
		return città;
	}
	public void setCittà(String città) {
		this.città = città;
	}
	public String getFotoProfilo() {
		return fotoProfilo;
	}
	public void setFotoProfilo(String fotoProfilo) {
		this.fotoProfilo = fotoProfilo;
	}
	public Boolean getNotificheAttive() {
		return notificheAttive;
	}
	public void setNotificheAttive(Boolean notificheAttive) {
		this.notificheAttive = notificheAttive;
	}
	
}