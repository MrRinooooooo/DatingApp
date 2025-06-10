package com.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.entities.Abbonamento;
import com.app.repositories.AbbonamentoRepository;

@Service
public class AbbonamentoService {
	
	@Autowired
	AbbonamentoRepository abbonamentoRepository;
	
	//GET LISTA COMPLETA ABBONAMENTI DI utente_id
	public List<Abbonamento> getSubscriptionHistoryByUserId(Long utente_id) {
		return abbonamentoRepository.findByUtenteIdOrderByDataFine(utente_id);
	}
	
	//GET ULTIMO ABBONAMENTO DI utente_id
	public Optional<Abbonamento> getLastSubscriptionByUserId(Long utente_id)
	{
		return abbonamentoRepository.findFirstByUtenteIdOrderByDataFineDesc(utente_id);
	}
}
