package com.vlad.metrics;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.util.Util;

public class Testing {
    public static void main(String[] args) {
        SystemInfo systemInfo = new SystemInfo();
        GlobalMemory memory = systemInfo.getHardware().getMemory();

        // Continuous monitoring loop
        while (true) {
            System.out.println("----- Memory Metrics -----");

            // Total and Used Physical Memory
            long totalPhysicalMemory = memory.getTotal();
            long usedPhysicalMemory = totalPhysicalMemory - memory.getAvailable();

            // Total and Used Swap Memory
            long totalSwapMemory = memory.getVirtualMemory().getSwapTotal();
            long usedSwapMemory = memory.getVirtualMemory().getSwapUsed();

            // Virtual Memory (In Use)
            long virtualMemoryUsed = memory.getVirtualMemory().getVirtualInUse();

            // Print Memory Metrics
            System.out.println("----- Memory Metrics -----");
            System.out.printf("Physical Memory - Total: %.2f GB, Used: %.2f GB\n",
                    totalPhysicalMemory / 1e9, usedPhysicalMemory / 1e9);
            System.out.printf("Swap Memory - Total: %.2f GB, Used: %.2f GB\n",
                    totalSwapMemory / 1e9, usedSwapMemory / 1e9);
            System.out.printf("Virtual Memory - In Use: %.2f GB\n",
                    virtualMemoryUsed / 1e9);
            System.out.println("--------------------------");

            // Pause for 2 seconds before the next update
            Util.sleep(2000);
        }
    }
}