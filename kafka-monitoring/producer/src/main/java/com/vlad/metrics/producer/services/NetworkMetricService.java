package com.vlad.metrics.producer.services;

import com.vlad.metrics.models.NetworkMetric;
import oshi.SystemInfo;
import oshi.hardware.NetworkIF;

import java.util.ArrayList;
import java.util.Arrays;
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

            NetworkMetric networkMetric = NetworkMetric.newBuilder()
                            .setName(name)
                    .setDisplayName(displayName)
                    .setBytesSent(bytesSent)
                    .setBytesRecv(bytesRecv)
                    .setPacketsSent(packetsSent)
                    .setPacketsRecv(packetsRecv)
                    .addAllIpv4Addresses(Arrays.asList(ipv4Addresses))
                    .addAllIpv6Addresses(Arrays.asList(ipv6Addresses))
                    .setMacAddress(macAddress)
                    .build();
            networkMetricArrayList.add(networkMetric);
        }

        return networkMetricArrayList.toArray(new NetworkMetric[0]);

    }
}
