package com.app.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
 
import java.util.Arrays;
 
/**
 * Configurazione CORS per permettere richieste da frontend esterni.
 */
@Configuration
public class CorsConfig {
 
    /**
     * Configura CORS per permettere richieste cross-origin
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
 
        // Permette richieste da questi domini (in produzione specifica i domini esatti)
        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
 
        // Metodi HTTP permessi
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
 
        // Header permessi
        configuration.setAllowedHeaders(Arrays.asList("*"));
 
        // Permette l'invio di credenziali (cookies, authorization header)
        configuration.setAllowCredentials(true);
 
        // Header esposti al client
        configuration.setExposedHeaders(Arrays.asList("Authorization"));
 
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
 
        return source;
    }
}