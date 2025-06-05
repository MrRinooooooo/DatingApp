package com.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dto.RegistrazioneDto;
import com.app.entities.Utente;
import com.app.repositories.UtenteRepository;

@Service
public class UtenteService {

	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	//REGISTRAZIONE
	public Utente createUtente(RegistrazioneDto registrazioneDto) {

			String encodedPassword = this.passwordEncoder.encode(registrazioneDto.getPassword().trim());
			Utente utenteRegistrato = utenteRepository.save(new Utente(registrazioneDto.getEmail(), encodedPassword));
			return utenteRegistrato;
	}
	
	//CONTROLLO EMAIL ESISTENTE
	public boolean existsByEmail(String email)
	{
		return utenteRepository.existsByEmail(email);
	}
	
	//RITORNA UTENTE SE TROVATO PER EMAIL
	public Utente findByEmail(String email)
	{
		Utente utente = new Utente();
		if(utenteRepository.existsByEmail(email))
		{
			utente = utenteRepository.findByEmail(email).get();
			return utente;
		}
		return null;
	}
}