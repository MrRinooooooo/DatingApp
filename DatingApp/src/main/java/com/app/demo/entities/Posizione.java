package com.app.demo.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class Posizione {

	private Double latitudine;
	private Double longitudine;
	private String citta;

	public Posizione() {

	}

	public Posizione(String citta, Double latitudine, Double longitudine) {
		this.citta = citta;
		this.latitudine = latitudine;
		this.longitudine = longitudine;
	}

	public Double getLatitudine() {
		return latitudine;
	}

	public void setLatitudine(Double latitudine) {
		this.latitudine = latitudine;
	}

	public Double getLongitudine() {
		return longitudine;
	}

	public void setLongitudine(Double longitudine) {
		this.longitudine = longitudine;
	}

	public String getCitta() {
		return citta;
	}

	public void setCitta(String citta) {
		this.citta = citta;
	}

}
