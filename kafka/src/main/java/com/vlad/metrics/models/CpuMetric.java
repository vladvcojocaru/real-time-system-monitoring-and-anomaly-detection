package com.vlad.metrics.models;

import com.google.gson.Gson;

import java.util.Arrays;

public class CpuMetric {
    private double totalLoad; // Total CPU usage as a percentage
    private double[] coreLoads; // Per-core CPU usage as percentages

    public CpuMetric(double totalLoad, double[] coreLoads) {
        this.totalLoad = totalLoad;
        this.coreLoads = coreLoads;
    }

    public double getTotalLoad() {
        return totalLoad;
    }

    public double[] getCoreLoad() {
        return coreLoads;
    }

    // TODO: change this to use PROTOBUF
    public String toJson(){
        // Sanitize values before serializing to JSON
        double sanitizedTotalLoad = sanitize(totalLoad);
        double[] sanitizedCoreLoads = Arrays.stream(coreLoads)
                .map(this::sanitize)
                .toArray();

        // Create a temporary CpuMetric object with sanitized values for serialization
        CpuMetric sanitizedMetric = new CpuMetric(sanitizedTotalLoad, sanitizedCoreLoads);
        return new Gson().toJson(sanitizedMetric);
    }

    // Sanitize a single double value (in case a Cpu metric is Nan)
    private double sanitize(double value) {
        return Double.isNaN(value) || Double.isInfinite(value) ? 0.0 : value;
    }

    @Override
    public String toString() {
        return "CpuMetric{" +
                "totalLoad=" + totalLoad +
                ", coreLoads=" + Arrays.toString(coreLoads) +
                '}';
    }
}
