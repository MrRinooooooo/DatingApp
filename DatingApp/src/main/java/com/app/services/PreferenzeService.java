package com.app.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import com.app.dto.PreferenzeDto;
import com.app.entities.Preferenze;
import com.app.entities.Utente;
import com.app.repositories.PreferenceRepository;
import com.app.repositories.UtenteRepository;

@Service
public class PreferenzeService {

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private PreferenceRepository preferenceRepository;

	// prendiamo tutti gli utenti
	public Optional<PreferenzeDto> getPreferenzeByUtenteId(Long utenteId) {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof User) {
			User springUser = (User) authentication.getPrincipal();

			// Troviamo l'utente nel database e trasformiamo le sue preferenze in
			// PreferenzeDto
			return utenteRepository.findByUsername(springUser.getUsername())
					.flatMap(utente -> Optional.ofNullable(utente.getPreferenze())
							.map(preferenze -> new PreferenzeDto(preferenze.getGenerePreferito(),
									preferenze.getMinEta(), preferenze.getMaxEta(), preferenze.getDistanzaMax())));
		} else {
			throw new RuntimeException("Autenticazione non valida");
		}
	}

	public PreferenzeDto salvaPreferenze(Long utenteId, PreferenzeDto preferenzeDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof User) {
			User springUser = (User) authentication.getPrincipal(); // Usa l'oggetto User di Spring Security

			// Recupera l'utente dal database usando l'email
			Utente utente = utenteRepository.findByUsername(springUser.getUsername())
					.orElseThrow(() -> new RuntimeException("Utente non trovato"));

			Preferenze preferenze = new Preferenze();
			preferenze.setUtente(utente);
			preferenze.setGenerePreferito(preferenzeDto.getGenerePreferito());
			preferenze.setMinEta(preferenzeDto.getEtaMinima());
			preferenze.setMaxEta(preferenzeDto.getEtaMassima());
			preferenze.setDistanzaMax(preferenzeDto.getDistanzaMax());

			preferenceRepository.saveAndFlush(preferenze); // forziamo la creazione immediata dell'oggetto

			return new PreferenzeDto(preferenze.getGenerePreferito(), preferenze.getMinEta(), preferenze.getMaxEta(),
					preferenze.getDistanzaMax());
		} else {
			throw new RuntimeException("Autenticazione non valida");
		}
	}

	public PreferenzeDto modificaPreferenze(Long utenteId, PreferenzeDto preferenzeDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof User) {
			User springUser = (User) authentication.getPrincipal();

			Utente utente = utenteRepository.findByUsername(springUser.getUsername())
					.orElseThrow(() -> new RuntimeException("Utente non trovato"));

			Preferenze modificaPreferenze = new Preferenze();
			modificaPreferenze.setUtente(utente);
			modificaPreferenze.setGenerePreferito(preferenzeDto.getGenerePreferito());
			modificaPreferenze.setMinEta(preferenzeDto.getEtaMinima());
			modificaPreferenze.setMaxEta(preferenzeDto.getEtaMassima());
			modificaPreferenze.setDistanzaMax(preferenzeDto.getDistanzaMax());

			return new PreferenzeDto(modificaPreferenze.getGenerePreferito(), modificaPreferenze.getMinEta(),
					modificaPreferenze.getMaxEta(), modificaPreferenze.getDistanzaMax());
		} else {
			throw new RuntimeException("Autenticazione non valida");
		}

	}
}
