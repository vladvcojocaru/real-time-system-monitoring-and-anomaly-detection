package com.vlad.metrics.producer.services;

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

        return OsMetric.newBuilder()
                .setOsName(os_name)
                .setUptime(uptime)
                .setProcesses(processes)
                .setThreads(threads)
                .build();
    }
}
