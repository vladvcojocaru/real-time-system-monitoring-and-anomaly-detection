package com.vlad.metrics;

import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

import java.util.List;

public class Testing {
    public static void main(String[] args) {
        // Initialize OSHI SystemInfo
        SystemInfo systemInfo = new SystemInfo();

        // Monitor network metrics in a loop
        while (true) {
            List<NetworkIF> networkIFs = systemInfo.getHardware().getNetworkIFs();
            System.out.println("===== Network Metrics =====");

            for (NetworkIF net : networkIFs) {
                net.updateAttributes(); // Update metrics for this interface

                // Collecting network metrics
                String name = net.getName();
                String displayName = net.getDisplayName();
                long bytesSent = net.getBytesSent();
                long bytesRecv = net.getBytesRecv();
                long packetsSent = net.getPacketsSent();
                long packetsRecv = net.getPacketsRecv();
                long speed = net.getSpeed();
                // boolean isUp = net.isKnownOperational();
                String[] ipv4Addresses = net.getIPv4addr();
                String[] ipv6Addresses = net.getIPv6addr();
                String macAddress = net.getMacaddr();

                // Display metrics
                System.out.println("Interface: " + name + " (" + displayName + ")");
                // System.out.println("  Status: " + (isUp ? "Up" : "Down"));
                System.out.println("  MAC Address: " + macAddress);
                System.out.println("  IPv4 Addresses: " + String.join(", ", ipv4Addresses));
                System.out.println("  IPv6 Addresses: " + String.join(", ", ipv6Addresses));
                System.out.println("  Bytes Sent: " + bytesSent);
                System.out.println("  Bytes Received: " + bytesRecv);
                System.out.println("  Packets Sent: " + packetsSent);
                System.out.println("  Packets Received: " + packetsRecv);
                System.out.println("  Speed: " + (speed > 0 ? speed / 1_000_000 + " Mbps" : "Unknown"));

                System.out.println("-------------------------------------");
            }

            try {
                // Wait for a specific interval (e.g., 5 seconds) before next update
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                System.out.println("Monitoring interrupted.");
                break;
            }
        }
    }
}
