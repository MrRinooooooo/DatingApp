package com.app.entities;

import com.app.enums.Genere;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

@Entity
@Table(name = "preferenze")

public class Preferenze {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "utente", nullable = false)
	@JsonManagedReference	//per evitare il loop infinito del json di risposta
	private Utente utente;

	//@Column(name = "genere_preferito", columnDefinition = "varchar(255)")
	//private String generePreferito;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "generePreferito")
    private Genere generePreferito;
	
	@Min(18)
    @Max(100)
	@Column(name = "eta_minima", columnDefinition = "int(3)")
	private Integer minEta;

	@Min(18)
    @Max(100)
	@Column(name = "eta_massima", columnDefinition = "int(3)")
	private Integer maxEta;

	@Column(name = "distanza_massima")
	private Double distanzaMax;
	
	public Preferenze() {
		super();
	}
	
	public Preferenze(Utente utente) {
		this.utente = utente;
		this.generePreferito = null;
		this.minEta = null;
		this.maxEta = null;
		this.distanzaMax =null;
	}
	
	public Preferenze(Utente utente, Genere generePreferito, Integer minEta, Integer maxEta, Double distanzaMax) {
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

	public Genere getGenerePreferito() {
		return generePreferito;
	}

	public void setGenerePreferito(Genere generePreferito) {
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
