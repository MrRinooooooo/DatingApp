package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.AbbonamentoDto;
import com.app.entities.Abbonamento;
import com.app.entities.Utente;
import com.app.repositories.AbbonamentoRepository;
import com.app.repositories.UtenteRepository;
import com.app.services.AbbonamentoService;
import com.app.services.UtenteService;


@RestController
@RequestMapping("/api/premium")
public class AbbonamentoController {

	@Autowired
    UtenteRepository utenteRepository;
	
	@Autowired
	UtenteService utenteService;
	
	@Autowired
	AbbonamentoRepository abbonamentoRepository;
	
	@Autowired
	AbbonamentoService abbonamentoService;

    AbbonamentoController(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }
	
	// --------------- UPGRADE ACCOUNT A PREMIUM
	@PostMapping("/upgrade")
	public ResponseEntity<?> createSubscription( @RequestBody AbbonamentoDto abbonamentoDto) {
		try {
			Long stripeSubscriptionId = null;
			Utente utente = utenteService.getCurrentUser();
			
			
			if (abbonamentoDto.getMetodoPagamento().toUpperCase() == "STRIPE")
				{
					//IMPLEMENTAZIONE    stripeSubscriptionId = .getStripeSubscriptionId();
				}
			Abbonamento nuovoAbbonamento = new Abbonamento( utente.getId(), abbonamentoDto.getTipoAbbonamento().toUpperCase(), abbonamentoDto.getMetodoPagamento().toUpperCase(), stripeSubscriptionId);
			utente.setTipoAccount("PREMIUM");
			utenteRepository.save(utente);
			return ResponseEntity.ok(abbonamentoRepository.save(nuovoAbbonamento));
			
		} catch (RuntimeException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Errore durante la creazione abbonamento:" + e.getMessage());
		}
	}
	
	// --------------- VERIFICA SE ABBONAMENTO ATTIVO
	@GetMapping("/status")
	public ResponseEntity<?> subscriptionStatus() {
		try {
			Utente utente = utenteService.getCurrentUser();
			
			return ResponseEntity.ok(abbonamentoService.getLastSubscriptionByUserId(utente.getId()));
			
		} catch (RuntimeException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Errore durante la visualizzazione abbonamento:" + e.getMessage());
		}
	}
	
	// --------------- STORICO ABBONAMENTI
	@GetMapping("/subscriptionHistory")
	public ResponseEntity<?> getSubscriptionHistory() {
		try {
			Utente utente = utenteService.getCurrentUser();
			
			return ResponseEntity.ok(abbonamentoService.getSubscriptionHistoryByUserId(utente.getId()));
			
		} catch (RuntimeException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Errore durante la visualizzazione dello storico:" + e.getMessage());
		}
	}
	
	//---------------- per domani 10/06 implementazione modelli da restituire come da documento
}