package com.vlad.metrics.models;

import com.google.gson.Gson;

public class OsMetric {
    private String os_name;
    private long uptime;
    private int processes;
    private int threads;

    public OsMetric(String os_name, long uptime, int processes, int threads) {
        this.os_name = os_name;
        this.uptime = uptime;
        this.processes = processes;
        this.threads = threads;
    }

    public String getOs_name() {
        return os_name;
    }

    public long getUptime() {
        return uptime;
    }

    public int getProcesses() {
        return processes;
    }

    public int getThreads() {
        return threads;
    }

    public String toJson(){
        return new Gson().toJson(new OsMetric(os_name, uptime, processes, threads));
    }

    @Override
    public String toString() {
        return "OsMetric{" +
                "os_name='" + os_name + '\'' +
                ", uptime=" + uptime +
                ", processes=" + processes +
                ", threads=" + threads +
                '}';
    }
}
