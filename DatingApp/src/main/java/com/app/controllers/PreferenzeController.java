package com.app.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.PreferenzeDto;
import com.app.entities.Utente;
import com.app.services.PreferenzeService;
import com.app.services.UtenteService;
import com.app.utils.SecurityUtils;

import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/preferenze")
public class PreferenzeController {

	@Autowired
	private PreferenzeService preferenzeService;

	@Autowired
	UtenteService utenteService;
	
	//unico metodo per verificare se l'utente è autenticato 
	//lo chiameremo poi in ogni metodo

	private Utente getCurrentUser() {
		String currentUserEmail = SecurityUtils.getCurrentUserEmail();
		if (currentUserEmail == null) {
			throw new RuntimeException("Utente non autenticato");
		}
		return utenteService.findByEmail(currentUserEmail);
	}

	@PostMapping("/me")
	public ResponseEntity<?> creaPreferenze( @RequestBody PreferenzeDto preferenzeDto) {
		try {
			Utente utente = getCurrentUser();
			return ResponseEntity.ok(preferenzeService.salvaPreferenze(utente.getId(), preferenzeDto));
		} catch (RuntimeException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Errore durante la creazione:" + e.getMessage());
		}
	}

	@GetMapping("/me")
	public ResponseEntity<?> getPreferenze() {
		try {
			Utente utente = getCurrentUser();
			Optional<PreferenzeDto> preferenze = preferenzeService.getPreferenzeByUtenteId(utente.getId());
			return ResponseEntity.ok(preferenze);

		} catch (RuntimeException e) {
			return ResponseEntity.status(400).body(e.getMessage());
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Nessuna corrispondenza trovata" + e.getMessage());
		}

	}

	@PutMapping("/me")
	public ResponseEntity<?> modificaPreferenze(@RequestBody PreferenzeDto preferenzeDto) {
		try {
			String currentUserEmail = SecurityUtils.getCurrentUserEmail();

			if (currentUserEmail == null) {
				return ResponseEntity.badRequest().body("Utente non autenticato");
			}

			Utente utente = utenteService.findByEmail(currentUserEmail);

			PreferenzeDto preferenzeAggiornate = preferenzeService.modificaPreferenze(utente.getId(), preferenzeDto);

			return ResponseEntity.ok(preferenzeAggiornate);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Errore durante la modifica preferenze: " + e.getMessage());
		}
	}

}
