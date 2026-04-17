package com.workshop.monitoring.application.usecase;

public class MetricSummary {
    private String serviceId;
    private long totalCalls;
    private double errorRate;
    private double avgResponseTime;

    public MetricSummary() {}

    public MetricSummary(String serviceId, long totalCalls, double errorRate, double avgResponseTime) {
        this.serviceId = serviceId;
        this.totalCalls = totalCalls;
        this.errorRate = errorRate;
        this.avgResponseTime = avgResponseTime;
    }

    public String getServiceId() { return serviceId; }
    public void setServiceId(String serviceId) { this.serviceId = serviceId; }

    public long getTotalCalls() { return totalCalls; }
    public void setTotalCalls(long totalCalls) { this.totalCalls = totalCalls; }

    public double getErrorRate() { return errorRate; }
    public void setErrorRate(double errorRate) { this.errorRate = errorRate; }

    public double getAvgResponseTime() { return avgResponseTime; }
    public void setAvgResponseTime(double avgResponseTime) { this.avgResponseTime = avgResponseTime; }

    public static MetricSummaryBuilder builder() {
        return new MetricSummaryBuilder();
    }

    public static class MetricSummaryBuilder {
        private String serviceId;
        private long totalCalls;
        private double errorRate;
        private double avgResponseTime;

        public MetricSummaryBuilder serviceId(String serviceId) { this.serviceId = serviceId; return this; }
        public MetricSummaryBuilder totalCalls(long totalCalls) { this.totalCalls = totalCalls; return this; }
        public MetricSummaryBuilder errorRate(double errorRate) { this.errorRate = errorRate; return this; }
        public MetricSummaryBuilder avgResponseTime(double avgResponseTime) { this.avgResponseTime = avgResponseTime; return this; }
        public MetricSummary build() {
            return new MetricSummary(serviceId, totalCalls, errorRate, avgResponseTime);
        }
    }
}
