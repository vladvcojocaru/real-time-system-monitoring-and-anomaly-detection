package com.vlad.metrics.producer.services;

import com.vlad.metrics.models.DiskMetric;
import java.util.ArrayList;
import java.util.List;
import oshi.SystemInfo;
import oshi.hardware.HWDiskStore;

public class DiskMetricService {

    private final SystemInfo systemInfo;

    public DiskMetricService() {
        systemInfo = new SystemInfo();
    }

    public DiskMetric[] getDiskMetric() {
        ArrayList<DiskMetric> diskMetricList = new ArrayList<>();
        List<HWDiskStore> diskStores = systemInfo.getHardware().getDiskStores();

        for (HWDiskStore disk : diskStores) {
            disk.updateAttributes();
            String diskName = disk.getName();
            long diskReads = disk.getReads();
            long diskWrites = disk.getWrites();
            long diskReadBytes = disk.getReadBytes();
            long diskWriteBytes = disk.getWriteBytes();
            long diskQueueLength = disk.getCurrentQueueLength();
            long diskTransferTime = disk.getTransferTime();

            DiskMetric diskMetric = DiskMetric.newBuilder()
                    .setDiskName(diskName)
                    .setDiskReads(diskReads)
                    .setDiskWrites(diskWrites)
                    .setDiskReadBytes(diskReadBytes)
                    .setDiskWriteBytes(diskWriteBytes)
                    .setDiskQueueLength(diskQueueLength)
                    .setDiskTransferTime(diskTransferTime).build();

            diskMetricList.add(diskMetric);
        }

        return diskMetricList.toArray(new DiskMetric[0]);
    }
}
