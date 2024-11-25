package com.vlad.metrics;

import oshi.SystemInfo;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.util.Util;

import java.util.List;

public class Testing {
    public static void main(String[] args) {
        SystemInfo systemInfo = new SystemInfo();

        // Continuous monitoring loop
        while (true) {
            List<HWDiskStore> diskStores = systemInfo.getHardware().getDiskStores();

            System.out.println("----- Disk Metrics -----");
            for (HWDiskStore disk : diskStores) {
                // Update disk attributes to get the latest metrics
                disk.updateAttributes();

                // Disk name and model
                System.out.println("Disk: " + disk.getName());
                System.out.println("Model: " + disk.getModel());

                // Read and write metrics
                System.out.printf("Reads: %d bytes (%d operations)\n", disk.getReadBytes(), disk.getReads());
                System.out.printf("Writes: %d bytes (%d operations)\n", disk.getWriteBytes(), disk.getWrites());
                System.out.printf("Current Queue Length: %d\n", disk.getCurrentQueueLength());
                System.out.printf("Transfer Time: %.2f ms\n", disk.getTransferTime() / 1.0e3);

                System.out.println("------------------------");
            }

            // Pause for 2 seconds before fetching the next set of metrics
            Util.sleep(2000);
        }
    }
}