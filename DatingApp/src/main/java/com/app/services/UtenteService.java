package com.app.services;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dto.RegistrazioneDto;
import com.app.entities.Posizione;
import com.app.entities.Utente;
import com.app.repositories.MessaggioRepository;
import com.app.repositories.UtenteRepository;
import com.app.dto.*;

@Service
public class UtenteService {

    private final MessaggioRepository messaggioRepository;

	@Autowired
	private UtenteRepository utenteRepository;

    UtenteService(MessaggioRepository messaggioRepository) {
        this.messaggioRepository = messaggioRepository;
    }
	
	@Autowired
	private PasswordEncoder passwordEncoder;
    
	//REGISTRAZIONE
	public RestRegistrazioneDto aggiungiUtente(RegistrazioneDto registrazioneDto) {
		Optional<Utente> utenteEsistente = utenteRepository.findByEmail(registrazioneDto.getEmail());
		if (utenteEsistente.isPresent()) {
			return new RestRegistrazioneDto(false, "Username gia esistente");
		} 
			String encodedPassword = this.passwordEncoder.encode(registrazioneDto.getPassword().trim());
			
			 utenteRepository.save(new Utente(registrazioneDto.getEmail(), encodedPassword));
			 return new RestRegistrazioneDto(true, "Registrazione effettuata con successo");
			 
	}	
	
}
