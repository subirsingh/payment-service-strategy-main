package com.ug.paymentservicestrategy;

import com.ug.paymentservicestrategy.model.enums.PaymentMode;
import com.ug.paymentservicestrategy.service.PaymentExecutor;
import com.ug.paymentservicestrategy.service.PaymentStrategy;
import com.ug.paymentservicestrategy.service.impl.CreditCardPayment;
import com.ug.paymentservicestrategy.service.impl.PaymentExecutorImpl;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;
import java.util.HashMap;

@SpringBootTest
class PaymentServiceStrategyApplicationTests {
	PaymentExecutor paymentExecutorImpl;
	@Mock
	PaymentStrategy paymentStrategy;

	@Test
	void contextLoads() {
	}

	@Test
	void testCreditCardPayment() {
		paymentExecutorImpl = new PaymentExecutorImpl(Collections.singletonList(new CreditCardPayment()));
		paymentExecutorImpl.processPayment(PaymentMode.CREDIT_CARD,500);
	}
	@Test
	void testPayPalPayment() {
		paymentExecutorImpl = new PaymentExecutorImpl(Collections.singletonList(new CreditCardPayment()));
		paymentExecutorImpl.processPayment(PaymentMode.CREDIT_CARD,10500);
	}
}
