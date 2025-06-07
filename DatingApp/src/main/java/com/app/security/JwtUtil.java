package com.app.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
 
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
 
/**
 * Utility class per la gestione dei JWT tokens.
 * Contiene tutti i metodi necessari per creare, validare e decodificare i token JWT.
 */
@Component
public class JwtUtil {
 
    // Chiave segreta per firmare i token JWT (dovrebbe essere in application.properties)
    @Value("${jwt.secret:mySecretKey12345678901234567890}")
    private String secret;
 
    // Durata del token in millisecondi (24 ore)
    @Value("${jwt.expiration:86400000}")
    private Long expiration;
 
    /**
     * Genera la chiave segreta per firmare i JWT
     * @return SecretKey oggetto per la firma
     */
    private SecretKey getSigningKey() {
        // Converte la stringa secret in una chiave crittografica sicura
        return Keys.hmacShaKeyFor(secret.getBytes());
    }
 
    /**
     * Estrae lo username dal token JWT
     * @param token il token JWT
     * @return username dell'utente
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }
 
    /**
     * Estrae la data di scadenza dal token
     * @param token il token JWT
     * @return data di scadenza
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
 
    /**
     * Metodo generico per estrarre claim specifici dal token
     * @param token il token JWT
     * @param claimsResolver funzione per estrarre il claim desiderato
     * @return il valore del claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {     // da eliminare?
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
 
    /**
     * Estrae tutti i claims dal token JWT
     * @param token il token JWT
     * @return oggetto Claims contenente tutti i dati del token
     */
    public Claims extractAllClaims(String token) {															//-----------------private - public per prova
        try {
            return Jwts.parserBuilder()  // Crea un parser JWT
                    .setSigningKey(getSigningKey())  // Imposta la chiave per verificare la firma
                    .build()
                    .parseClaimsJws(token)  // Analizza e verifica il token
                    .getBody();  // Restituisce i claims
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token JWT scaduto", e);
        } catch (JwtException e) {
            throw new RuntimeException("Token JWT non valido", e);
        }
    }
 
    /**
     * Verifica se il token è scaduto
     * @param token il token JWT
     * @return true se scaduto, false altrimenti
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }
 
    /**
     * Genera un nuovo token JWT per l'utente
     * @param userDetails i dettagli dell'utente
     * @return il token JWT generato
     */
    public String generateToken(UserDetails userDetails) {            // da passare email non user
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }
 
    /**
     * Genera un token JWT con claims personalizzati
     * @param userId ID dell'utente da includere nel token
     * @param username username dell'utente
     * @param accountType tipo di account (STANDARD, PREMIUM)
     * @return il token JWT generato
     */
    public String generateToken(Long userId, String username, String accountType) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);  // Aggiungiamo l'ID utente ai claims
        claims.put("accountType", accountType);  // Aggiungiamo il tipo di account
        return createToken(claims, username);
    }
    
    /**
     * Crea effettivamente il token JWT
     * @param claims i claims da includere nel token
     * @param subject il soggetto del token (solitamente lo username)
     * @return il token JWT firmato
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder()
                .setClaims(claims)  // Imposta i claims personalizzati
                .setSubject(subject)  // Imposta il subject (username)
                .setIssuedAt(new Date(System.currentTimeMillis()))  // Data di creazione
                .setExpiration(new Date(System.currentTimeMillis() + expiration))  // Data di scadenza
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)  // Firma il token
                .compact();  // Compatta il token in una stringa
    }
 
    /**
     * Valida se il token è valido per l'utente specificato
     * @param token il token JWT
     * @param userDetails i dettagli dell'utente
     * @return true se valido, false altrimenti
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        // Il token è valido se lo username corrisponde e non è scaduto
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
 
    /**
     * Estrae l'ID utente dal token JWT
     * @param token il token JWT
     * @return l'ID dell'utente
     */
    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("userId", Long.class);
    }
 
    /**
     * Estrae il tipo di account dal token JWT
     * @param token il token JWT
     * @return il tipo di account
     */
    public String extractAccountType(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("accountType", String.class);
    }
    
    public String extractEmail(String token) {
    	return extractAllClaims(token).getSubject();
    }
}