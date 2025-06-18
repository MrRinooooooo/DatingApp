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
import com.app.exceptions.PreferencesNotFoundException;
import com.app.exceptions.UserNotFoundException;
import com.app.repositories.PreferenceRepository;
import com.app.repositories.UtenteRepository;

@Service
public class PreferenzeService {

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private PreferenceRepository preferenceRepository;

	// prendiamo tutti gli utenti
    public PreferenzeDto getPreferenzeByUtenteId(Long utenteId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User springUser = (User) authentication.getPrincipal();

            // Troviamo l'utente nel database e trasformiamo le sue preferenze in
            // PreferenzeDto

            Preferenze preferenze = preferenceRepository.findById(utenteId)
            	    .orElseThrow(() -> new PreferencesNotFoundException("Preferenze non trovate per utente ID: " + utenteId));

            PreferenzeDto preferenzeDto = new PreferenzeDto(preferenze.getGenerePreferito(), preferenze.getMinEta(), preferenze.getMaxEta(), preferenze.getDistanzaMax());
            return preferenzeDto;

        } else {
            throw new RuntimeException("Autenticazione non valida");
        }
    }

	public PreferenzeDto modificaPreferenze(Long utenteId, PreferenzeDto preferenzeDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.getPrincipal() instanceof User) {
			User springUser = (User) authentication.getPrincipal();

			Utente utente = utenteRepository.findByUsername(springUser.getUsername())
				    .orElseThrow(() -> new UserNotFoundException("Utente non trovato con email: " + springUser.getUsername()));

			

			Preferenze modificaPreferenze = preferenceRepository.findByUtenteId(utente.getId())
				    .orElseThrow(() -> new PreferencesNotFoundException("Preferenze non trovate per utente ID: " + utente.getId()));

			modificaPreferenze.setGenerePreferito(preferenzeDto.getGenerePreferito());
			modificaPreferenze.setMinEta(preferenzeDto.getEtaMinima());
			modificaPreferenze.setMaxEta(preferenzeDto.getEtaMassima());
			modificaPreferenze.setDistanzaMax(preferenzeDto.getDistanzaMax());
			
			preferenceRepository.save(modificaPreferenze);

			return new PreferenzeDto(modificaPreferenze.getGenerePreferito(), modificaPreferenze.getMinEta(),
					modificaPreferenze.getMaxEta(), modificaPreferenze.getDistanzaMax());
		} else {
			throw new RuntimeException("Autenticazione non valida");
		}

	}
}
