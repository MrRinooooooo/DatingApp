package com.app.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.PreferenzeDto;
import com.app.services.PreferenzeService;
import com.app.services.UtenteService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/preferenze")
public class PreferenzeController {

	@Autowired
	private PreferenzeService preferenzeService;
	
	
	@PostMapping("/me")
	public ResponseEntity<?> creaPreferenze(@PathVariable Long utenteId, @RequestBody PreferenzeDto preferenzeDto) {
		try {
			return ResponseEntity.ok(preferenzeService.salvaPreferenze(utenteId, preferenzeDto));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Errore durante la creazione" + e.getMessage());
		}
	}

	@GetMapping("/me")
	public ResponseEntity<?> getPreferenze(@PathVariable Long utenteId) {
		try {
			Optional<PreferenzeDto> preferenze = preferenzeService.getPreferenzeByUtenteId(utenteId);

			return ResponseEntity.ok(preferenze);

		} catch (Exception e) {
			return ResponseEntity.badRequest().body("Nessuna corrispondenza trovata" + e.getMessage());
		}

	}

}
