package com.vlad.metrics.producer.services;

import com.vlad.metrics.models.NetworkMetric;
import oshi.SystemInfo;
import oshi.hardware.NetworkIF;

import java.util.ArrayList;
import java.util.List;

public class NetworkMetricService {
    private final SystemInfo systemInfo;

    public NetworkMetricService() {
        systemInfo = new SystemInfo();
    }

    public NetworkMetric[] getNetworkMetric(){
        List<NetworkIF> networkIFs = systemInfo.getHardware().getNetworkIFs();
        ArrayList<NetworkMetric> networkMetricArrayList = new ArrayList<>();

        for(NetworkIF networkIF: networkIFs){
            networkIF.updateAttributes();

            String name = networkIF.getName();
            String displayName = networkIF.getDisplayName();
            long bytesSent = networkIF.getBytesSent();
            long bytesRecv = networkIF.getBytesRecv();
            long packetsSent = networkIF.getPacketsSent();
            long packetsRecv = networkIF.getPacketsRecv();
            String[] ipv4Addresses = networkIF.getIPv4addr();
            String[] ipv6Addresses = networkIF.getIPv6addr();
            String macAddress = networkIF.getMacaddr();

            networkMetricArrayList.add(new NetworkMetric(name, displayName, bytesSent, bytesRecv, packetsSent, packetsRecv, ipv4Addresses, ipv6Addresses, macAddress));
        }

        return networkMetricArrayList.toArray(new NetworkMetric[0]);

    }
}
