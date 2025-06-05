package com.app.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
		Utente utente = utenteRepository.findById(utenteId)
				.orElseThrow(() -> new RuntimeException("Utente non trovato"));
		
		Preferenze preferenze = preferenceRepository.findByUtenteId(utenteId)
				.orElse(new Preferenze());
		
		preferenze.setUtente(utente);
		preferenze.setGenerePreferito(preferenzeDto.getGenerePreferito());
		preferenze.setMinEta(preferenzeDto.getEtaMinima());
		preferenze.setMaxEta(preferenzeDto.getEtaMassima());
		preferenze.setDistanzaMax(preferenzeDto.getDistanzaMax());
		
		return preferenceRepository.save(preferenze);
		
	}

}
