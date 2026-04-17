package com.workshop.monitoring.application.usecase;

import com.workshop.monitoring.domain.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MetricsUseCase {
    private final LogRepository logRepository;

    public MetricsUseCase(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    public List<MetricSummary> getSummary() {
        List<Object[]> results = logRepository.getMetricsSummary();
        return results.stream().map(row -> {
            String serviceId = (String) row[0];
            long total = (long) row[1];
            long errors = (long) row[2];
            double avg = (double) row[3];
            
            double errorRate = total > 0 ? (double) errors / total * 100 : 0;
            
            return MetricSummary.builder()
                    .serviceId(serviceId)
                    .totalCalls(total)
                    .errorRate(errorRate)
                    .avgResponseTime(avg)
                    .build();
        }).collect(Collectors.toList());
    }
}
