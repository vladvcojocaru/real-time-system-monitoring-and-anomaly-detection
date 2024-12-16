package com.vlad.metrics.models;

import com.google.gson.Gson;
import java.util.Arrays;

public class NetworkMetric {

    private String name;
    private String displayName;
    private long bytesSent;
    private long bytesRecv;
    private long packetsSent;
    private long packetsRecv;
    private String[] ipv4Addresses;
    private String[] ipv6Addresses;
    private String macAddress;

    public NetworkMetric(
        String name,
        String displayName,
        long bytesSent,
        long bytesRecv,
        long packetsSent,
        long packetsRecv,
        String[] ipv4Addresses,
        String[] ipv6Addresses,
        String macAddress
    ) {
        this.name = name;
        this.displayName = displayName;
        this.bytesSent = bytesSent;
        this.bytesRecv = bytesRecv;
        this.packetsSent = packetsSent;
        this.packetsRecv = packetsRecv;
        this.ipv4Addresses = ipv4Addresses;
        this.ipv6Addresses = ipv6Addresses;
        this.macAddress = macAddress;
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public long getBytesSent() {
        return bytesSent;
    }

    public long getBytesRecv() {
        return bytesRecv;
    }

    public long getPacketsSent() {
        return packetsSent;
    }

    public long getPacketsRecv() {
        return packetsRecv;
    }

    public String[] getIpv4Addresses() {
        return ipv4Addresses;
    }

    public String[] getIpv6Addresses() {
        return ipv6Addresses;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String toJson() {
        return new Gson()
            .toJson(
                new NetworkMetric(
                    name,
                    displayName,
                    bytesSent,
                    bytesRecv,
                    packetsSent,
                    packetsRecv,
                    ipv4Addresses,
                    ipv6Addresses,
                    macAddress
                )
            );
    }

    @Override
    public String toString() {
        return (
            "NetworkMetric{" +
            "name='" +
            name +
            '\'' +
            ", displayName='" +
            displayName +
            '\'' +
            ", bytesSent=" +
            bytesSent +
            ", bytesRecv=" +
            bytesRecv +
            ", packetsSent=" +
            packetsSent +
            ", packetsRecv=" +
            packetsRecv +
            ", ipv4Addresses=" +
            Arrays.toString(ipv4Addresses) +
            ", ipv6Addresses=" +
            Arrays.toString(ipv6Addresses) +
            ", macAddress='" +
            macAddress +
            '\'' +
            '}'
        );
    }
}
