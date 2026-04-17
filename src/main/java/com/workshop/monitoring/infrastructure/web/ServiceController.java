package com.workshop.monitoring.infrastructure.web;

import com.workshop.monitoring.domain.proxy.MicroserviceProxy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*") 
public class ServiceController {

    private final MicroserviceProxy<Object> inventoryProxy;
    private final MicroserviceProxy<Object> orderProxy;
    private final MicroserviceProxy<Object> paymentProxy;

    public ServiceController(
            @Qualifier("inventoryProxy") MicroserviceProxy<Object> inventoryProxy,
            @Qualifier("orderProxy") MicroserviceProxy<Object> orderProxy,
            @Qualifier("paymentProxy") MicroserviceProxy<Object> paymentProxy) {
        this.inventoryProxy = inventoryProxy;
        this.orderProxy = orderProxy;
        this.paymentProxy = paymentProxy;
    }

    @PostMapping("/inventory/{operation}")
    public ResponseEntity<Object> callInventory(@PathVariable String operation, @RequestBody(required = false) Object[] params) throws Exception {
        return ResponseEntity.ok(inventoryProxy.execute(operation, params));
    }

    @PostMapping("/orders/{operation}")
    public ResponseEntity<Object> callOrders(@PathVariable String operation, @RequestBody(required = false) Object[] params) throws Exception {
        return ResponseEntity.ok(orderProxy.execute(operation, params));
    }

    @PostMapping("/payments/{operation}")
    public ResponseEntity<Object> callPayments(@PathVariable String operation, @RequestBody(required = false) Object[] params) throws Exception {
        return ResponseEntity.ok(paymentProxy.execute(operation, params));
    }
}
