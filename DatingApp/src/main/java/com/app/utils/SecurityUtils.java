package com.app.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
 
/**
 * Utility class per operazioni relative alla sicurezza.
 * Fornisce metodi per ottenere informazioni sull'utente correntemente autenticato.
 */
@Component
public class SecurityUtils {
 
    /**
     * Ottiene l'utente correntemente autenticato dal SecurityContext
     * @return UserDetails dell'utente autenticato o null se non autenticato
     */
    public static UserDetails getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
 
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            return (UserDetails) authentication.getPrincipal();
        }
 
        return null;
    }
 
    /**
     * Ottiene l'email dell'utente correntemente autenticato
     * @return email dell'utente o null se non autenticato
     */
    public static String getCurrentUserEmail() {
        UserDetails userDetails = getCurrentUser();
        return userDetails != null ? userDetails.getUsername() : null;
    }
 
    /**
     * Verifica se l'utente corrente è autenticato
     * @return true se autenticato, false altrimenti
     */
    public static boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && 
               authentication.isAuthenticated() && 
               !(authentication.getPrincipal() instanceof String);
    }
 
    /**
     * Verifica se l'utente corrente ha un ruolo specifico
     * @param role il ruolo da verificare (es. "ROLE_PREMIUM")
     * @return true se l'utente ha il ruolo, false altrimenti
     */
    public static boolean hasRole(String role) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && 
               authentication.getAuthorities().stream()
                   .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(role));
    }
 
    /**
     * Verifica se l'utente corrente ha l'account premium
     * @return true se premium, false altrimenti
     */
    public static boolean isPremiumUser() {
        return hasRole("ROLE_PREMIUM");					// Forse da cancellare "ROLE_"
    }
}