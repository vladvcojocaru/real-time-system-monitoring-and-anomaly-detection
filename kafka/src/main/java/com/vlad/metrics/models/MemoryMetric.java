package com.vlad.metrics.models;

import com.google.gson.Gson;

public class MemoryMetric {
    long totalPhysicalMemory;
    long availablePhysicalMemory;
    long totalSwapMemory;
    long usedSwapMemory;
    long freeSwapMemory;
    long virtualMemoryUsed;

    public MemoryMetric(long totalPhysicalMemory, long availablePhysicalMemory, long totalSwapMemory, long usedSwapMemory, long freeSwapMemory, long virtualMemoryUsed) {
        this.totalPhysicalMemory = totalPhysicalMemory;
        this.availablePhysicalMemory = availablePhysicalMemory;
        this.totalSwapMemory = totalSwapMemory;
        this.usedSwapMemory = usedSwapMemory;
        this.freeSwapMemory = freeSwapMemory;
        this.virtualMemoryUsed = virtualMemoryUsed;
    }

    public long getTotalPhysicalMemory() {
        return totalPhysicalMemory;
    }

    public long getAvailablePhysicalMemory() {
        return availablePhysicalMemory;
    }

    public long getTotalSwapMemory() {
        return totalSwapMemory;
    }

    public long getUsedSwapMemory() {
        return usedSwapMemory;
    }

    public long getFreeSwapMemory() {
        return freeSwapMemory;
    }

    public long getVirtualMemoryUsed() {
        return virtualMemoryUsed;
    }

    public String toJson(){
        return new Gson().toJson(new MemoryMetric(totalPhysicalMemory, availablePhysicalMemory, totalSwapMemory, usedSwapMemory, freeSwapMemory, virtualMemoryUsed));
    }

    @Override
    public String toString() {
        return "MemoryMetric{" +
                "totalPhysicalMemory=" + totalPhysicalMemory +
                ", availablePhysicalMemory=" + availablePhysicalMemory +
                ", totalSwapMemory=" + totalSwapMemory +
                ", usedSwapMemory=" + usedSwapMemory +
                ", freeSwapMemory=" + freeSwapMemory +
                ", virtualMemoryUsed=" + virtualMemoryUsed +
                '}';
    }
}
