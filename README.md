
This project demonstrates the strategy design pattern using Java and Spring Boot. The Strategy pattern is a behavioral design pattern that enables selecting an algorithm’s behavior at runtime. In this project, different payment services (such as credit card, PayPal, etc.) can be implemented and dynamically chosen based on the context, following the Strategy pattern.

Here’s a detailed explanation of the `payment-service-strategy-main` project, highlighting its structure, design, and code details based on its use of the Strategy Design Pattern for payment processing in a Spring Boot application.

---

## Project Purpose

This project demonstrates how to implement the Strategy Pattern in a Java Spring Boot application to handle different payment methods (like Credit Card and PayPal) in a modular and extensible way.

---

## Key Components and Code Structure

### 1. **PaymentMode Enum**
Defines available payment types.

```java
// src/main/java/com/ug/paymentservicestrategy/model/enums/PaymentMode.java
public enum PaymentMode {
    CREDIT_CARD,
    PAYPAL;
}
```

---

### 2. **Strategy Interface: PaymentStrategy**

Each payment method implements this interface.

```java
// src/main/java/com/ug/paymentservicestrategy/service/PaymentStrategy.java
public interface PaymentStrategy {
    PaymentMode register(); // Identifies which payment mode this strategy handles
    String execute(double amount); // Payment logic for this method
}
```

---

### 3. **Concrete Strategies**

#### a) **Credit Card Payment**

```java
// src/main/java/com/ug/paymentservicestrategy/service/impl/CreditCardPayment.java
@Service
public class CreditCardPayment implements PaymentStrategy {
    @Override
    public PaymentMode register() { return PaymentMode.CREDIT_CARD; }

    @Override
    public String execute(double amount) {
        // Credit card payment logic here
        return "Paid " + amount + " via Credit Card";
    }
}
```

#### b) **PayPal Payment**

```java
// src/main/java/com/ug/paymentservicestrategy/service/impl/PayPalPayment.java
@Service
public class PayPalPayment implements PaymentStrategy {
    @Override
    public PaymentMode register() { return PaymentMode.PAYPAL; }

    @Override
    public String execute(double amount) {
        // PayPal payment logic here
        return "Paid " + amount + " via PayPal";
    }
}
```

---

### 4. **Strategy Executor: PaymentExecutor & PaymentExecutorImpl**

Responsible for selecting and executing the right payment strategy.

```java
// src/main/java/com/ug/paymentservicestrategy/service/PaymentExecutor.java
public interface PaymentExecutor {
    String processPayment(PaymentMode paymentMode, double amount);
}

// src/main/java/com/ug/paymentservicestrategy/service/impl/PaymentExecutorImpl.java
@Service
@RequiredArgsConstructor
public class PaymentExecutorImpl implements PaymentExecutor {
    private final Map<PaymentMode, PaymentStrategy> paymentStrategies;

    @Autowired
    public PaymentExecutorImpl(List<PaymentStrategy> strategies) {
        paymentStrategies = new HashMap<>();
        for (PaymentStrategy strategy : strategies) {
            paymentStrategies.put(strategy.register(), strategy);
        }
    }

    @Override
    public String processPayment(PaymentMode paymentMode, double amount) {
        PaymentStrategy strategy = paymentStrategies.get(paymentMode);
        if (strategy == null) throw new IllegalArgumentException("Payment mode not supported.");
        return strategy.execute(amount);
    }
}
```
- **How it works:** All strategies are injected. The executor chooses the correct one based on the payment mode.

---

### 5. **Web Layer: PaymentController**

Handles HTTP requests to trigger payment processing.

```java
// src/main/java/com/ug/paymentservicestrategy/controller/PaymentController.java
@RestController
public class PaymentController {
    @Autowired
    private PaymentExecutor paymentExecutor;

    @PostMapping("/pay")
    public ResponseEntity<String> pay(@RequestParam PaymentMode paymentMode, @RequestParam double amount) {
        String response = paymentExecutor.processPayment(paymentMode, amount);
        return ResponseEntity.ok(response);
    }
}
```
- **How it works:** Accepts `paymentMode` and `amount` as parameters, calls the executor, and returns the result.

---

### 6. **Application Entry Point**

```java
// src/main/java/com/ug/paymentservicestrategy/PaymentServiceStrategyApplication.java
@SpringBootApplication
public class PaymentServiceStrategyApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentServiceStrategyApplication.class, args);
    }
}
```

---

### 7. **Testing**

Unit tests ensure that strategies and the executor function as expected.

```java
// src/test/java/com/ug/paymentservicestrategy/PaymentServiceStrategyApplicationTests.java
@SpringBootTest
class PaymentServiceStrategyApplicationTests {
    @Autowired
    private PaymentExecutor paymentExecutor;

    @Test
    void testCreditCardPayment() {
        String result = paymentExecutor.processPayment(PaymentMode.CREDIT_CARD, 100.0);
        Assertions.assertEquals("Paid 100.0 via Credit Card", result);
    }

    @Test
    void testPayPalPayment() {
        String result = paymentExecutor.processPayment(PaymentMode.PAYPAL, 50.0);
        Assertions.assertEquals("Paid 50.0 via PayPal", result);
    }
}
```

---

## **How Everything Fits Together**

1. **Client** sends a POST request to `/pay` with a payment mode and amount.
2. **PaymentController** receives the request and calls `PaymentExecutor`.
3. **PaymentExecutorImpl** picks the appropriate payment strategy.
4. The selected strategy's `execute` method processes the payment.
5. The **response** is returned to the client.

---

## **Extending the System**

To add a new payment method:
1. Implement the `PaymentStrategy` interface in a new class.
2. Add the new mode to the `PaymentMode` enum.
3. Spring will auto-register your new strategy.

---

**Summary:**  
This project is a clean, extensible example of the Strategy Design Pattern in a real Spring Boot application. Each payment method is encapsulated in its own class, making it easy to add, test, or modify payment strategies without touching the rest of the codebase.
