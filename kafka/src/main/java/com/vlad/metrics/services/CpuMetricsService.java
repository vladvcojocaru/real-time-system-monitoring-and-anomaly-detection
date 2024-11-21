package com.vlad.metrics.services;

import com.vlad.metrics.models.CpuMetric;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

public class CpuMetricsService {
    private final CentralProcessor processor;
    private long[] prevTicks; // For total cpu load
    private long[][] prevCoreTicks; // For per-core cpu load

    public CpuMetricsService() {
        this.processor = new SystemInfo().getHardware().getProcessor();
        this.prevTicks = processor.getSystemCpuLoadTicks();
        this.prevCoreTicks = processor.getProcessorCpuLoadTicks();
    }

    public CpuMetric getCpuMetrics(){
        // Get the current tick values
        long[] ticks = processor.getSystemCpuLoadTicks();
        long[][] coreTicks = processor.getProcessorCpuLoadTicks();

        // Calculate total cpu usage
        double totalLoad = calculateCpuLoad(ticks, prevTicks) * 100;

        // Calculate per-core usage
        double[] coreLoads = new double[coreTicks.length];
        for (int i = 0; i < coreTicks.length; i++){
            coreLoads[i] = calculateCpuLoad(coreTicks[i], prevCoreTicks[i]);
        }

        prevTicks = ticks;
        prevCoreTicks = coreTicks;

        return new CpuMetric(totalLoad, coreLoads);
    }

    private double calculateCpuLoad(long[] currentTicks, long[] previousTicks){
        long user = currentTicks[CentralProcessor.TickType.USER.getIndex()] - previousTicks[CentralProcessor.TickType.USER.getIndex()];
        long nice = currentTicks[CentralProcessor.TickType.NICE.getIndex()] - previousTicks[CentralProcessor.TickType.NICE.getIndex()];
        long system = currentTicks[CentralProcessor.TickType.SYSTEM.getIndex()] - previousTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = currentTicks[CentralProcessor.TickType.IDLE.getIndex()] - previousTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long iowait = currentTicks[CentralProcessor.TickType.IOWAIT.getIndex()] - previousTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long irq = currentTicks[CentralProcessor.TickType.IRQ.getIndex()] - previousTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = currentTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - previousTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = currentTicks[CentralProcessor.TickType.STEAL.getIndex()] - previousTicks[CentralProcessor.TickType.STEAL.getIndex()];

        long total = user + nice + system + idle + iowait + irq + softirq + steal;
        long busy = total - idle;

        // Check for Nan cases
        return total == 0 ? Double.NaN : (double) busy / total;
    }
}
