package com.app.dto;

public class PreferenzeDto {

	private String generePreferito;
	private int etaMinima;
	private int etaMassima;
	private Double distanzaMax;
	
	public PreferenzeDto() {
		
	}
	
	public PreferenzeDto(String generePreferito, int etaMinima, int etaMassima, double distanzaMax) {
		this.generePreferito = generePreferito;
		this.etaMinima = etaMinima;
		this.etaMassima = etaMassima;
		this.distanzaMax = distanzaMax;
	}

	public String getGenerePreferito() {
		return generePreferito;
	}

	public void setGenerePreferito(String generePreferito) {
		this.generePreferito = generePreferito;
	}

	public int getEtaMinima() {
		return etaMinima;
	}

	public void setEtaMinima(int etaMinima) {
		this.etaMinima = etaMinima;
	}

	public int getEtaMassima() {
		return etaMassima;
	}

	public void setEtaMassima(int etaMassima) {
		this.etaMassima = etaMassima;
	}

	public Double getDistanzaMax() {
		return distanzaMax;
	}

	public void setDistanzaMax(Double distanzaMax) {
		this.distanzaMax = distanzaMax;
	}
	
}
