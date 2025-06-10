package com.app.dto;

public class AbbonamentoDto {

	private String tipoAbbonamento;
	private String metodoPagamento;
	
	public AbbonamentoDto() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AbbonamentoDto(String tipoAbbonamento, String metodoPagamento) {
		super();
		this.tipoAbbonamento = tipoAbbonamento;
		this.metodoPagamento = metodoPagamento;
	}

	public String getTipoAbbonamento() {
		return tipoAbbonamento;
	}

	public void setTipoAbbonamento(String tipoAbbonamento) {
		this.tipoAbbonamento = tipoAbbonamento;
	}

	public String getMetodoPagamento() {
		return metodoPagamento;
	}

	public void setMetodoPagamento(String metodoPagamento) {
		this.metodoPagamento = metodoPagamento;
	}	
}
