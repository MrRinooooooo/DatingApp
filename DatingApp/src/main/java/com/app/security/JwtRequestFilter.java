package com.app.security;

import com.app.services.CustomUserDetailsService;

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
 
        // Ottiene l'header Authorization dalla richiesta
        final String requestTokenHeader = request.getHeader("Authorization");
 
        String username = null;
        String jwtToken = null;
 
        // Verifica se l'header Authorization è presente e inizia con "Bearer "
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            // Estrae il token rimuovendo il prefisso "Bearer "
            jwtToken = requestTokenHeader.substring(7);
            try {
                // Estrae lo username dal token
                username = jwtUtil.extractUsername(jwtToken);
            } catch (IllegalArgumentException e) {
                System.out.println("Impossibile ottenere il JWT Token");
            } catch (RuntimeException e) {
                System.out.println("JWT Token è scaduto o non valido");
            }
        } else {
            logger.warn("JWT Token non inizia con Bearer String");
        }
 
        // Se abbiamo uno username e non c'è già un'autenticazione nel context
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
 
            // Carica i dettagli dell'utente dal database
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
 
            // Valida il token
            if (jwtUtil.validateToken(jwtToken, userDetails)) {
 
                // Crea un oggetto di autenticazione
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
                    new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
 
                // Imposta i dettagli della richiesta
                usernamePasswordAuthenticationToken
                    .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
 
                // Imposta l'autenticazione nel SecurityContext
                // Questo indica a Spring Security che l'utente è autenticato
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
 
        // Continua con il prossimo filtro nella catena
        chain.doFilter(request, response);
    }
}