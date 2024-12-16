package com.vlad.metrics.models;

import com.google.gson.Gson;

public class MemoryMetric {

    private long totalPhysicalMemory;
    private long usedPhysicalMemory;
    private long totalSwapMemory;
    private long usedSwapMemory;
    private long virtualMemoryUsed;

    public MemoryMetric(
        long totalPhysicalMemory,
        long usedPhysicalMemory,
        long totalSwapMemory,
        long usedSwapMemory,
        long virtualMemoryUsed
    ) {
        this.totalPhysicalMemory = totalPhysicalMemory;
        this.usedPhysicalMemory = usedPhysicalMemory;
        this.totalSwapMemory = totalSwapMemory;
        this.usedSwapMemory = usedSwapMemory;
        this.virtualMemoryUsed = virtualMemoryUsed;
    }

    public long getTotalPhysicalMemory() {
        return totalPhysicalMemory;
    }

    public long getUsedPhysicalMemory() {
        return usedPhysicalMemory;
    }

    public long getTotalSwapMemory() {
        return totalSwapMemory;
    }

    public long getUsedSwapMemory() {
        return usedSwapMemory;
    }

    public long getVirtualMemoryUsed() {
        return virtualMemoryUsed;
    }

    public String toJson() {
        return new Gson()
            .toJson(
                new MemoryMetric(
                    totalPhysicalMemory,
                    usedPhysicalMemory,
                    totalSwapMemory,
                    usedSwapMemory,
                    virtualMemoryUsed
                )
            );
    }

    @Override
    public String toString() {
        return (
            "MemoryMetric{" +
            "totalPhysicalMemory=" +
            totalPhysicalMemory +
            ", usedPhysicalMemory=" +
            usedPhysicalMemory +
            ", totalSwapMemory=" +
            totalSwapMemory +
            ", usedSwapMemory=" +
            usedSwapMemory +
            ", virtualMemoryUsed=" +
            virtualMemoryUsed +
            '}'
        );
    }
}
