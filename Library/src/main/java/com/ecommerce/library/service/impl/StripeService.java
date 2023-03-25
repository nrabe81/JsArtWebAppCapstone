package com.ecommerce.library.service.impl;

import com.ecommerce.library.model.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class StripeService {

//    @Value("${STRIPE_SECRET_KEY}")
    //Stripe Test Secrete Key
    private String secretKey = "sk_test_51MovtkBwzfKoO9qzKKnbKvNnTD9pI7anBpAj5uRxfM7eWCXXnwAtKiKYc1styDu5WgFwCGZShtEZbwHoxcuGiWwm00fN8TrVM9";

    @PostConstruct
    public void init()
    {
        Stripe.apiKey = secretKey;
    }
    public Charge charge(ChargeRequest chargeRequest)
            throws StripeException {
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("amount", chargeRequest.getAmount());
        chargeParams.put("currency", chargeRequest.getCurrency());
        chargeParams.put("description", chargeRequest.getDescription());
        chargeParams.put("source", chargeRequest.getStripeToken());
        return Charge.create(chargeParams);
    }
}
