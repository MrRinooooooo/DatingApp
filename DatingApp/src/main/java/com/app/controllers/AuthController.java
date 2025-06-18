package com.app.controllers;

import com.app.exceptions.EmailAlreadyUsedException;
import com.app.exceptions.WrongPasswordException;

import com.app.dto.LoginRequest;
import com.app.dto.LoginResponse;
import com.app.dto.RegistrazioneDto;
import com.app.entities.Utente;
import com.app.exceptions.UserNotFoundException;
import com.app.security.JwtUtil;
import com.app.services.UtenteService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

/**
 * Controller per gestire autenticazione e registrazione degli utenti.
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*") // Permette richieste da qualsiasi origine (per sviluppo)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UtenteService utenteService;

    /**
     * Endpoint per la registrazione di un nuovo utente.
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrazioneDto registrazioneDto) {
        try {
        	if (utenteService.existsByEmail(registrazioneDto.getEmail())) {
        	    throw new EmailAlreadyUsedException("Email già in uso");
        	}


            Utente nuovoUtente = utenteService.createUtente(registrazioneDto);

            return ResponseEntity.ok(new LoginResponse(
                "Disponibile dopo il login",
                "Registrazione completata con successo",
                nuovoUtente.getId(),
                nuovoUtente.getTipoAccount()
            ));

        } catch (jakarta.validation.ConstraintViolationException e) {
            return ResponseEntity.unprocessableEntity()
                    .body(new LoginResponse(null, "Dati non validi", null, null));
            } 
    }

    /**
     * Endpoint per il login degli utenti.
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Utente utente = utenteService.findByEmail(loginRequest.getEmail());

            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getEmail(),
                    loginRequest.getPassword()
                )
            );

            String token = jwtUtil.generateToken(
                utente.getId(),
                utente.getUsername(),
                utente.getTipoAccount()
            );

            return ResponseEntity.ok(new LoginResponse(
                token,
                "Login effettuato con successo",
                utente.getId(),
                utente.getTipoAccount()
            ));

        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new LoginResponse(null, e.getMessage(), null, null));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginResponse(null, "Password errata!", null, null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new LoginResponse(null, "Errore interno durante il login", null, null));
        }

    }


    /**
     * Endpoint per validare un token JWT.
     * GET /api/auth/validate
     */
    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            if (token != null && token.startsWith("Bearer ")) {
                String jwtToken = token.substring(7);
                String username = jwtUtil.extractUsername(jwtToken);
                Long userId = jwtUtil.extractUserId(jwtToken);
                String accountType = jwtUtil.extractAccountType(jwtToken);

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (userDetails != null && jwtUtil.validateToken(jwtToken, userDetails)) {
                    return ResponseEntity.ok(new LoginResponse(
                        jwtToken,
                        "Token valido",
                        userId,
                        accountType
                    ));
                }
            }

            return ResponseEntity.badRequest()
                .body(new LoginResponse(null, "Token non valido", null, null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                .body(new LoginResponse(null, "Errore nella validazione del token", null, null));
        }
    }
}
