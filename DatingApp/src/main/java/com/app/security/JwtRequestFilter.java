package com.app.security;

import com.app.services.CustomUserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
 
import java.io.IOException;
 
/**
 * Filtro che intercetta ogni richiesta HTTP per verificare la presenza 
 * e validità del token JWT nell'header Authorization.
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {
 
    @Autowired
    private CustomUserDetailsService userDetailsService;
 
    @Autowired
    private JwtUtil jwtUtil;
 
    /**
     * Metodo principale del filtro che viene eseguito per ogni richiesta HTTP
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, 
                                  HttpServletResponse response, 
                                  FilterChain chain) throws ServletException, IOException {
 
  																					//System.out.println("=== INIZIO FILTRO JWT ===");
  																					//System.out.println("URL richiesta: " + request.getRequestURL()); 	
    	
        // Ottiene l'header Authorization dalla richiesta
        final String requestTokenHeader = request.getHeader("Authorization");
        																			//System.out.println("Sono all'interno del filtro");
 
        String username = null;
        String jwtToken = null;
 
        // Verifica se l'header Authorization è presente e inizia con "Bearer "
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            // Estrae il token rimuovendo il prefisso "Bearer "
            jwtToken = requestTokenHeader.substring(7);
            
            																		//System.out.println("Token estratto: " + jwtToken);
 
            try {
            	
            																		// Prima verifica: proviamo a estrarre tutti i claims per vedere se il token è ben formato
 																					//System.out.println("--- Tentativo estrazione claims ---");
 Claims claims = jwtUtil.extractAllClaims(jwtToken);
 System.out.println("Claims estratti con successo: " + claims);          	
            	
            	
            	
                // Estrae lo username dal token
 																					//System.out.println("--- Tentativo estrazione username ---");
            	username = jwtUtil.extractUsername(jwtToken);
 																					//System.out.println("Username estratto" + username);
 
            } catch (ExpiredJwtException e) {
                System.out.println("ERRORE: JWT Token scaduto - " + e.getMessage());
                System.out.println("Data scadenza: " + e.getClaims().getExpiration());
            } catch (MalformedJwtException e) {
                System.out.println("ERRORE: JWT Token malformato - " + e.getMessage());
            } catch (SignatureException e) {
                System.out.println("ERRORE: Firma JWT non valida - " + e.getMessage());
            } catch (IllegalArgumentException e) {
                System.out.println("ERRORE: Argomento illegale - " + e.getMessage());
            } catch (Exception e) {
                System.out.println("ERRORE generico durante estrazione username: " + e.getClass().getSimpleName() + " - " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            logger.warn("JWT Token non inizia con Bearer String");
        }
 
        // Se abbiamo uno username e non c'è già un'autenticazione nel context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        																			//.out.println("--- Procedura di autenticazione ---");
            
            try {
                // Carica i dettagli dell'utente dal database
																					//System.out.println("1. Caricamento dettagli utente per: " + username);
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
																					//System.out.println("2. Dettagli utente caricati: " + userDetails.getUsername());
                
                // Valida il token
																					//System.out.println("3. Validazione token...");
                if (jwtUtil.validateToken(jwtToken, userDetails)) {
																					//System.out.println("4. Token valido! Creazione autenticazione...");
                    
                    // Crea un oggetto di autenticazione
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                        new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    
                    // Imposta i dettagli della richiesta
                    usernamePasswordAuthenticationToken
                        .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
     
                    // Imposta l'autenticazione nel SecurityContext
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
																					//System.out.println("5. Autenticazione impostata con successo!");
                } else {
																					//System.out.println("4. Token NON valido!");
                }
            } catch (Exception e) {
																					//System.out.println("ERRORE durante l'autenticazione: " + e.getMessage());
                e.printStackTrace();
            }
        } else if (username == null) {
																					//System.out.println("Username è null, autenticazione saltata");
        } else {
																					//System.out.println("Utente già autenticato nel SecurityContext");
        }
 
																					//System.out.println("=== FINE FILTRO JWT - Proseguo con chain.doFilter ===");
        
        // Fa proseguire la richiesta al prossimo filtro/controller
        chain.doFilter(request, response);
    }
}