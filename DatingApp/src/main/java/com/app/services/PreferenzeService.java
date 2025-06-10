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
		return preferenceRepository.findByUtenteId(utenteId)
				.map(preferenze -> new PreferenzeDto(preferenze.getGenerePreferito(), preferenze.getMinEta(),
						preferenze.getMaxEta(), preferenze.getDistanzaMax()));
	}

	public List<Utente> filtraUtenti(Long utenteId) {
		Optional<Preferenze> preferenze = preferenceRepository.findByUtenteId(utenteId);
		if (preferenze.isEmpty()) {
			return Collections.emptyList();
		}

		Preferenze p = preferenze.get();
		return utenteRepository.findByPreferenze(p.getGenerePreferito(), p.getMinEta(), p.getMaxEta(),
				p.getDistanzaMax());
	}
	
	public Preferenze salvaPreferenze(Long utenteId, PreferenzeDto preferenzeDto) {
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

	       return preferenceRepository.saveAndFlush(preferenze);	//forziamo la creazione immediata dell'oggetto
	    } else {
	        throw new RuntimeException("Autenticazione non valida");
	    }
	}
	
	public PreferenzeDto modificaPreferenze(Long utenteId, PreferenzeDto preferenzeDto) {
		Preferenze modificaPreferenze = preferenceRepository.findById(utenteId)
				.orElseThrow(() -> new RuntimeException("Utente non trovato con id" + utenteId));
				modificaPreferenze.setGenerePreferito(preferenzeDto.getGenerePreferito());
				modificaPreferenze.setMinEta(preferenzeDto.getEtaMinima());
				modificaPreferenze.setMaxEta(preferenzeDto.getEtaMassima());
				modificaPreferenze.setDistanzaMax(preferenzeDto.getDistanzaMax());
				
				Preferenze nuovaPreferenza = preferenceRepository.save(modificaPreferenze);
				
				//conversione
				
				return new PreferenzeDto(
						nuovaPreferenza.getGenerePreferito(),
						nuovaPreferenza.getMinEta(),
						nuovaPreferenza.getMaxEta(),
						nuovaPreferenza.getDistanzaMax()
						);
	}

}
