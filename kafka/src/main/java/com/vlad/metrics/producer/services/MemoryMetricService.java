package com.vlad.metrics.producer.services;

import com.vlad.metrics.models.MemoryMetric;
import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;

public class MemoryMetricService {
    private final GlobalMemory memory;

    public MemoryMetricService() {
        memory = new SystemInfo().getHardware().getMemory();
    }

    public MemoryMetric getMemoryMetrics(){
        // these values are in bytes
        // Total and Used Physical Memory
        long totalPhysicalMemory = memory.getTotal();
        long usedPhysicalMemory = totalPhysicalMemory - memory.getAvailable();

        // Total and Used Swap Memory
        long totalSwapMemory = memory.getVirtualMemory().getSwapTotal();
        long usedSwapMemory = memory.getVirtualMemory().getSwapUsed();

        // Virtual Memory (In Use)
        long virtualMemoryUsed = memory.getVirtualMemory().getVirtualInUse();

        return new MemoryMetric(totalPhysicalMemory, usedPhysicalMemory, totalSwapMemory, usedSwapMemory, virtualMemoryUsed);

    }
}
