package com.vlad.metrics.models;

import com.google.gson.Gson;

import java.util.Arrays;

public class SensorMetric {
    double cpuTemperature;
    // TODO: Make it work or delete this shit (fan speed is very machine dependent e.g on laptops you get it by BIOS and on pc (idk maybe) you get it via motherboard)

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
