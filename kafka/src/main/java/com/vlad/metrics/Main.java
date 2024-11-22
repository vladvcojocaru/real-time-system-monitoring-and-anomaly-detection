//package com.vlad.metrics;
//
//import com.vlad.metrics.kafka.CpuMetricProducer;
//import com.vlad.metrics.kafka.OsMetricProducer;
//import com.vlad.metrics.runnable.CpuMetricProducerRunnable;
//import com.vlad.metrics.runnable.OsMetricProducerRunnable;
//import com.vlad.metrics.services.CpuMetricService;
//import com.vlad.metrics.services.OsMetricService;
//
//public class Main {
//    public static void main(String[] args) {
//        OsMetricService osMetricService = new OsMetricService();
//        CpuMetricService cpuMetricService = new CpuMetricService();
//
//        OsMetricProducer osMetricProducer = new OsMetricProducer("os-metrics");
//        CpuMetricProducer cpuMetricProducer = new CpuMetricProducer("cpu-metrics");
//
//        OsMetricProducerRunnable osRunnable = new OsMetricProducerRunnable(
//                osMetricProducer.getProducer(), // Provide the KafkaProducer
//                osMetricProducer.getTopic(),   // Provide the Kafka topic
//                1000,                          // Collection interval in milliseconds
//                osMetricService                // Provide the OS metric service
//        );
//    }
//}
