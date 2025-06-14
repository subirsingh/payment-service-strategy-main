package com.ug.paymentservicestrategy.service.impl;

import com.ug.paymentservicestrategy.model.enums.PaymentMode;
import com.ug.paymentservicestrategy.service.PaymentStrategy;
import org.springframework.stereotype.Service;

@Service
public class CreditCardPayment implements PaymentStrategy {

    @Override
    public PaymentMode register() {
        return PaymentMode.CREDIT_CARD;
    }

    @Override
    public String execute(double amount) {
        return "Amount paid via credit card: "+amount;
    }
}
