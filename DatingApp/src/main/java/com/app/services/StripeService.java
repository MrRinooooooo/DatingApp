package com.app.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;

@Service
public class StripeService {
	
	/** Crea una sessione di checkout per il pagamento
	 *  successUrl: l’URL a cui Stripe redirige l’utente dopo il pagamento completato.
     *  cancelUrl: URL a cui viene rediretto se annulla il pagamento.
     *  productName: nome del prodotto o abbonamento (es. “Premium”).
     *  amount: importo da pagare in centesimi (es. 499 = €4,99).
     */

    public Session createCheckoutSession(String successUrl, String cancelUrl, String productName, String priceId) throws StripeException {

    	System.out.println("--- CREO UNA SESSIONE DI CHECK-OUT PER IL PAGAMENTO---" );
    	System.out.println("Valori ricevuti: "
    			+ "successUrl: "  + successUrl
    			+ "cancelUrl: " + cancelUrl
    			+ "productName: " + productName
    			+ "amount: " + priceId);
    	
        // Creo una sessione e inserisco una lista di prodotti
    	/*    SessionCreateParams.LineItem.builder()
                .setPriceData(
                    SessionCreateParams.LineItem.PriceData.builder()
                        .setCurrency("eur")
                        .setUnitAmount(amount) // Inserisco il valore della transazione in centesimi (es. 499 = €4.99)
                        .setProductData(
                            SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                .setName(productName) // Inserisco il nome del prodotto
                                .build()
                        )
                        .build()
                )
                .setQuantity(1L) // Imposto la quantità del prodotto
                .build()
        );
    	
    	System.out.println(lineItems);*/

    	SessionCreateParams params = SessionCreateParams.builder()
    		/* Imposto il tipo di pagamento
    		 *  SUBSCRIPTION per i pagamenti ricorrenti (mensili o annuali)
    		 *  PAYMENT per i pagamenti una tantum                            */
            .setMode(SessionCreateParams.Mode.SUBSCRIPTION) 
            .setSuccessUrl(successUrl) 	// Inserisco URL di successo            
            .setCancelUrl(cancelUrl) 	// Inserisco URL di cancellazione
            .addLineItem(
                    SessionCreateParams.LineItem.builder()
                        .setPrice(priceId)
                        .setQuantity(1L)
                        .build()
                )
                .build();
    	
    	System.out.println(params.getMode());
    	System.out.println(params.getSuccessUrl());
    	System.out.println(params.getCancelUrl());
    	System.out.println(params.getLineItems());

        return Session.create(params); // Creo la sessione di pagamento con Stripe contenente Id del pagamento e link di pagamento
    }
}
