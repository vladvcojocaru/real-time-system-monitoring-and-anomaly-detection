package com.vlad.metrics.util;

import java.net.InetAddress;
import java.util.UUID;

public class MachineIdProvider {
    public static String getMachineId(){
        try {
            // Use hostname as machine ID (if unique)
            return InetAddress.getLocalHost().getHostName();
        } catch (Exception e){
            // Fallback: generate UUID
            return UUID.randomUUID().toString();
        }
    }
}
