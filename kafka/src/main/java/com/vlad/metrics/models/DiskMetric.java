package com.vlad.metrics.models;

import com.google.gson.Gson;

public class DiskMetric {
    String diskName;
    long diskReads;
    long diskWrites;
    long diskReadBytes;
    long diskWriteBytes;
    long diskQueueLength;
    long diskTransferTime;

    public DiskMetric(String diskName, long diskReads, long diskWrites, long diskReadBytes, long diskWriteBytes, long diskQueueLength, long diskTransferTime) {
        this.diskName = diskName;
        this.diskReads = diskReads;
        this.diskWrites = diskWrites;
        this.diskReadBytes = diskReadBytes;
        this.diskWriteBytes = diskWriteBytes;
        this.diskQueueLength = diskQueueLength;
        this.diskTransferTime = diskTransferTime;
    }

    public String getDiskName() {
        return diskName;
    }

    public long getDiskReads() {
        return diskReads;
    }

    public long getDiskWrites() {
        return diskWrites;
    }

    public long getDiskQueueLength() {
        return diskQueueLength;
    }

    public long getDiskTransferTime() {
        return diskTransferTime;
    }

    public long getDiskReadBytes() {
        return diskReadBytes;
    }

    public long getDiskWriteBytes() {
        return diskWriteBytes;
    }

    public String toJson(){
        return new Gson().toJson(new DiskMetric(diskName, diskReads, diskWrites, diskReadBytes, diskWriteBytes, diskQueueLength, diskTransferTime));
    }


    @Override
    public String toString() {
        return "DiskMetric{" +
                "diskName='" + diskName + '\'' +
                ", diskReads=" + diskReads +
                ", diskWrites=" + diskWrites +
                ", diskReadBytes=" + diskReadBytes +
                ", diskWriteBytes=" + diskWriteBytes +
                ", diskQueueLength=" + diskQueueLength +
                ", diskTransferTime=" + diskTransferTime +
                '}';
    }
}
