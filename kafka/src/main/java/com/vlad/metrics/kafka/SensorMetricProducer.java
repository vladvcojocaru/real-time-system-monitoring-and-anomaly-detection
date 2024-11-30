package com.vlad.metrics.kafka;

import com.vlad.metrics.models.OsMetric;
import com.vlad.metrics.models.SensorMetric;

public class SensorMetricProducer extends MetricProducer<SensorMetric> {
    public SensorMetricProducer(String topic){
        super(topic);
    }

    @Override
    protected String serializeMetric(SensorMetric metric) {
        return metric.toJson(); // Convert OsMetric to JSON
    }
}
