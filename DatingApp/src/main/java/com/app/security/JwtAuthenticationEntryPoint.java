package com.app.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.Serializable;
 
/**
 * Classe che gestisce le richieste non autorizzate.
 * Viene chiamata quando un utente prova ad accedere a una risorsa 
 * protetta senza essere autenticato.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {
 
    /**
     * Metodo chiamato quando un utente non autenticato tenta di accedere 
     * a una risorsa protetta
     */
    @Override
    public void commence(HttpServletRequest request, 
                        HttpServletResponse response,
                        AuthenticationException authException) throws IOException {
 
        // Imposta il codice di stato HTTP 401 (Unauthorized)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
 
        // Imposta il content type della risposta
        response.setContentType("application/json");
 
        // Scrive una risposta JSON con il messaggio di errore
        response.getWriter().write(
            "{\"error\": \"Unauthorized\", " +
            "\"message\": \"Token JWT mancante o non valido\", " +
            "\"status\": 401}"
        );
    }
}