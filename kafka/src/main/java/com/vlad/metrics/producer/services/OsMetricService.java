package com.vlad.metrics.services;

import com.vlad.metrics.models.OsMetric;
import oshi.SystemInfo;
import oshi.software.os.OperatingSystem;

public class OsMetricService {
    private final OperatingSystem operatingSystem;

    public OsMetricService() {
        operatingSystem = new SystemInfo().getOperatingSystem();
    }

    public OsMetric getOsMetrics(){
        String os_name = operatingSystem.toString();
        long uptime = operatingSystem.getSystemUptime();
        int processes = operatingSystem.getProcessCount();
        int threads = operatingSystem.getThreadCount();

        return new OsMetric(os_name, uptime, processes, threads);
    }
}
