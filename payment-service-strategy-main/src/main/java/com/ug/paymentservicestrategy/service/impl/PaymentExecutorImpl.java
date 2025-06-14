package com.ug.paymentservicestrategy.service.impl;

import com.ug.paymentservicestrategy.model.enums.PaymentMode;
import com.ug.paymentservicestrategy.service.PaymentExecutor;
import com.ug.paymentservicestrategy.service.PaymentStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class PaymentExecutorImpl implements PaymentExecutor {
    private  HashMap<PaymentMode, PaymentStrategy> paymentStrategyMap = new HashMap<>();

    public PaymentExecutorImpl(@Autowired List<PaymentStrategy> paymentStrategy){
        paymentStrategy.stream().forEach(paymentMode -> {
            paymentStrategyMap.put(paymentMode.register(), paymentMode);
        });
    }
    @Override
    public String processPayment(PaymentMode paymentMode,double amount) {
        return paymentStrategyMap.get(paymentMode)
                .execute(amount);
    }
}
