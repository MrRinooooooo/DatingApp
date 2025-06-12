package com.app.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table (name = "abbonamento")
public class Abbonamento {

		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
		
		@Column(name = "utente_id", columnDefinition = "bigint(11)")
		private Long utenteId;
		
		@Column(name = "tipo", columnDefinition = "varchar(255)")
		private String tipo;
		
		@Column(name = "data_inizio", nullable = false)
		private LocalDate dataInizio;
		
		@Column(name = "data_fine", nullable = false)
		private LocalDate dataFine;
		
		@Column(name = "attivo", nullable = false)
		private boolean attivo;
		
		@Column(name = "metodo_pagamento", columnDefinition = "varchar(255)")
		private String metodoPagamento;
				
		@Column(name = "stripe_subscription_id", columnDefinition = "varchar(255)")
		private String stripeSubscriptionId;

		public Abbonamento() {
			this.dataInizio = LocalDate.now();
			this.dataFine = dataInizio.plusDays(30);
			this.attivo = true;
		}

	public Abbonamento(Long utenteID, String tipo, String metodoPagamento, String stripeSubscriptionId) {
			super();
			this.utenteId = utenteID;
			this.tipo = tipo;
			this.dataInizio = LocalDate.now();
			this.dataFine = dataInizio.plusDays(30);
			this.attivo = true;
			this.metodoPagamento = metodoPagamento;
			this.stripeSubscriptionId = stripeSubscriptionId;
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

		public void setUtenteId(Long utenteID) {
			this.utenteId = utenteID;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

		public LocalDate getDataInizio() {
			return dataInizio;
		}

		public void setDataInizio(LocalDate dataInizio) {
			this.dataInizio = dataInizio;
		}

		public LocalDate getDataFine() {
			return dataFine;
		}

		public void setDataFine(LocalDate dataFine) {
			this.dataFine = dataFine;
		}

		public boolean isAttivo() {
			return attivo;
		}

		public void setAttivo(boolean attivo) {
			this.attivo = attivo;
		}

		public String getMetodoPagamento() {
			return metodoPagamento;
		}

		public void setMetodoPagamento(String metodoPagamento) {
			this.metodoPagamento = metodoPagamento;
		}

		public String getStripeSubscriptionId() {
			return stripeSubscriptionId;
		}

		public void setStripeSubscriptionId(String stripeSubscriptionId) {
			this.stripeSubscriptionId = stripeSubscriptionId;
		}
		
}
