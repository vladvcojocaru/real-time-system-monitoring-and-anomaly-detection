package com.vlad.metrics.models;

import com.google.gson.Gson;

import java.util.Arrays;

public class SensorMetric {
    double cpuTemperature;
    int[] fanSpeeds;
    double cpuVoltage;

    public SensorMetric(double cpuTemperature, int[] fanSpeeds, double cpuVoltage) {
        this.cpuTemperature = cpuTemperature;
        this.fanSpeeds = fanSpeeds;
        this.cpuVoltage = cpuVoltage;
    }

    public double getCpuTemperature() {
        return cpuTemperature;
    }

    public int[] getFanSpeeds() {
        return fanSpeeds;
    }

    public double getCpuVoltage() {
        return cpuVoltage;
    }

    public String toJson(){
        return new Gson().toJson(new SensorMetric(cpuTemperature, fanSpeeds, cpuVoltage));
    }

    @Override
    public String toString() {
        return "SensorMetric{" +
                "cpuTemperature=" + cpuTemperature +
                ", fanSpeeds=" + Arrays.toString(fanSpeeds) +
                ", cpuVoltage=" + cpuVoltage +
                '}';
    }
}
