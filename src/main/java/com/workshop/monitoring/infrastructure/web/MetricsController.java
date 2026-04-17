package com.workshop.monitoring.infrastructure.web;

import com.workshop.monitoring.application.usecase.LoadSimulationUseCase;
import com.workshop.monitoring.application.usecase.MetricsUseCase;
import com.workshop.monitoring.domain.model.LogEntry;
import com.workshop.monitoring.domain.repository.LogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import com.workshop.monitoring.application.usecase.MetricSummary;

@RestController
@RequestMapping("/api/metrics")
@CrossOrigin(origins = "*")
public class MetricsController {

    private final MetricsUseCase metricsUseCase;
    private final LogRepository logRepository;
    private final LoadSimulationUseCase loadSimulationUseCase;

    public MetricsController(MetricsUseCase metricsUseCase, LogRepository logRepository, LoadSimulationUseCase loadSimulationUseCase) {
        this.metricsUseCase = metricsUseCase;
        this.logRepository = logRepository;
        this.loadSimulationUseCase = loadSimulationUseCase;
    }

    @GetMapping("/summary")
    public ResponseEntity<List<MetricSummary>> getSummary() {
        return ResponseEntity.ok(metricsUseCase.getSummary());
    }

    @GetMapping("/logs")
    public ResponseEntity<Page<LogEntry>> getLogs(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        return ResponseEntity.ok(logRepository.findFilteredLogs(
                service, status, from, to, 
                PageRequest.of(page, size, Sort.by("timestamp").descending())));
    }

    @PostMapping("/simulate-load")
    public ResponseEntity<Map<String, String>> simulateLoad() {
        loadSimulationUseCase.simulateLoad();
        return ResponseEntity.ok(Map.of("message", "Simulation of 50 calls completed successfully"));
    }
}
