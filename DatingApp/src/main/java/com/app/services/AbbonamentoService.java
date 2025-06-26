package com.app.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entities.Abbonamento;
import com.app.entities.Utente;
import com.app.repositories.AbbonamentoRepository;
import com.app.repositories.UtenteRepository;

import jakarta.transaction.Transactional;

@Service
public class AbbonamentoService {
	
	@Autowired
	AbbonamentoRepository abbonamentoRepository;
	
	@Autowired
	UtenteRepository utenteRepository;
	
	@Autowired
	EmailService emailService;
	
	//GET LISTA COMPLETA ABBONAMENTI DI utente_id
	public List<Abbonamento> getSubscriptionHistoryByUserId(Utente utente) {
		return abbonamentoRepository.findByUtenteIdOrderByDataFine(utente);
	}
	
	//GET ULTIMO ABBONAMENTO DI utente_id
	public Optional<Abbonamento> getLastSubscriptionByUserId(Utente utente)
	{
		return abbonamentoRepository.findFirstByUtenteOrderByDataFineDesc(utente);
	}
	
	//CONTROLLA SE ABBONAMENTI PREMIUM SCADUTI CON @SCHEDULED A MEZZANOTTE E LI SETTA A STANDARD
	 @Transactional
	    public void controllaScadenzeAbbonamenti() {
		 
		 LocalDate oggi = LocalDate.now();
		 LocalDate ultimaSettimana = oggi.plusDays(7);
		 List<Abbonamento> daDisattivare = new ArrayList<>();
		 
		 //TROVA TUTTI GLI UTENTI CON tipoAccount "PREMIUM"
	        List<Utente> utentiPremium = utenteRepository.findByTipoAccount("PREMIUM");
		 //CICLIAMO DENTRO LA LISTA E PRENDIAMO L'ULTIMO ABBONAMENTO DI OGNI UTENTE 
		 for (Utente utente : utentiPremium) {
			 Optional<Abbonamento> optionalUltimo = abbonamentoRepository.findFirstByUtenteOrderByDataFineDesc(utente);
		 //CONTROLLO SE PRESENTE
			 if (optionalUltimo.isPresent()) {
				 Abbonamento ultimo = optionalUltimo.get();
		//SE PRESENTE E SCADUTO SETTA TIPO ACCOUNT A STANDARD
			     if (ultimo.getDataFine().isBefore(oggi)) {
			    	 ultimo.setAttivo(false);
			         utente.setTipoAccount("STANDARD");
			         daDisattivare.add(ultimo);
	
			         System.out.println("Downgradato utente ID: " + utente.getId());
			     	}
		//CONTROLLO SE MANCA UNA SETTIMANA PER INVIO REMINDER
			     if (ultimo.getDataFine().isEqual(ultimaSettimana)) {
			    	 emailService.sendPremiumSubscriptionReminder(utente.getUsername(), utente.getNome());
			     }
			 }
			     
		 }

		 utenteRepository.saveAll(utentiPremium);
		 abbonamentoRepository.saveAll(daDisattivare);
	}
}
