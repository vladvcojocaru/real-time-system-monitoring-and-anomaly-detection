package com.vlad.metrics.producer.runnable;

import com.vlad.metrics.producer.kafka.SensorMetricProducer;
import com.vlad.metrics.models.SensorMetric;
import com.vlad.metrics.producer.services.SensorMetricService;

public class SensorMetricProducerRunnable implements Runnable {

    private final SensorMetricService sensorMetricService;
    private final SensorMetricProducer sensorMetricProducer;

    public SensorMetricProducerRunnable(SensorMetricService sensorMetricService,
            SensorMetricProducer sensorMetricProducer) {
        this.sensorMetricService = sensorMetricService;
        this.sensorMetricProducer = sensorMetricProducer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                SensorMetric sensorMetric = sensorMetricService.getSensorMetric();

                sensorMetricProducer.sendMetric(sensorMetric);

                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.err.println("Producer interrupted: " + e.getMessage());
        }
    }
}
