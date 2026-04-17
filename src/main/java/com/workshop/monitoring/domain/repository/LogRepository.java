package com.workshop.monitoring.domain.repository;

import com.workshop.monitoring.domain.model.LogEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntry, Long> {
    
    @Query("SELECT l FROM LogEntry l WHERE " +
            "(:serviceId IS NULL OR l.serviceId = :serviceId) AND " +
            "(:status IS NULL OR l.status = :status) AND " +
            "(:from IS NULL OR l.timestamp >= :from) AND " +
            "(:to IS NULL OR l.timestamp <= :to)")
    Page<LogEntry> findFilteredLogs(
            @Param("serviceId") String serviceId,
            @Param("status") String status,
            @Param("from") LocalDateTime from,
            @Param("to") LocalDateTime to,
            Pageable pageable);

    @Query("SELECT l.serviceId as serviceId, count(l) as totalCalls, " +
            "sum(case when l.status = 'ERROR' then 1 else 0 end) as errorCount, " +
            "avg(l.durationMs) as avgDuration " +
            "FROM LogEntry l GROUP BY l.serviceId")
    List<Object[]> getMetricsSummary();
}
