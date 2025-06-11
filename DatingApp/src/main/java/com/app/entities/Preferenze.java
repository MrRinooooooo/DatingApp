package com.app.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "preferenze")

public class Preferenze {

	@Id
	private Long id;
	

	@OneToOne
	@MapsId
	@JoinColumn(name = "utente", nullable = false)
	@JsonManagedReference	//per evitare il loop infinito del json di risposta
	private Utente utente;


	@Column(name = "genere_preferito", columnDefinition = "varchar(255)")
	private String generePreferito;
	
	@Column(name = "eta_minima", columnDefinition = "int(3)")
	private Integer minEta;

	@Column(name = "eta_massima", columnDefinition = "int(3)")

	private Integer maxEta;

	@Column(name = "distanza_massima")
	private Double distanzaMax;
	
	public Preferenze() {
		super();
	}
	
	public Preferenze(Utente utente, String generePreferito, Integer minEta, Integer maxEta, Double distanzaMax) {
		this.utente = utente;
		this.generePreferito = generePreferito;
		this.minEta = minEta;
		this.maxEta = maxEta;
		this.distanzaMax = distanzaMax;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Utente getUtente() {
		return utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	public String getGenerePreferito() {
		return generePreferito;
	}

	public void setGenerePreferito(String generePreferito) {
		this.generePreferito = generePreferito;
	}

	public Integer getMinEta() {
		return minEta;
	}

	public void setMinEta(Integer minEta) {
		this.minEta = minEta;
	}

	public Integer getMaxEta() {
		return maxEta;
	}

	public void setMaxEta(Integer maxEta) {
		this.maxEta = maxEta;
	}

	public Double getDistanzaMax() {
		return distanzaMax;
	}

	public void setDistanzaMax(Double distanzaMax) {
		this.distanzaMax = distanzaMax;
	}


	public String toString() {
		return "Preferenze [utente_id=" + utente + ", genere_preferito" + generePreferito + ", eta_minima=" + minEta + ", eta_massima=" + maxEta
				 + ", distanza_massima" + distanzaMax + "]";
	}	
}
