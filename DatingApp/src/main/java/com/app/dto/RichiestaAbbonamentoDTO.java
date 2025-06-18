package com.app.dto;
import io.swagger.v3.oas.annotations.media.Schema;
import com.app.enums.TipoAbbonamento;

public class RichiestaAbbonamentoDTO {
	@Schema(description = "tipo abbonamento", example = "STANDARD/PREMIUM")
	private TipoAbbonamento tipoAbbonamento;

    public TipoAbbonamento getTipoAbbonamento() {
        return tipoAbbonamento;
    }

    public void setTipoAbbonamento(TipoAbbonamento tipoAbbonamento) {
        this.tipoAbbonamento = tipoAbbonamento;
    }

}
