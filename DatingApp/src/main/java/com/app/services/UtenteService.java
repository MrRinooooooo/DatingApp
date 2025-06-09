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
import com.app.utils.SecurityUtils;
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
	public Utente createUtente(RegistrazioneDto registrazioneDto) {
			String encodedPassword = this.passwordEncoder.encode(registrazioneDto.getPassword().trim());
			Utente nuovoUtente = new Utente(registrazioneDto.getEmail(), encodedPassword);			
			return utenteRepository.save(nuovoUtente);
	}
	
	//CONTROLLO EMAIL ESISTENTE
	public boolean existsByEmail(String email)
	{
		return utenteRepository.existsByUsername(email);
	}
	
	//RITORNA UTENTE SE TROVATO PER EMAIL
	public Utente findByEmail(String email)
	{
		Utente utente = new Utente();
		if(utenteRepository.existsByUsername(email))
		{
			utente = utenteRepository.findByUsername(email).get();
			return utente;
		}
		return null;
	}
	
	//UPDATE UTENTE	
	public Utente updateProfile(String email, Utente datiAggiornati) {
	    // Trova l'utente esistente
	    Utente utenteEsistente = utenteRepository.findByUsername(email)
	        .orElseThrow(() -> new RuntimeException("Utente non trovato"));
	    
	    // Aggiorna solo i campi che possono essere modificati dall'utente
	    if (datiAggiornati.getNome() != null && !datiAggiornati.getNome().trim().isEmpty()) {
	        utenteEsistente.setNome(datiAggiornati.getNome().trim());
	    }
	    
	    if (datiAggiornati.getBio() != null) {
	        utenteEsistente.setBio(datiAggiornati.getBio().trim());
	    }
	    
	    if (datiAggiornati.getGenere() != null) {
	        utenteEsistente.setGenere(datiAggiornati.getGenere().trim());
	    }
	    
	    if (datiAggiornati.getInteressi() != null) {
	        utenteEsistente.setInteressi(datiAggiornati.getInteressi().trim());
	    }
	    
	    if (datiAggiornati.getPosizione() != null) {
	       utenteEsistente.setPosizione(datiAggiornati.getPosizione());
	    }
	    
	    // NON permettere la modifica di: email, password, id, dataRegistrazione, tipoAccount
	    
	    return utenteRepository.save(utenteEsistente);
	}

	/**
	 * Ottiene il profilo pubblico di un utente (senza informazioni sensibili)
	 * @param userId ID dell'utente
	 * @return profilo pubblico dell'utente
	 */
	public Utente getPublicProfile(Long userId) {
	    Utente utente = utenteRepository.findById(userId)
	        .orElseThrow(() -> new RuntimeException("Utente non trovato"));
	    
	    // Crea una copia dell'utente con solo le informazioni pubbliche
	    Utente profiloPubblico = new Utente();
	    profiloPubblico.setId(utente.getId());
	    profiloPubblico.setNome(utente.getNome());
	    profiloPubblico.setBio(utente.getBio());
	    profiloPubblico.setInteressi(utente.getInteressi());
	    profiloPubblico.setPosizione(utente.getPosizione());
	    profiloPubblico.setGenere(utente.getGenere());
	    profiloPubblico.setDataNascita(utente.getDataNascita());
	    
	    // NON includere: email, password, dataRegistrazione, tipoAccount
	    
	    return profiloPubblico;
	}
	
	public Utente getCurrentUser() {
		String currentUserEmail = SecurityUtils.getCurrentUserEmail();
		if (currentUserEmail == null) {
			throw new RuntimeException("Utente non autenticato");
		}
		return findByEmail(currentUserEmail);
	}
	
}
