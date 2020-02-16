package ru.otus.l011;

import com.sun.management.GarbageCollectionNotificationInfo;

import javax.management.MBeanServer;
import javax.management.NotificationEmitter;
import javax.management.NotificationListener;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

//-Xms512m -Xmx512m
// -Xlog:gc=debug:file=C:\Users\Serg\tmp\gc-%p-%t.log:tags,uptime,time,level:filecount=5,filesize=10m
// -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=C:\Users\Serg\tmp\dump
// -XX:+UseSerialGC -XX:MaxGCPauseMillis=10

public class Main {
    private static int numberYoungGC = 0;
    private static int numberOldGC = 0;
    private static int timeYoungGC = 0;
    private static int timeOldGC = 0;
    private static int timeTotalGC = 0;

    public static void main(String... args) throws Exception {
        int j;
        int maxsize = 5_000_001;
        int numberOfIterations = 20;

        System.out.println("Starting pid: " + ManagementFactory.getRuntimeMXBean().getName());
        switchOnMonitoring();
        long beginTime = System.currentTimeMillis();

        for (int l = 0; l < numberOfIterations; l++) {

            var a = new ArrayList<String>();

            for (int i = 0; i < maxsize; i++) {
                a.add(String.valueOf("AAA fff AAA fff sss DDD FFF DDD jjj IIIG DDDS ffffffffffffff dassssssssssss"));
                j = a.size() / 2;
                a.set(j, a.get(j).replace("f", "F"));
                if (i % 250_000 == 1) {
                    System.out.println("I=" + i);
                }
            }
            System.out.println("L=" + l);
        }
        System.out.println("time:" + (System.currentTimeMillis() - beginTime) / 1000);
        System.out.println("Number of Young GC = " + numberYoungGC);
        System.out.println("Duration of Young GC = " + timeYoungGC);
        System.out.println("Number of Old GC = " + numberOldGC);
        System.out.println("Duration of Old GC = " + timeOldGC);
        System.out.println("Total duration of GC = " + timeTotalGC);
    }

    private static void switchOnMonitoring() {
        List<GarbageCollectorMXBean> gcbeans = java.lang.management.ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gcbean : gcbeans) {
            System.out.println("GC name:" + gcbean.getName());
            NotificationEmitter emitter = (NotificationEmitter) gcbean;
            NotificationListener listener = (notification, handback) -> {
                if (notification.getType().equals(GarbageCollectionNotificationInfo.GARBAGE_COLLECTION_NOTIFICATION)) {
                    GarbageCollectionNotificationInfo info = GarbageCollectionNotificationInfo.from((CompositeData) notification.getUserData());
                    String gcName = info.getGcName();
                    String gcAction = info.getGcAction();
                    String gcCause = info.getGcCause();

                    long startTime = info.getGcInfo().getStartTime();
                    long duration = info.getGcInfo().getDuration();

                    if (gcAction.contains("major")) {
                        numberOldGC++;
                        timeOldGC += duration;
                    }

                    if (gcAction.contains("minor")) {
                        numberYoungGC++;
                        timeYoungGC += duration;
                    }
                    timeTotalGC += duration;
                }
            };
            emitter.addNotificationListener(listener, null, null);
        }
    }
}
