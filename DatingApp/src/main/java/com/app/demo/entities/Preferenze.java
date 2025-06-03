package com.app.demo.entities;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "utenteId")
	private Utente utente;
	
	@Column(name = "genere_preferito", nullable = false, columnDefinition = "varchar(255)")
	private String generePreferito;
	
	@Column(name = "eta_minima", nullable = false)
	private Integer minEta;

	@Column(name = "eta_massima", nullable = false)
	private Integer maxEta;

	@Column(name = "distanza_massima", nullable = false)
	private Integer distanzaMax;
	
	public Preferenze() {
		
	}
	
	public Preferenze(Utente utente, String generePreferito, Integer minEta, Integer maxEta, Integer distanzaMax) {
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

	public Integer getDistanzaMax() {
		return distanzaMax;
	}

	public void setDistanzaMax(Integer distanzaMax) {
		this.distanzaMax = distanzaMax;
	}


	public String toString() {
		return "Preferenze [utente_id=" + utente + ", genere_preferito" + generePreferito + ", eta_minima=" + minEta + ", eta_massima=" + maxEta
				 + ", distanza_massima" + distanzaMax + "]";
	}	
	
	
}
