package com.app.controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.dto.RichiestaAbbonamentoDTO;
import com.app.enums.TipoAbbonamento;
import com.app.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
@RestController
@RequestMapping("/api/stripe")

@Tag(name = " StripeController", description = "(Gestione pagamento dell'abbonamento)")
public class StripeController {

    @Autowired
    private StripeService stripeService;

    @PostMapping("/createCheckoutSession")
    public ResponseEntity<?> createCheckoutSession(@RequestBody RichiestaAbbonamentoDTO abbonamentoRichiesto ) {
    	    		
    	TipoAbbonamento tipoAbbonamento = abbonamentoRichiesto.getTipoAbbonamento();
        String priceId = "";

        System.out.println("--- RICHIEDO DI FARE UPGRADE ABBONAMENTO ---");
        System.out.println("Abbonamento richiesto: " + tipoAbbonamento);
        
        priceId = switch (tipoAbbonamento) {
	        case GOLD -> "price_XXXGOLD";
	        case PLATINUM -> "price_XXXPLATINUM";
	        case PREMIUM -> "price_1RY4sNP0z2XEGqAd6zLfQjCr";
        };        

        try {            
            Session session = stripeService.createCheckoutSession(
                "http://localhost:8080/success",
                "http://localhost:8080/cancel",
                "Abbonamento Premium",
                priceId
            );            
            return ResponseEntity.ok(Map.of("Id Stripe Check-out session", session.getId()));

        } catch (StripeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}