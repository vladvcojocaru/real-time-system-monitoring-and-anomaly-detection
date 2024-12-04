package com.vlad.metrics.util;

public final class Constants {

    public static final String CPU_METRICS_TOPIC = "cpu-metrics";
    public static final String DISK_METRICS_TOPIC = "disk-metrics";
    public static final String MEMORY_METRICS_TOPIC = "memory-metrics";
    public static final String NETWORK_METRICS_TOPIC = "network-metrics";
    public static final String OS_METRICS_TOPIC = "os-metrics";
    public static final String SENSOR_METRICS_TOPIC = "sensor-metrics";

    // Consumer Groups
    public static final String METRICS_CONSUMER_GROUP =
        "metrics-consumer-group";

    // Private constructor to prevent instantiation
    private Constants() {
        throw new UnsupportedOperationException(
            "Constants class cannot be instantiated"
        );
    }
}
