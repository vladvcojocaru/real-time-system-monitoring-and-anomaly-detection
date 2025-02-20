package com.vlad.metrics.producer.services;

import com.vlad.metrics.models.SensorMetric;
import oshi.SystemInfo;
import oshi.hardware.Sensors;

public class SensorMetricService {
    private SystemInfo systemInfo;

    public SensorMetricService() {
        systemInfo = new SystemInfo();
    }

    public SensorMetric getSensorMetric() {
        Sensors sensors = systemInfo.getHardware().getSensors();

        double cpuTemperature = sensors.getCpuTemperature();
        int[] fanSpeeds = sensors.getFanSpeeds();
        double cpuVoltage = sensors.getCpuVoltage();

        return SensorMetric.newBuilder()
                .setCpuTemperature(cpuTemperature)
                //.addAllFanSpeeds(fanSpeeds)
                .setCpuVoltage(cpuVoltage)
                .build();
    }
}
