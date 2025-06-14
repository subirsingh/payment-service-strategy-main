Payment Service Strategy
This project demonstrates a payment processing service using the Strategy Pattern in a Spring Boot application. It provides an extensible architecture for integrating multiple payment methods (such as Credit Card, PayPal, etc.).

Key Features
Clean implementation of the Strategy Design Pattern in Java with Spring Boot.
Easily add or modify payment strategies without changing existing code.
Example code and instructions to run and test the service locally.
Ideal for developers seeking to learn or apply design patterns in real-world Spring Boot projects.

Project Overview
This project is a demonstration of using the Strategy Design Pattern in a Spring Boot application to process payments via multiple methods (e.g., Credit Card, PayPal). The design allows you to add new payment strategies with minimal code changes.

Main Components and Flow
1. PaymentMode Enum
src/main/java/com/ug/paymentservicestrategy/model/enums/PaymentMode.java

Java
public enum PaymentMode {
    CREDIT_CARD,
    PAYPAL;
}
Defines available payment types.

2. Strategy Interface: PaymentStrategy
src/main/java/com/ug/paymentservicestrategy/service/PaymentStrategy.java

Java
public interface PaymentStrategy {
    PaymentMode register();
    String execute(double amount);
}
register(): Returns the supported PaymentMode.
execute(double amount): Contains payment logic for a specific type.
3. Concrete Strategies
Credit Card Payment
src/main/java/com/ug/paymentservicestrategy/service/impl/CreditCardPayment.java

PayPal Payment
src/main/java/com/ug/paymentservicestrategy/service/impl/PayPalPayment.java

Each payment method implements the strategy interface.

4. Strategy Executor: PaymentExecutor & PaymentExecutorImpl
src/main/java/com/ug/paymentservicestrategy/service/PaymentExecutor.java

Java
public interface PaymentExecutor {
    String processPayment(PaymentMode paymentMode, double amount);
}
src/main/java/com/ug/paymentservicestrategy/service/impl/PaymentExecutorImpl.java

All payment strategies are injected and registered in a map.
To process a payment, the correct strategy is selected and executed based on the payment mode.
5. Web Layer: PaymentController
src/main/java/com/ug/paymentservicestrategy/controller/PaymentController.java

Exposes a REST endpoint /pay for payment requests.
Accepts paymentMode and amount as parameters and delegates processing to the executor.
6. Application Entry Point
src/main/java/com/ug/paymentservicestrategy/PaymentServiceStrategyApplication.java

Standard Spring Boot main class.

7. Testing
Tests check that the strategies and executor work as expected.
Example: src/test/java/com/ug/paymentservicestrategy/PaymentServiceStrategyApplicationTests.java
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
Requirements
For building and running the application you need:

JDK 17
Maven 3
Getting Started
To use this project, follow these steps:

Clone the repository to your local machine:

Shell
git clone https://github.com/your-username/your-project.git
Running the application locally:

Execute the main method in the com.ug.paymentservicestrategy.PaymentServiceStrategyApplication class from your IDE.

Or use the Spring Boot Maven plugin:

Navigate to the project directory:
Shell
cd <downloaded-location>/payment-service-strategy
Build the project using Maven:
Shell
mvn clean install
Run the application:
Shell
mvn spring-boot:run
