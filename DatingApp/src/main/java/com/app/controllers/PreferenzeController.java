package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.PreferenzeDto;

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

	// Visualizza le preferenze dell'utente loggato
	@GetMapping("/me")
	public ResponseEntity<?> getPreferenze() {		
		String currentUserEmail = SecurityUtils.getCurrentUserEmail();			
		return preferenzeService.getPreferenzeByUtenteId(currentUserEmail);		
	}
	
	// Modifica le preferenze dell'utente loggato
	@PutMapping("/me")
	public ResponseEntity<?> modificaPreferenze(@RequestBody PreferenzeDto preferenzeDto) {		
		String currentUserEmail = SecurityUtils.getCurrentUserEmail();
		return preferenzeService.modificaPreferenze(currentUserEmail, preferenzeDto);		
	}
	
	// Visualizza utenti in base alle mie preferenze
	@GetMapping("/preferenze")
	public ResponseEntity<?> listPreferenze() {		
		String currentUserEmail = SecurityUtils.getCurrentUserEmail();			
		return preferenzeService.getUtentiByPreferenze(currentUserEmail);		
	}
	

}
