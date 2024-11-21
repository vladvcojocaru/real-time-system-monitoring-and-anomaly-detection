package test;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;

public class MainTest {
    public static void main(String[] args) throws InterruptedException {
        SystemInfo systemInfo = new SystemInfo();
        CentralProcessor processor = systemInfo.getHardware().getProcessor();

        System.out.println("Monitoring CPU usage... Press Ctrl+C to exit.");

        // Initialize ticks to calculate usage
        long[] prevTicks = processor.getSystemCpuLoadTicks();

        while (true){
            Thread.sleep(1000);

            /*
            USER: Time spent executing user processes.
            NICE: Time spent running low-priority processes.
            SYSTEM: Time spent executing kernel processes.
            IDLE: Time when the CPU was idle.
            IOWAIT: Time spent waiting for I/O operations.
            IRQ: Time handling hardware interrupts.
            SOFTIRQ: Time handling software interrupts.
            STEAL: Time stolen by other operating systems in a virtualized environment.
             */
            long[] ticks = processor.getSystemCpuLoadTicks();

            long user = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
            long nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
            long system = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
            long idle = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
            long iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
            long irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
            long softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
            long steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];

            long totalCpu = user + nice + system + idle + iowait + irq + softirq + steal;
            double cpuUsage = 100.0 * (totalCpu - idle) / totalCpu;
            System.out.printf("CPU Usage: %.2f%%\n",cpuUsage);

            prevTicks = ticks;
        }

    }


}
