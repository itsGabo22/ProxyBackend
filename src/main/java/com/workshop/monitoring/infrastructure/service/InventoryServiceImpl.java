package com.workshop.monitoring.infrastructure.service;

import com.workshop.monitoring.domain.proxy.MicroserviceProxy;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("inventoryService")
public class InventoryServiceImpl implements MicroserviceProxy<Object> {

    @Override
    public Object execute(String operation, Object... params) throws Exception {
        // Simulate some processing time
        Thread.sleep((long) (Math.random() * 200 + 50));

        switch (operation) {
            case "checkStock":
                return Map.of("productId", params[0], "inStock", true, "quantity", 150);
            case "updateInventory":
                return Map.of("productId", params[0], "status", "updated", "newQuantity", params[1]);
            default:
                throw new IllegalArgumentException("Unknown operation: " + operation);
        }
    }
}
