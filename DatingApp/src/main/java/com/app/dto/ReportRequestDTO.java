package com.app.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = false)
public class ReportRequestDTO {

    private Long segnalatoId;
    private String motivo;

    public ReportRequestDTO() {}

    public ReportRequestDTO(Long segnalatoId, String motivo) {
        this.segnalatoId = segnalatoId;
        this.motivo = motivo;
    }

    public Long getSegnalatoId() {
        return segnalatoId;
    }

    public void setSegnalatoId(Long segnalatoId) {
        this.segnalatoId = segnalatoId;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    @Override
    public String toString() {
        return "ReportRequestDTO{" +
                "segnalatoId=" + segnalatoId +
                ", motivo='" + motivo + '\'' +
                '}';
    }
}
