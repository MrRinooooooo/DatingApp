package com.app.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.PreferenzeDto;
import com.app.entities.Utente;
import com.app.repositories.UtenteRepository;
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
	
	@Autowired
	UtenteRepository utenteRepository;

	@GetMapping("/me")
	public ResponseEntity<?> getPreferenze() {
		try {
			Utente utente = utenteService.getCurrentUser();
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
