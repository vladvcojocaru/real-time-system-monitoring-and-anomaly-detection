package com.vlad.metrics.producer.services;

//import com.vlad.metrics.models.old_models.CpuMetric;
import com.vlad.metrics.models.CpuMetric;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

/**
 * Service class to collect CPU usage metrics using OSHI (Operating System and Hardware Information).
 * Provides both total CPU usage and per-core usage metrics.
 */
public class CpuMetricService {
    private final CentralProcessor processor;
    private long[] prevTicks;
    private long[][] prevCoreTicks;

    public CpuMetricService() {
        this.processor = new SystemInfo().getHardware().getProcessor();
        this.prevTicks = processor.getSystemCpuLoadTicks();
        this.prevCoreTicks = processor.getProcessorCpuLoadTicks();
    }

    public CpuMetric getCpuMetrics() {
        long[] ticks = processor.getSystemCpuLoadTicks();
        long[][] coreTicks = processor.getProcessorCpuLoadTicks();

        // Calculate the total CPU usage percentage based on tick differences.
        double totalLoad = calculateCpuLoad(ticks, prevTicks) * 100;

        // Calculate per-core CPU usage percentages.
        double[] coreLoads = new double[coreTicks.length];
        for (int i = 0; i < coreTicks.length; i++) {
            coreLoads[i] = calculateCpuLoad(coreTicks[i], prevCoreTicks[i]);
        }

        // Get the frequencies for each core
        long[] currentFrequencies = processor.getCurrentFreq();

        // Update previous ticks with the current values for the next cycle.
        prevTicks = ticks;
        prevCoreTicks = coreTicks;

        // code for normal classes
        // return new CpuMetric(totalLoad, coreLoads, currentFrequencies);

        // code for protobuf shit
        // Build the protobuf CpuMetric
        CpuMetric.Builder builder = CpuMetric.newBuilder();

        builder.setTotalLoad(totalLoad);
        for(double coreLoad : coreLoads) {
            builder.addCoreLoads(coreLoad);
        }
        for(long frequency : currentFrequencies) {
            builder.addCoreLoads(frequency);
        }
        return builder.build();

    }

    /**
     * Calculates the CPU load (as a fraction) based on the difference between current and previous tick values.
     *
     * @param currentTicks  The current CPU tick values.
     * @param previousTicks The previous CPU tick values.
     * @return A double value representing the CPU load, where 0.0 means 0% and 1.0 means 100%.
     */
    private double calculateCpuLoad(long[] currentTicks, long[] previousTicks) {
        // Compute differences for each CPU tick type (user, system, idle, etc.).
        long user = currentTicks[CentralProcessor.TickType.USER.getIndex()] - previousTicks[CentralProcessor.TickType.USER.getIndex()];
        long nice = currentTicks[CentralProcessor.TickType.NICE.getIndex()] - previousTicks[CentralProcessor.TickType.NICE.getIndex()];
        long system = currentTicks[CentralProcessor.TickType.SYSTEM.getIndex()] - previousTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        long idle = currentTicks[CentralProcessor.TickType.IDLE.getIndex()] - previousTicks[CentralProcessor.TickType.IDLE.getIndex()];
        long iowait = currentTicks[CentralProcessor.TickType.IOWAIT.getIndex()] - previousTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        long irq = currentTicks[CentralProcessor.TickType.IRQ.getIndex()] - previousTicks[CentralProcessor.TickType.IRQ.getIndex()];
        long softirq = currentTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - previousTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        long steal = currentTicks[CentralProcessor.TickType.STEAL.getIndex()] - previousTicks[CentralProcessor.TickType.STEAL.getIndex()];

        // Calculate total and busy ticks.
        long total = user + nice + system + idle + iowait + irq + softirq + steal;
        long busy = total - idle;

        // Return the CPU load as a fraction of busy to total ticks.
        // If total is 0 (unlikely), return NaN to indicate no valid data.
        return total == 0 ? Double.NaN : (double) busy / total;
    }
}
