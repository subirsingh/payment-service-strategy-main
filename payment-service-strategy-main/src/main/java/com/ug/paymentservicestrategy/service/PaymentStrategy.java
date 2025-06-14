package com.ug.paymentservicestrategy.service;

import com.ug.paymentservicestrategy.model.enums.PaymentMode;

public interface PaymentStrategy {

    PaymentMode register();
    String execute(double amount);
}
