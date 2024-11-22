import oshi.SystemInfo;
import oshi.hardware.*;
import oshi.software.os.*;
import oshi.util.FormatUtil;

public class ContinuousSystemMonitoring {
    public static void main(String[] args) {
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        OperatingSystem os = si.getOperatingSystem();

        // Set refresh interval in milliseconds (e.g., 2000 ms = 2 seconds)
        int refreshInterval = 2000; // Type: int

        System.out.println("=== Real-Time System Monitoring ===");

        while (true) {
            try {
                // Operating System Metrics
                System.out.println("\n=== Operating System ===");
                System.out.println("OS: " + os); // Type: String
                System.out.println("Uptime: " + FormatUtil.formatElapsedSecs(os.getSystemUptime()));
                // Type: long (seconds)
                System.out.println("Processes: " + os.getProcessCount()); // Type: int
                System.out.println("Threads: " + os.getThreadCount()); // Type: int

                // CPU Metrics
                CentralProcessor processor = hal.getProcessor();
                System.out.println("\n=== CPU Metrics ===");
                System.out.println("CPU Load: " + (processor.getSystemCpuLoadBetweenTicks() * 100) + "%");
                // Type: double (percentage load 0.0–100.0)
                long[] currentFrequencies = processor.getCurrentFreq();
                if (currentFrequencies != null) {
                    for (int i = 0; i < currentFrequencies.length; i++) {
                        System.out.println("Core " + i + " Frequency: " + currentFrequencies[i] / 1_000_000 + " MHz");
                        // Type: long (frequency in Hz, converted to MHz)
                    }
                } else {
                    System.out.println("Current frequencies not available.");
                }

                // Memory Metrics
                GlobalMemory memory = hal.getMemory();
                System.out.println("\n=== Memory Metrics ===");
                System.out.println("Total Memory: " + FormatUtil.formatBytes(memory.getTotal()));
                // Type: long (bytes)
                System.out.println("Available Memory: " + FormatUtil.formatBytes(memory.getAvailable()));
                // Type: long (bytes)
                VirtualMemory virtualMemory = memory.getVirtualMemory();
                System.out.println("Virtual Memory Used: " + FormatUtil.formatBytes(virtualMemory.getVirtualInUse()));
                // Type: long (bytes)

                // Disk Metrics
                System.out.println("\n=== Disk Metrics ===");
                for (HWDiskStore disk : hal.getDiskStores()) {
                    disk.updateAttributes(); // Refresh disk stats
                    System.out.println("Disk: " + disk.getName()); // Type: String
                    System.out.println("  Reads: " + disk.getReads()); // Type: long
                    System.out.println("  Writes: " + disk.getWrites()); // Type: long
                }

                // Network Metrics
                System.out.println("\n=== Network Metrics ===");
                for (NetworkIF net : hal.getNetworkIFs()) {
                    net.updateAttributes(); // Refresh network stats
                    System.out.println("Network Interface: " + net.getName()); // Type: String
                    System.out.println("  Bytes Sent: " + FormatUtil.formatBytes(net.getBytesSent()));
                    // Type: long (bytes)
                    System.out.println("  Bytes Received: " + FormatUtil.formatBytes(net.getBytesRecv()));
                    // Type: long (bytes)
                }

                // Sensors (if supported)
                System.out.println("\n=== Sensors ===");
                Sensors sensors = hal.getSensors();
                System.out.println("CPU Temperature: " + sensors.getCpuTemperature() + "°C");
                // Type: double (degrees Celsius)

                // Pause for the refresh interval
                Thread.sleep(refreshInterval); // Type: int (milliseconds)

            } catch (InterruptedException e) {
                System.err.println("Monitoring interrupted.");
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
