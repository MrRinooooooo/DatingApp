package com.app.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendConfirmationEmail(String to, String token) {
        String subject = "Conferma registrazione - DatingApp";
        String confirmationUrl = "http://localhost:8080/api/auth/confirm?token=" + token;
        String body = "Clicca sul link per confermare l'account: " + confirmationUrl;

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        
        mailSender.send(message);
    }
    
    public void sendPremiumSubscriptionReminder(String to, String name) {
        String subject = "Abbonamento PREMIUM in scadenza";
        String body = "Ciao " + name + "!\nIl tuo abbonamento è in scadenza, ricordati di rinnovare per non perdere l'accesso alle funzionalità PREMIUM! ;)";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        
        mailSender.send(message);
    }
    
}
