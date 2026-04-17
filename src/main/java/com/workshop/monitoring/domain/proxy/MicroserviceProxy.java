package com.workshop.monitoring.domain.proxy;

public interface MicroserviceProxy<T> {
    /**
     * Executes a specific operation on the microservice.
     * @param operation The name of the operation to execute.
     * @param params Parameters for the operation.
     * @return The result of the operation.
     * @throws Exception If the operation fails.
     */
    T execute(String operation, Object... params) throws Exception;
}
