package com.app.services;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.app.dto.PreferenzeDto;
import com.app.entities.Preferenze;
import com.app.entities.Utente;
import com.app.repositories.PreferenceRepository;
import com.app.repositories.UtenteRepository;

import jakarta.validation.Validator;


@Service
public class PreferenzeService {

	@Autowired
	private UtenteRepository utenteRepository;

	@Autowired
	private PreferenceRepository preferenceRepository;
	
	@Autowired Validator validator;

	// Metodo per visualizzare le preferenze dell'utente
    public ResponseEntity<?> getPreferenzeByUtenteId(String username) {		
		
    	// Recupera l'utente autenticato
		Utente utente = utenteRepository.findByUsername(username)
				.orElseThrow(() -> new RuntimeException("Utente non trovato"));					
		          
		// Cerca le preferenze esistenti
        Optional<Preferenze> preferenzeOpt = preferenceRepository.findByUtenteId(utente.getId());           
        
        Preferenze preferenze;       
        
        if (preferenzeOpt.isEmpty()) {  
        	// Crea nuove preferenze vuote se non esistono
        	preferenze = new Preferenze(utente);
        	preferenceRepository.save(preferenze);
        } else {
        	// Visualizza le preferenze esistenti
        	preferenze = preferenzeOpt.get();
        }
        
        return ResponseEntity.ok(preferenze);
    }

	// Metodo per modificare le preferenze dell'utente
    public ResponseEntity<?> modificaPreferenze(String username, PreferenzeDto nuovePreferenze) {
    	
    	// Recupera l'utente autenticato
		Utente utente = utenteRepository.findByUsername(username)		
				.orElseThrow(() -> new RuntimeException("Utente non trovato"));		
		
		if (nuovePreferenze.getMinEta() != null && nuovePreferenze.getMinEta() < 18) 
		    return ResponseEntity.badRequest().body("Età Minima: Inserisci un valore maggiore di 17 anni");		
		if (nuovePreferenze.getMinEta() != null && nuovePreferenze.getMinEta() > 100) 
			return ResponseEntity.badRequest().body("Età Minima: Inserisci un valore minore di 100 anni");
		if (nuovePreferenze.getMaxEta() != null && nuovePreferenze.getMaxEta() < 18) 
			return ResponseEntity.badRequest().body("Età Massima: Inserisci un valore maggiore di 18 anni");
		if (nuovePreferenze.getMaxEta() != null && nuovePreferenze.getMaxEta() > 100) 
			return ResponseEntity.badRequest().body("Età Massima: Inserisci un valore minore di 100 anni");
		if (nuovePreferenze.getMinEta() != null && nuovePreferenze.getMaxEta() != null && nuovePreferenze.getMinEta() > nuovePreferenze.getMaxEta()) 
			return ResponseEntity.badRequest().body("L'Età Minima non puàò essere maggire dell'età massima. Inserisci dei valori corretti");
		if (nuovePreferenze.getDistanzaMax() < 0) 
			return ResponseEntity.badRequest().body("Distanza Massima: Inserisci un valore maggiore di zero!");		
		
		// Cerca le preferenze esistenti
		Optional<Preferenze> preferenzeOpt = preferenceRepository.findByUtenteId(utente.getId());
		
		Preferenze preferenze;			
		
        if (preferenzeOpt.isEmpty()) { 
        	// Crea nuove preferenze se non esistono
        	preferenze = new Preferenze(
        			utente, 
        			nuovePreferenze.getGenerePreferito(),
        			nuovePreferenze.getMinEta(), 
        			nuovePreferenze.getMaxEta(), 
        			nuovePreferenze.getDistanzaMax());	        	
        } else {
        	// Aggiorna le preferenze esistenti
        	preferenze = preferenzeOpt.get();
        	preferenze.setGenerePreferito(nuovePreferenze.getGenerePreferito());
        	preferenze.setMinEta(nuovePreferenze.getMinEta());
        	preferenze.setMaxEta(nuovePreferenze.getMaxEta());
        	preferenze.setDistanzaMax(nuovePreferenze.getDistanzaMax());
        }
        
        // Salva le preferenze (nuove o aggiornate)
    	preferenceRepository.save(preferenze);
    	return ResponseEntity.ok(preferenze);
    }
    
    // Metodo per visualizzare gli utenti in base alle preferenze
    public ResponseEntity<?> getUtentiByPreferenze(String username) {
    	
    	// Recupero l'utente autenticato e le sue preferenze
        Utente utente = utenteRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));
        Optional<Preferenze> preferenzeOpt = preferenceRepository.findByUtenteId(utente.getId());
        
        // Carico tutti gli utenti tranne quello autenticato
        List<Utente> utenti = utenteRepository.findByIdNot(utente.getId());
        
        // Se le preferenze sono state create 
        if (preferenzeOpt.isPresent()) {
        	// Carico le preferenze
        	Preferenze preferenze = preferenzeOpt.get();
        	// Prendo come data di riferimento il primo dell'anno corrente
            LocalDate dataRiferimento = LocalDate.of(LocalDate.now().getYear(), 1, 1);
            // Filtro gli utenti in base alle preferenze
            utenti = utenti.stream()
        		 .filter(u -> preferenze.getGenerePreferito() == null ||
                     u.getGenere().equals(preferenze.getGenerePreferito()))
        		 // Calcolo la data di nascita min in base alla preferenza sull'età max
        		 .filter(u -> preferenze.getMinEta() == null || 
                     !u.getDataNascita().isAfter(dataRiferimento.minusYears(preferenze.getMinEta())))
        		 // Calcolo la data di nascita max in base alla preferenza sull'età min
        		 .filter(u -> preferenze.getMaxEta() == null ||
                     !u.getDataNascita().isBefore(dataRiferimento.minusYears(preferenze.getMaxEta())))
        		 .filter(u -> preferenze.getDistanzaMax() == null ||
        	        calcolaDistanza(utente, u) <= preferenze.getDistanzaMax())
        		 .sorted(Comparator
        			        .comparing((Utente u) -> !"PREMIUM".equalsIgnoreCase(u.getTipoAccount())) // Oridinato in base al tipo Account 
        			        .thenComparingDouble(u -> calcolaDistanza(utente, u))) // Oridnato in base alla distanza
        		 .toList();
        }
        return ResponseEntity.ok(utenti);
    }
    
    // Metodo per calcolare la distanza in base alla posizione gsp (latitudine e longitudine)
    private double calcolaDistanza(Utente u1, Utente u2) {
        double R = 6371.0; // Earth radius in km
        double lat1 = Math.toRadians(u1.getPosizione().getLatitudine());
        double lon1 = Math.toRadians(u1.getPosizione().getLatitudine());
        double lat2 = Math.toRadians(u2.getPosizione().getLatitudine());
        double lon2 = Math.toRadians(u2.getPosizione().getLatitudine());
        double dlat = lat2 - lat1;
        double dlon = lon2 - lon1;
        double a = Math.sin(dlat / 2) * Math.sin(dlat / 2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.sin(dlon / 2) * Math.sin(dlon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distance in km
    }
	
}
