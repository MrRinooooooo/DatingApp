package com.app.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import com.app.enums.Genere;

public class PreferenzeDto {
	@Schema(description = "preferenza di genere", example = "MASCHIO/FEMMINA")	
	private Genere generePreferito;
	
	@Schema(description = "preferenza età minima", example = "18")	
	private Integer minEta;
	
	@Schema(description = "preferenza etàmassima", example = "75")	
	private Integer maxEta;
	
	@Schema(description = "distanza massima", example = "25.2")	
	private Double distanzaMax;
	
	public PreferenzeDto() {
		
	}
	
	public PreferenzeDto(Genere generePreferito, Integer minEta, Integer maxEta, Double distanzaMax) {
		this.generePreferito = generePreferito;
		this.minEta = minEta;
		this.maxEta = maxEta;
		this.distanzaMax = distanzaMax;
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
	
}
