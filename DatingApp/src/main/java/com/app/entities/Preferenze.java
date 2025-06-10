package com.app.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "preferenze")

public class Preferenze {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "utente_id", columnDefinition = "bigint(11)")
	private Long utenteId;
	
	@Column(name = "genere_preferito", nullable = false, columnDefinition = "varchar(255)")
	private String generePreferito;
	
	@Column(name = "eta_minima", nullable = false, columnDefinition = "int(3)")
	private Integer minEta;

	@Column(name = "eta_massima", nullable = false, columnDefinition = "int(3)")
	private Integer maxEta;

	@Column(name = "distanza_massima", nullable = false)
	private Double distanzaMax;
	
	@OneToOne(mappedBy = "preferenze")
    private Utente utente;
	
	public Preferenze() {
		super();
	}
	
	public Preferenze(Long utente, String generePreferito, Integer minEta, Integer maxEta, Double distanzaMax) {
		this.utenteId = utente;
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

	public Long getUtenteId() {
		return utenteId;
	}

	public void setUtenteId(Long utenteId) {
		this.utenteId = utenteId;
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
		return "Preferenze [utente_id=" + utenteId + ", genere_preferito" + generePreferito + ", eta_minima=" + minEta + ", eta_massima=" + maxEta
				 + ", distanza_massima" + distanzaMax + "]";
	}	
	
	
}
