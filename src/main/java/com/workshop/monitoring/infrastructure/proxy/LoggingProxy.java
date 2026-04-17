package com.workshop.monitoring.infrastructure.proxy;

import com.workshop.monitoring.domain.model.LogEntry;
import com.workshop.monitoring.domain.proxy.MicroserviceProxy;
import com.workshop.monitoring.domain.repository.LogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

public class LoggingProxy<T> implements MicroserviceProxy<T> {

    private final MicroserviceProxy<T> target;
    private final String serviceId;
    private final LogRepository logRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoggingProxy(MicroserviceProxy<T> target, String serviceId, LogRepository logRepository) {
        this.target = target;
        this.serviceId = serviceId;
        this.logRepository = logRepository;
    }

    @Override
    public T execute(String operation, Object... params) throws Exception {
        String requestId = UUID.randomUUID().toString();
        long startTime = System.currentTimeMillis();
        String paramsJson = "";
        
        try {
            paramsJson = objectMapper.writeValueAsString(params);
        } catch (Exception e) {
            paramsJson = Arrays.toString(params);
        }

        try {
            // Register start implicit in logic
            T result = target.execute(operation, params);
            
            long duration = System.currentTimeMillis() - startTime;
            String resultJson = "";
            try {
                resultJson = objectMapper.writeValueAsString(result);
            } catch (Exception e) {
                resultJson = String.valueOf(result);
            }

            saveLog(requestId, operation, duration, "SUCCESS", paramsJson, resultJson, null, null);
            
            return result;
        } catch (Exception e) {
            long duration = System.currentTimeMillis() - startTime;
            
            String errorMessage = e.getMessage();
            String stackTrace = getSummarizedStackTrace(e);

            saveLog(requestId, operation, duration, "ERROR", paramsJson, null, errorMessage, stackTrace);
            
            throw e;
        }
    }

    private void saveLog(String requestId, String operation, long duration, String status, 
                         String params, String response, String error, String stackTrace) {
        LogEntry log = new LogEntry();
        log.setRequestId(requestId);
        log.setServiceId(serviceId);
        log.setOperation(operation);
        log.setDurationMs(duration);
        log.setStatus(status);
        log.setTimestamp(LocalDateTime.now());
        log.setInputParams(params);
        log.setResponseBody(response);
        log.setErrorMessage(error);
        log.setStackTrace(stackTrace);
        
        logRepository.save(log);
    }

    private String getSummarizedStackTrace(Exception e) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (StackTraceElement element : e.getStackTrace()) {
            if (count >= 5) break; 
            sb.append(element.toString()).append("\n");
            count++;
        }
        return sb.toString();
    }
}
