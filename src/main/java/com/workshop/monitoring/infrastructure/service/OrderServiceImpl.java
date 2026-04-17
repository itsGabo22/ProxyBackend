package com.workshop.monitoring.infrastructure.service;

import com.workshop.monitoring.domain.proxy.MicroserviceProxy;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service("orderService")
public class OrderServiceImpl implements MicroserviceProxy<Object> {

    @Override
    public Object execute(String operation, Object... params) throws Exception {
        Thread.sleep((long) (Math.random() * 300 + 100));

        switch (operation) {
            case "createOrder":
                return Map.of("orderId", UUID.randomUUID().toString(), "status", "CREATED", "items", params[0]);
            case "cancelOrder":
                return Map.of("orderId", params[0], "status", "CANCELLED");
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }
}
