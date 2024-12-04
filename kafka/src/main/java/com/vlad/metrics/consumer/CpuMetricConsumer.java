package com.vlad.metrics.consumer;

import com.google.gson.Gson;
import com.vlad.metrics.models.CpuMetric;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class CpuMetricConsumer {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "cpu-metrics-consumer-group");
        props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singleton("cpu-metrics"));

        Gson gson = new Gson();

        System.out.println("Listening for CpuMetric data...");

        try{
            while (true){
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, String> record: records){
                    CpuMetric cpuMetric = gson.fromJson(record.value(), CpuMetric.class);
                    System.out.println("Consumed Metric: "+ cpuMetric);

                    // Example: Forward to redis, db, process further the data etc.
                    processMetric(cpuMetric);
                }
            }
        } finally {
            consumer.close();
        }
    }
    private static void processMetric(CpuMetric cpuMetric){
        System.out.println("Processing metric: "+ cpuMetric);
    }
}

