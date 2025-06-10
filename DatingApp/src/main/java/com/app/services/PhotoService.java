package com.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dto.UtenteDiscoverDTO;
import com.app.entities.Utente;
import com.app.repositories.UtenteRepository;

@Service
public class PhotoService {
	
	@Autowired
	UtenteRepository utenteRepository;
	
	public Utente addPhoto(String email, UtenteDiscoverDTO utentediscoverDTO) {
		Utente utente = utenteRepository.findByUsername(email)
				.orElseThrow(() -> new RuntimeException("utente non trovato"));
		
		//impostiamo la nuova foto
		
		utente.setFotoProfilo(utentediscoverDTO.getFotoProfilo());
		
		return utenteRepository.save(utente);
	}
	
	//modifica foto
	public Utente updatePhoto(String email, Utente fotoAggiornata) {
		Utente fotoEsistente = utenteRepository.findByUsername(email)
				.orElseThrow(() -> new RuntimeException("utente non trovato"));
		
		//aggiornamento foto
		if (fotoAggiornata.getFotoProfilo() != null && !fotoAggiornata.getFotoProfilo().trim().isEmpty())  {
		fotoEsistente.setFotoProfilo(fotoAggiornata.getFotoProfilo());
		
		
		}
		return utenteRepository.save(fotoEsistente);
	}
	
	//visualizza foto
	public Utente getPhoto(String email) {
		Utente utente = utenteRepository.findByUsername(email)
				.orElseThrow(() -> new RuntimeException("utente non trovato"));
		
		Utente visualizzaFoto = new Utente();
		visualizzaFoto.getFotoProfilo();
		
		return visualizzaFoto;
	}
	
	//elimina foto 
	
	public Utente deletePhoto(String email) {
		Utente utente = utenteRepository.findByUsername(email)
				.orElseThrow(() -> new RuntimeException("utente non trovato"));
		
		// verifica se la foto è presente
		utente.setFotoProfilo(null);
		
		return utenteRepository.save(utente);
	}

}
