package com.workshop.monitoring.application.usecase;

import com.workshop.monitoring.domain.proxy.MicroserviceProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class LoadSimulationUseCase {
    private static final Logger log = LoggerFactory.getLogger(LoadSimulationUseCase.class);
    
    private final MicroserviceProxy<Object> inventoryProxy;
    private final MicroserviceProxy<Object> orderProxy;
    private final MicroserviceProxy<Object> paymentProxy;

    private final Random random = new Random();

    public LoadSimulationUseCase(
            @Qualifier("inventoryProxy") MicroserviceProxy<Object> inventoryProxy,
            @Qualifier("orderProxy") MicroserviceProxy<Object> orderProxy,
            @Qualifier("paymentProxy") MicroserviceProxy<Object> paymentProxy) {
        this.inventoryProxy = inventoryProxy;
        this.orderProxy = orderProxy;
        this.paymentProxy = paymentProxy;
    }

    public void simulateLoad() {
        for (int i = 0; i < 50; i++) {
            int serviceChoice = random.nextInt(3);
            try {
                switch (serviceChoice) {
                    case 0:
                        inventoryProxy.execute("checkStock", "PROD-" + random.nextInt(100));
                        break;
                    case 1:
                        orderProxy.execute("createOrder", "ITEM-SET-" + random.nextInt(10));
                        break;
                    case 2:
                        paymentProxy.execute("processPayment", random.nextDouble() * 1000);
                        break;
                }
            } catch (Exception e) {
                log.warn("Simulation call failed: {}", e.getMessage());
            }
        }
    }
}
