package com.app.configs;

import com.app.security.JwtAuthenticationEntryPoint;
import com.app.security.JwtRequestFilter;
import com.app.services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
 
/**
 * Configurazione principale di Spring Security per l'applicazione.
 * Definisce come vengono gestite autenticazione e autorizzazione.
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // Abilita @PreAuthorize e @PostAuthorize
public class SecurityConfig {
 
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
 
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
 
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
 
    /**
     * Bean per l'encoder delle password.
     * BCrypt è un algoritmo di hashing sicuro per le password.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 
    /**
     * Bean per l'AuthenticationManager.
     * Gestisce il processo di autenticazione.
     */
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
 
    /**
     * Provider di autenticazione che usa il nostro CustomUserDetailsService
     * e l'encoder delle password.
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
 
    /**
     * Configurazione principale della sicurezza.
     * Definisce quale URL sono protetti e come.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // Disabilita CSRF (non necessario per API REST con JWT)
            .csrf().disable()
 
            // Configura le regole di autorizzazione
            .authorizeHttpRequests(authz -> authz
                // Permette l'accesso senza autenticazione a questi endpoint
                .requestMatchers(
                    "/api/auth/**",           // Registrazione e login
                    "/swagger-ui/**",         // Documentazione Swagger
                    "/v3/api-docs/**",        // OpenAPI docs
                    "/swagger-resources/**",   // Risorse Swagger
                    "/webjars/**"             // Dipendenze web
                ).permitAll()
 
                // Endpoint che richiedono ruolo PREMIUM
                .requestMatchers("/api/premium/**").hasRole("PREMIUM")
 
                // Tutti gli altri endpoint richiedono autenticazione
                .anyRequest().authenticated()
            )
 
            // Configura la gestione delle eccezioni
            .exceptionHandling(ex -> ex
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
            )
 
            // Configura la gestione delle sessioni
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Non usa sessioni
            );
 
        // Aggiunge il filtro JWT prima del filtro di autenticazione standard
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
 
        return http.build();
    }
}