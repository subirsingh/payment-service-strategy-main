package com.ug.paymentservicestrategy.service.impl;

import com.ug.paymentservicestrategy.model.enums.PaymentMode;
import com.ug.paymentservicestrategy.service.PaymentStrategy;
import org.springframework.stereotype.Service;

@Service
public class PayPalPayment implements PaymentStrategy {

    @Override
    public PaymentMode register() {
       return PaymentMode.PAYPAL;
    }
    @Override
    public String execute(double amount) {
        return "Amount paid via PayPal: "+amount;
    }
}
