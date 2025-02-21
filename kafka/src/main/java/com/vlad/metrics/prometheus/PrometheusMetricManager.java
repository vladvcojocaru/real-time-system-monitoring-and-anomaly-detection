package com.vlad.metrics.prometheus;
import com.vlad.metrics.models.*;
import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.HTTPServer;


import java.io.IOException;


public class PrometheusMetricManager {
    // CPU METRIC
    public static final Gauge cpuTotalLoadGauge = Gauge.build()
            .name("cpu_total_load")
            .help("Total CPU load")
            .labelNames("machine")
            .register();

    public static final Gauge cpuCoreLoadGauge = Gauge.build()
            .name("cpu_core_load")
            .help("CPU load per core")
            .labelNames("core", "machine")
            .register();

    public static final Gauge cpuFrequencyGauge = Gauge.build()
            .name("cpu_frequency")
            .help("CPU frequency per core")
            .labelNames("core", "machine")
            .register();

    // DISK METRIC
    public static final Counter diskReadsCounter = Counter.build()
            .name("disk_reads_total")
            .help("Total number of disk read operations")
            .labelNames("disk", "machine")
            .register();

    public static final Counter diskWritesCounter = Counter.build()
            .name("disk_writes_total")
            .help("Total number of disk write operations")
            .labelNames("disk", "machine")
            .register();

    public static final Counter diskReadBytesCounter = Counter.build()
            .name("disk_read_bytes_total")
            .help("Total disk read bytes")
            .labelNames("disk", "machine")
            .register();

    public static final Counter diskWriteBytesCounter = Counter.build()
            .name("disk_write_bytes_total")
            .help("Total disk write bytes")
            .labelNames("disk", "machine")
            .register();

    public static final Gauge diskQueueLengthGauge = Gauge.build()
            .name("disk_queue_length")
            .help("Current disk queue length")
            .labelNames("disk", "machine")
            .register();

    public static final Gauge diskTransferTimeGauge = Gauge.build()
            .name("disk_transfer_time")
            .help("Current disk transfer time")
            .labelNames("disk", "machine")
            .register();

    // --- Memory Metrics ---
    public static final Gauge totalPhysicalMemoryGauge = Gauge.build()
            .name("memory_total_physical")
            .help("Total physical memory")
            .labelNames("machine")
            .register();

    public static final Gauge usedPhysicalMemoryGauge = Gauge.build()
            .name("memory_used_physical")
            .help("Used physical memory")
            .labelNames("machine")
            .register();

    public static final Gauge totalSwapMemoryGauge = Gauge.build()
            .name("memory_total_swap")
            .help("Total swap memory")
            .labelNames("machine")
            .register();

    public static final Gauge usedSwapMemoryGauge = Gauge.build()
            .name("memory_used_swap")
            .help("Used swap memory")
            .labelNames("machine")
            .register();

    public static final Gauge virtualMemoryUsedGauge = Gauge.build()
            .name("memory_virtual_used")
            .help("Virtual memory used")
            .labelNames("machine")
            .register();

    // --- Network Metrics ---
    public static final Counter bytesSentCounter = Counter.build()
            .name("network_bytes_sent_total")
            .help("Total network bytes sent")
            .labelNames("interface", "machine")
            .register();

    public static final Counter bytesRecvCounter = Counter.build()
            .name("network_bytes_recv_total")
            .help("Total network bytes received")
            .labelNames("interface", "machine")
            .register();

    public static final Counter packetsSentCounter = Counter.build()
            .name("network_packets_sent_total")
            .help("Total network packets sent")
            .labelNames("interface", "machine")
            .register();

    public static final Counter packetsRecvCounter = Counter.build()
            .name("network_packets_recv_total")
            .help("Total network packets received")
            .labelNames("interface", "machine")
            .register();

    // --- OS Metrics ---
    public static final Gauge uptimeGauge = Gauge.build()
            .name("os_uptime")
            .help("OS uptime")
            .labelNames("os_name", "machine")
            .register();

    public static final Gauge processesGauge = Gauge.build()
            .name("os_processes")
            .help("Number of OS processes")
            .labelNames("os_name", "machine")
            .register();

    public static final Gauge threadsGauge = Gauge.build()
            .name("os_threads")
            .help("Number of OS threads")
            .labelNames("os_name", "machine")
            .register();

    // --- Sensor Metrics ---
    public static final Gauge cpuTemperatureGauge = Gauge.build()
            .name("sensor_cpu_temperature")
            .help("CPU temperature")
            .labelNames("machine")
            .register();

    public static final Gauge cpuVoltageGauge = Gauge.build()
            .name("sensor_cpu_voltage")
            .help("CPU voltage")
            .labelNames("machine")
            .register();

    // TODO: make it work or delete it
    public static final Gauge fanSpeedGauge = Gauge.build()
            .name("sensor_fan_speed")
            .help("Fan speed")
            .labelNames("fan")
            .register();

    // --- Update Methods ---
    public static void updateCpuMetrics(CpuMetric cpuMetric, String machineId) {
        cpuTotalLoadGauge.labels(machineId).set(cpuMetric.getTotalLoad());
        for (int i = 0; i < cpuMetric.getCoreLoadsCount(); i++) {
            cpuCoreLoadGauge.labels(String.valueOf(i), machineId).set(cpuMetric.getCoreLoads(i));
            // TODO: if the program breaks this is the first thing that you should check dumb ass
            cpuFrequencyGauge.labels(String.valueOf(i), machineId).set(cpuMetric.getFrequency(i));
        }
    }

    public static void updateDiskMetrics(DiskMetric diskMetric, String machineId) {
        String diskName = diskMetric.getDiskName();
        // If these values are cumulative, you might compute a delta before incrementing.
        diskReadsCounter.labels(diskName, machineId).inc();
        diskWritesCounter.labels(diskName, machineId).inc();
        diskReadBytesCounter.labels(diskName, machineId).inc(diskMetric.getDiskReadBytes());
        diskWriteBytesCounter.labels(diskName, machineId).inc(diskMetric.getDiskWriteBytes());
        diskQueueLengthGauge.labels(diskName, machineId).set(diskMetric.getDiskQueueLength());
        diskTransferTimeGauge.labels(diskName, machineId).set(diskMetric.getDiskTransferTime());
    }

    public static void updateMemoryMetrics(MemoryMetric memoryMetric, String machineId) {
        totalPhysicalMemoryGauge.labels(machineId).set(memoryMetric.getTotalPhysicalMemory());
        usedPhysicalMemoryGauge.labels(machineId).set(memoryMetric.getUsedPhysicalMemory());
        totalSwapMemoryGauge.labels(machineId).set(memoryMetric.getTotalSwapMemory());
        usedSwapMemoryGauge.labels(machineId).set(memoryMetric.getUsedSwapMemory());
        virtualMemoryUsedGauge.labels(machineId).set(memoryMetric.getVirtualMemoryUsed());
    }

    public static void updateNetworkMetrics(NetworkMetric networkMetric, String machineId) {
        String interface_name = networkMetric.getName(); // or use displayName if preferred
        bytesSentCounter.labels(interface_name, machineId).inc(networkMetric.getBytesSent());
        bytesRecvCounter.labels(interface_name, machineId).inc(networkMetric.getBytesRecv());
        packetsSentCounter.labels(interface_name, machineId).inc(networkMetric.getPacketsSent());
        packetsRecvCounter.labels(interface_name, machineId).inc(networkMetric.getPacketsRecv());
    }

    public static void updateOsMetrics(OsMetric osMetric, String machineId) {
        String os_name = osMetric.getOsName(); // or use displayName if preferred
        uptimeGauge.labels(os_name ,machineId).set(osMetric.getUptime());
        processesGauge.labels(os_name ,machineId).set(osMetric.getProcesses());
        threadsGauge.labels(os_name ,machineId).set(osMetric.getThreads());
    }

    public static void updateSensorMetrics(SensorMetric sensorMetric, String machineId) {
        cpuTemperatureGauge.labels(machineId).set(sensorMetric.getCpuTemperature());
        cpuVoltageGauge.labels(machineId).set(sensorMetric.getCpuVoltage());
        for (int i = 0; i < sensorMetric.getFanSpeedsCount(); i++) {
            fanSpeedGauge.labels(String.valueOf(i), machineId).set(sensorMetric.getFanSpeeds(i));
        }
    }

    // --- Start the Metrics HTTP Server ---
    public static void startMetricsServer(int port) throws IOException {
        // This also registers default JVM metrics (memory, GC, etc.)
        // DefaultExports.initialize();
        new HTTPServer(port);
    }
}
