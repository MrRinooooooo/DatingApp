package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AbbonamentoDto;
import com.app.entities.Abbonamento;
import com.app.entities.Utente;
import com.app.repositories.AbbonamentoRepository;
import com.app.services.UtenteService;



@RestController
@RequestMapping("/api/premium")
public class AbbonamentoController {
	
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	AbbonamentoRepository abbonamentoRepository;
	
	@PostMapping("/upgrade")
	public ResponseEntity<?> createSubscription( @RequestBody AbbonamentoDto abbonamentoDto) {
		try {
			Long stripeSubscriptionId = null;
			Utente utente = utenteService.getCurrentUser();
			
			
			if (abbonamentoDto.getMetodoPagamento() == "STRIPE")
				{
					//IMPLEMENTAZIONE    stripeSubscriptionId = .getStripeSubscriptionId();
				}
			Abbonamento nuovoAbbonamento = new Abbonamento( utente.getId(), abbonamentoDto.getTipoAbbonamento(), abbonamentoDto.getMetodoPagamento(), stripeSubscriptionId);
			return ResponseEntity.ok(abbonamentoRepository.save(nuovoAbbonamento));
			
		} catch (RuntimeException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Errore durante la creazione abbonamento:" + e.getMessage());
		}
	}
	
	
	
	
	
}