package com.app.entities;

import jakarta.persistence.Embeddable;

@Embeddable
public class Posizione {

	private Double latitudine;
	private Double longitudine;
	private String città;

	public Posizione() {

	}

	public Posizione(String citta, Double latitudine, Double longitudine) {
		this.città = citta;
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
		return città;
	}

	public void setCitta(String citta) {
		this.città = citta;
	}

}
