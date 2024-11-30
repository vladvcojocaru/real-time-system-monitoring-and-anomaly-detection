package com.vlad.metrics.services;

import com.vlad.metrics.models.SensorMetric;
import oshi.SystemInfo;
import oshi.hardware.Sensors;

public class SensorMetricService {
    private SystemInfo systemInfo;

    public SensorMetricService(){
        systemInfo = new SystemInfo();
    }

    public SensorMetric getSensorMetrics(){
        Sensors sensors = systemInfo.getHardware().getSensors();

        double cpuTemperature = sensors.getCpuTemperature();
        int[] fanSpeeds = sensors.getFanSpeeds();
        double cpuVoltage = sensors.getCpuVoltage();

        return new SensorMetric(cpuTemperature, fanSpeeds, cpuVoltage);
    }
}
