package com.app.services;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import com.app.entities.Notifica;
import com.app.entities.Utente;
import com.app.repositories.NotificaRepository;
import com.app.repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FirebaseService {

    @Autowired
    private NotificaRepository notificaRepository;
    
    @Autowired
    private UtenteRepository utenteRepository;
    
    @Autowired
    private UtenteService utenteService;

    // Invia notifica per nuovo match
    public void inviaNotificaMatch(Long utenteId, String nomeAltroUtente) {
        String titolo = "🎉 Nuovo Match!";
        String messaggio = "Hai un match con " + nomeAltroUtente + "! Inizia a chattare ora.";
        
        inviaNotifica(utenteId, "NUOVO_MATCH", titolo, messaggio);
    }
    
    // Invia notifica per nuovo messaggio
    public void inviaNotificaMessaggio(Long utenteId, String nomeMittente, String anteprimaMessaggio) {
        String titolo = "💬 Nuovo messaggio da " + nomeMittente;
        String messaggio = anteprimaMessaggio.length() > 50 ? 
            anteprimaMessaggio.substring(0, 50) + "..." : anteprimaMessaggio;
        
        inviaNotifica(utenteId, "NUOVO_MESSAGGIO", titolo, messaggio);
    }
    
    // Invia notifica per super like ricevuto
    public void inviaNotificaSuperLike(Long utenteId, String nomeMittente) {
        String titolo = "⭐ Super Like ricevuto!";
        String messaggio = nomeMittente + " ti ha dato un Super Like!";
        
        inviaNotifica(utenteId, "SUPER_LIKE_RICEVUTO", titolo, messaggio);
    }
    
    // Metodo principale per inviare notifiche
    private void inviaNotifica(Long utenteId, String tipo, String titolo, String contenuto) {
        try {
            // Verifica se l'utente ha notifiche attive
            if (!utenteService.hasNotificationsEnabled(utenteId)) {
                System.out.println("Notifiche disabilitate per utente: " + utenteId);
                return;
            }
            
            // Ottieni device token
            Utente utente = utenteRepository.findById(utenteId).orElse(null);
            if (utente == null || utente.getDeviceToken() == null) {
                System.out.println("Device token non trovato per utente: " + utenteId);
                // Salva solo nel database
                salvaNotificaDB(utenteId, tipo, contenuto);
                return;
            }
            
            // Crea notifica Firebase
            Notification notification = Notification.builder()
                .setTitle(titolo)
                .setBody(contenuto)
                .build();
            
            Message message = Message.builder()
                .setToken(utente.getDeviceToken())
                .setNotification(notification)
                .putData("tipo", tipo)
                .putData("utenteId", utenteId.toString())
                .build();
            
            // Invia notifica push
            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Notifica inviata con successo: " + response);
            
            // Salva nel database
            salvaNotificaDB(utenteId, tipo, contenuto);
            
        } catch (Exception e) {
            System.err.println("Errore invio notifica per utente " + utenteId + ": " + e.getMessage());
            
            // In caso di errore, salva comunque nel database
            salvaNotificaDB(utenteId, tipo, contenuto);
        }
    }
    
    // Salva notifica nel database
    private void salvaNotificaDB(Long utenteId, String tipo, String contenuto) {
        try {
            Notifica notifica = new Notifica(utenteId, tipo, contenuto);
            notificaRepository.save(notifica);
            System.out.println("Notifica salvata nel database per utente: " + utenteId);
        } catch (Exception e) {
            System.err.println("Errore salvataggio notifica DB: " + e.getMessage());
        }
    }
}