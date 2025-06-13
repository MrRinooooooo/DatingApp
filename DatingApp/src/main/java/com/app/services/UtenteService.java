package com.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.dto.RegistrazioneDto;

import com.app.entities.Preferenze;
import com.app.entities.Utente;
import com.app.repositories.MessaggioRepository;
import com.app.repositories.PreferenceRepository;
import com.app.repositories.UtenteRepository;
import com.app.utils.SecurityUtils;

@Service
public class UtenteService {

    private final MessaggioRepository messaggioRepository;

	@Autowired
	private UtenteRepository utenteRepository;
	
	@Autowired 
	private PreferenceRepository preferenceRepository;

    UtenteService(MessaggioRepository messaggioRepository) {
        this.messaggioRepository = messaggioRepository;
    }
	
	@Autowired
	private PasswordEncoder passwordEncoder;
    
	//REGISTRAZIONE
	public Utente createUtente(RegistrazioneDto registrazioneDto) {
			String encodedPassword = this.passwordEncoder.encode(registrazioneDto.getPassword().trim());
			Utente nuovoUtente = new Utente(registrazioneDto.getEmail(), encodedPassword);
			Preferenze preferenze = new Preferenze();
			
			System.out.println("nuovo utente da salvare");
			System.out.println(nuovoUtente);
			
			utenteRepository.save(nuovoUtente);
			
			preferenze.setUtente(utenteRepository.findByUsernameSecure(nuovoUtente.getUsername()));

			preferenze.setId(nuovoUtente.getId());
			System.out.println(preferenze);
			
			preferenceRepository.save(preferenze);
			return nuovoUtente;
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
	public ResponseEntity<?> updateProfile(String email, Utente uModificato) {
		
		try {			
						
			if (email == null)
			    return ResponseEntity.badRequest().body("Utente non autenticato"); // Verifico che la username non sia nulla		
			
			Utente uLoggato = utenteRepository.findByUsername(email)
			    .orElseThrow(() -> new RuntimeException("Utente non trovato")); // Trova l'utente esistente			
			  
			if (uLoggato.getId() != uModificato.getId())
				return ResponseEntity.badRequest().body("Modifica rifiutata: Id utente non modificabile!"); // Verifico che l'id utente non sia stato modificato  
			
			if ( !(uLoggato.getDataRegistrazione().equals(uModificato.getDataRegistrazione())) )
				return ResponseEntity.badRequest().body("Modifica rifiutata: Data di registrazione non modificabile!"); // Verifico che la data di registrazione utente non sia stata modificata			 
			            
			if ( !(uLoggato.getTipoAccount().equals(uModificato.getTipoAccount())) )
				return ResponseEntity.badRequest().body("Modifica rifiutata: Tipo Account non modificabile!"); // Verifico che il tipo di account utente non sia stato modificato			 
			           
			if ( !(uLoggato.getUsername().equals(uModificato.getUsername())) ) 
				if ( !(isEmailValida(uModificato.getUsername())) ) 
					return ResponseEntity.badRequest().body("Modifica username rifiutata: il valore digitato non è valido");
				 else if ( utenteRepository.existsByUsername(uModificato.getUsername()) ) 
					return ResponseEntity.badRequest().body("Modifica username rifiutata: Esite già nel database"); // Verifico che la nuova username non sia già usata nel database e sia valida
						          
			if ( uModificato.getPassword() == null || uModificato.getPassword().length() < 6 )
				return ResponseEntity.badRequest().body("Password non valida, deve essere di almeno 6 caratteri!"); // Verifico che la nuova password contenga almeno 6 caratteri e non sia nulla         	
			
			if( uModificato.getDataNascita() == null)
				return ResponseEntity.badRequest().body("Non puoi lasciare il campo data di nascita vuoto!"); // Verifico che la nuova data di nascita non sia nulla     
			
			// SE MODIFICO USERNAME O PASSWORD IL TOKEN NON E' PIU' VALIDO. DOBBIAMO GENERARNE UNO NUOVO TRAMITE IL LOGIN
			
			// Aggiorna solo i campi che possono essere modificati dall'utente

			uLoggato.setPassword(passwordEncoder.encode(uModificato.getPassword().trim()));
			uLoggato.setUsername(uModificato.getUsername().trim());
			
			if (uModificato.getNome() == null) uModificato.setNome("");
			uLoggato.setNome(uModificato.getNome().trim());

      uLoggato.setDataNascita(uModificato.getDataNascita());	
			
			if (uModificato.getBio() == (null)) uModificato.setBio("");				
				uLoggato.setBio(uModificato.getBio().trim());			
			
			if (uModificato.getGenere() == (null)) uModificato.setGenere("");
			uLoggato.setGenere(uModificato.getGenere().trim());
			
			if (uModificato.getInteressi() == (null)) uModificato.setInteressi("");
			uLoggato.setInteressi(uModificato.getInteressi().trim());
			
			uLoggato.setPosizione(uModificato.getPosizione());
			
			if (uModificato.getFotoProfilo() == (null)) uModificato.setFotoProfilo("");
			uLoggato.setFotoProfilo(uModificato.getFotoProfilo().trim());
			
			utenteRepository.save(uLoggato);
			
			return ResponseEntity.ok(uLoggato); 
    	 } catch (Exception e) {
    		 return ResponseEntity.badRequest().body("Errore nell'aggiornamento del profilo: " + e.getMessage());
    	 }	    
	}	
	
	/**
	 * Metodo per verificare che l'email sia corretta.
	 */	
	public boolean isEmailValida(String email) {
	    if (email == null || email.trim().isEmpty()) {
	        return false;
	    }
	 // Regex: almeno un punto dopo la @
	    return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
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
	    profiloPubblico.setFotoProfilo(utente.getFotoProfilo());
	    
	    // NON includere: email, password, dataRegistrazione, tipoAccount
	    
	    return profiloPubblico;
	}

	// Prende dati utente loggato (se loggato)
	public Utente getCurrentUser() {
		String currentUserEmail = SecurityUtils.getCurrentUserEmail();
		if (currentUserEmail == null) {
			throw new RuntimeException("Utente non autenticato");
		}
		return findByEmail(currentUserEmail);
	}
	
	// Restituisce true se il tipoAccount dell'utente loggato è "PREMIUM"
	public boolean isPremium() {
		Utente utente = getCurrentUser();
		boolean isPremium;
		
		if(utente.getTipoAccount()=="PREMIUM") {
			isPremium=true;
			}else {
				isPremium=false;
			}
		return isPremium;
	}
}
