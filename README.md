Project Overview
This project is a demonstration of using the Strategy Design Pattern in a Spring Boot application to process payments via multiple methods (e.g., Credit Card, PayPal). The design allows you to add new payment methods in the future with minimal changes to the existing codebase.

Main Components and Flow
1. PaymentMode Enum

src/main/java/com/ug/paymentservicestrategy/model/enums/PaymentMode.java
package com.ug.paymentservicestrategy.model.enums;

public enum PaymentMode {
    CREDIT_CARD,
    PAYPAL;
}
Defines available payment types.

2. Strategy Interface: PaymentStrategy

src/main/java/com/ug/paymentservicestrategy/service/PaymentStrategy.java
package com.ug.paymentservicestrategy.service;

import com.ug.paymentservicestrategy.model.enums.PaymentMode;

public interface PaymentStrategy {
    PaymentMode register();
register(): Returns the supported PaymentMode.
execute(double amount): Contains payment logic for a specific type.
3. Concrete Strategies
Credit Card Payment:


src/main/java/com/ug/paymentservicestrategy/service/impl/CreditCardPayment.java
package com.ug.paymentservicestrategy.service.impl;

import com.ug.paymentservicestrategy.model.enums.PaymentMode;
import com.ug.paymentservicestrategy.service.PaymentStrategy;
import org.springframework.stereotype.Service;

PayPal Payment:


src/main/java/com/ug/paymentservicestrategy/service/impl/PayPalPayment.java
package com.ug.paymentservicestrategy.service.impl;

import com.ug.paymentservicestrategy.model.enums.PaymentMode;
import com.ug.paymentservicestrategy.service.PaymentStrategy;
import org.springframework.stereotype.Service;

Each payment method implements the strategy interface.

4. Strategy Executor: PaymentExecutor & PaymentExecutorImpl

src/main/java/com/ug/paymentservicestrategy/service/PaymentExecutor.java
package com.ug.paymentservicestrategy.service;

import com.ug.paymentservicestrategy.model.enums.PaymentMode;

public interface PaymentExecutor {
    String processPayment(PaymentMode paymentMode, double amount) ;

src/main/java/com/ug/paymentservicestrategy/service/impl/PaymentExecutorImpl.java
package com.ug.paymentservicestrategy.service.impl;

import com.ug.paymentservicestrategy.model.enums.PaymentMode;
import com.ug.paymentservicestrategy.service.PaymentExecutor;
import com.ug.paymentservicestrategy.service.PaymentStrategy;
import lombok.RequiredArgsConstructor;
All payment strategies are injected and registered in a map.
To process a payment, the correct strategy is selected and executed based on the payment mode.
5. Web Layer: PaymentController

src/main/java/com/ug/paymentservicestrategy/controller/PaymentController.java
package com.ug.paymentservicestrategy.controller;

import com.ug.paymentservicestrategy.model.enums.PaymentMode;
import com.ug.paymentservicestrategy.service.PaymentExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
Exposes a REST endpoint /pay for payment requests.
Accepts paymentMode and amount as parameters and delegates processing to the executor.
6. Application Entry Point

src/main/java/com/ug/paymentservicestrategy/PaymentServiceStrategyApplication.java
package com.ug.paymentservicestrategy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
Standard Spring Boot main class.

7. Testing
Tests check that the strategies and executor work as expected.


src/test/java/com/ug/paymentservicestrategy/PaymentServiceStrategyApplicationTests.java
package com.ug.paymentservicestrategy;

import com.ug.paymentservicestrategy.model.enums.PaymentMode;
import com.ug.paymentservicestrategy.service.PaymentExecutor;
import com.ug.paymentservicestrategy.service.PaymentStrategy;
import com.ug.paymentservicestrategy.service.impl.CreditCardPayment;
How It All Fits Together
User sends a POST request to /pay with a payment mode and amount.
PaymentController receives the request and calls PaymentExecutor.
PaymentExecutorImpl selects the correct strategy implementation (e.g., CreditCardPayment, PayPalPayment) based on the PaymentMode.
The chosen strategy's execute method is called to perform the payment logic.
The response is returned to the user.
Extending the System
To add a new payment method:

Implement the PaymentStrategy interface in a new class.
Register the new payment mode in the enum.
Spring will auto-register it and it will be available for use.
