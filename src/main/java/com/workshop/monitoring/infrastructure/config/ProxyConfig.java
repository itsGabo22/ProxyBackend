package com.workshop.monitoring.infrastructure.config;

import com.workshop.monitoring.domain.proxy.MicroserviceProxy;
import com.workshop.monitoring.domain.repository.LogRepository;
import com.workshop.monitoring.infrastructure.proxy.LoggingProxy;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProxyConfig {

    @Bean("inventoryProxy")
    public MicroserviceProxy<Object> inventoryProxy(
            @Qualifier("inventoryService") MicroserviceProxy<Object> inventoryService,
            LogRepository logRepository) {
        return new LoggingProxy<>(inventoryService, "inventory", logRepository);
    }

    @Bean("orderProxy")
    public MicroserviceProxy<Object> orderProxy(
            @Qualifier("orderService") MicroserviceProxy<Object> orderService,
            LogRepository logRepository) {
        return new LoggingProxy<>(orderService, "orders", logRepository);
    }

    @Bean("paymentProxy")
    public MicroserviceProxy<Object> paymentProxy(
            @Qualifier("paymentService") MicroserviceProxy<Object> paymentService,
            LogRepository logRepository) {
        return new LoggingProxy<>(paymentService, "payments", logRepository);
    }
}
