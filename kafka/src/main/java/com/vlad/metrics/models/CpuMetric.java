package com.vlad.metrics.models;

import com.google.gson.Gson;

import java.util.Arrays;

/**
 * Represents CPU usage metrics, including total CPU load and per-core loads.
 * This class provides functionality to sanitize the data and serialize it into JSON format.
 */
public class CpuMetric {
    // The total CPU usage as a percentage (sum of all core usages normalized to 100%).
    private double totalLoad;

    // An array representing the percentage usage of each individual core.
    private double[] coreLoads;

    private long[] frequency;

    /**
     * Constructor to initialize the CpuMetric object with total CPU load and per-core loads.
     *
     * @param totalLoad The total CPU usage as a percentage.
     * @param coreLoads An array of per-core usage percentages.
     */
    public CpuMetric(double totalLoad, double[] coreLoads, long[] frequency) {
        this.totalLoad = totalLoad;
        this.coreLoads = coreLoads;
        this.frequency = frequency;
    }


    /**
     * Returns the total CPU load percentage.
     *
     * @return The total CPU usage as a percentage.
     */
    public double getTotalLoad() {
        return totalLoad;
    }

    /**
     * Returns the per-core CPU load percentages.
     *
     * @return An array of per-core CPU usage percentages.
     */
    public double[] getCoreLoad() {
        return coreLoads;
    }

    public long[] getFrequency() {
        return frequency;
    }

    // TODO: change this to use PROTOBUF

    /**
     * Serializes the CpuMetric object into a JSON string using Gson.
     * Before serialization, all values (total and per-core loads) are sanitized
     * to replace NaN or Infinity with 0.0 to ensure valid JSON output.
     *
     * @return A JSON string representation of the CpuMetric object.
     */
    public String toJson() {
        // Sanitize total CPU load
        double sanitizedTotalLoad = sanitize(totalLoad);

        // Sanitize per-core CPU loads
        double[] sanitizedCoreLoads = Arrays.stream(coreLoads)
                .map(this::sanitize) // Apply the sanitize method to each core load
                .toArray();

        // ! Frequency doesn't need sanitize because long can't be NaN or infinite

        // Create a sanitized CpuMetric object for serialization
        CpuMetric sanitizedMetric = new CpuMetric(sanitizedTotalLoad, sanitizedCoreLoads, frequency);

        // Convert the sanitized object to JSON using Gson
        return new Gson().toJson(sanitizedMetric);
    }

    /**
     * Ensures a given double value is valid for JSON.
     * If the value is NaN (Not a Number) or Infinity, it is replaced with 0.0.
     *
     * @param value The double value to sanitize.
     * @return The sanitized double value.
     */
    private double sanitize(double value) {
        return Double.isNaN(value) || Double.isInfinite(value) ? 0.0 : value;
    }


    /**
     * Provides a string representation of the CpuMetric object.
     * Useful for debugging or logging purposes.
     *
     * @return A string containing the total load and per-core loads.
     */
    @Override
    public String toString() {
        return "CpuMetric{" +
                "totalLoad=" + totalLoad +
                ", coreLoads=" + Arrays.toString(coreLoads) +
                ", frequency=" + Arrays.toString(frequency) +
                '}';
    }
}
