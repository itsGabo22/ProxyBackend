package com.workshop.monitoring.infrastructure.service;

import com.workshop.monitoring.domain.proxy.MicroserviceProxy;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

@Service("paymentService")
public class PaymentServiceImpl implements MicroserviceProxy<Object> {
    private final Random random = new Random();

    @Override
    public Object execute(String operation, Object... params) throws Exception {
        Thread.sleep((long) (Math.random() * 500 + 200));

        // Randomly fail 10% of the time
        if (random.nextDouble() < 0.10) {
            throw new RuntimeException("Payment gateway connection timeout (Simulated Failure)");
        }

        switch (operation) {
            case "processPayment":
                return Map.of(
                    "transactionId", "TXN-" + System.currentTimeMillis(),
                    "status", "COMPLETED",
                    "amount", params[0],
                    "currency", "USD"
                );
            case "refundPayment":
                return Map.of("refundId", "REF-" + System.currentTimeMillis(), "status", "PROCESSED");
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }
}
