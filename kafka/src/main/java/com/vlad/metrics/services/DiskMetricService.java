package com.vlad.metrics.services;

import com.vlad.metrics.models.DiskMetric;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;

import java.util.ArrayList;
import java.util.List;

public class DiskMetricService {
    private final SystemInfo systemInfo;

    public DiskMetricService() {
        systemInfo = new SystemInfo();
    }

    public DiskMetric[] getDiskMetric(){
        ArrayList<DiskMetric> diskMetricList = new ArrayList<>();
        List<HWDiskStore> diskStores = systemInfo.getHardware().getDiskStores();

        for(HWDiskStore disk: diskStores){
            String diskName = disk.getName();
            long diskReads = disk.getReads();
            long diskWrites = disk.getWrites();
            long diskReadBytes = disk.getReadBytes();
            long diskWriteBytes = disk.getWriteBytes();
            long diskQueueLength = disk.getCurrentQueueLength();
            long diskTransferTime = disk.getTransferTime();

            diskMetricList.add(new DiskMetric(diskName, diskReads, diskWrites, diskReadBytes, diskWriteBytes, diskQueueLength, diskTransferTime));
        }

        return diskMetricList.toArray(new DiskMetric[0]);
    }
}
