package com.vlad.metrics.producer.kafka;

import com.vlad.metrics.models.SensorMetric;

public class SensorMetricProducer extends MetricProducer<SensorMetric> {
    public SensorMetricProducer(String topic) {
        super(topic);
    }

    @Override
    protected String serializeMetric(SensorMetric metric) {
        return metric.toJson(); // Convert OsMetric to JSON
    }
}
