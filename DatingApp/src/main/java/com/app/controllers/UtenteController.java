package com.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.RegistrazioneDto;
import com.app.dto.RestRegistrazioneDto;
import com.app.entities.Utente;
import com.app.services.UtenteService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")

public class UtenteController {

	@Autowired
	private UtenteService utenteService;
	
	@PostMapping("/register")
	public RestRegistrazioneDto registraUtente(@RequestBody Utente utente) {
	
		System.out.println("Registrazione utente" + utente.toString() + " avvenuta con successo");
		return utenteService.aggiungiUtente(new RegistrazioneDto(utente.getEmail(), utente.getPassword()));
	}

}
