package com.hyperface.vt;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Test2 {
    public static void main(String[] args) throws InterruptedException {
        // Measure runtime for platform threads
        System.out.println("=== Platform Threads ===");
        long startPlatform = System.currentTimeMillis();

        Random random = new Random();
        Runnable runnable = () -> {
            double i = random.nextDouble(1000) % random.nextDouble(1000);
        };
        for (int i = 0; i < 50000; i++) {
            Thread t = new Thread(runnable);
            t.start();
        }

        long finishPlatform = System.currentTimeMillis();
        long timeElapsedPlatform = finishPlatform - startPlatform;
        System.out.println("Platform threads run time: " + timeElapsedPlatform + " ms");

        // Measure runtime for virtual threads
        System.out.println("\n=== Virtual Threads ===");
        long startVirtual = System.currentTimeMillis();

        Set<String> poolNames = ConcurrentHashMap.newKeySet();
        Set<String> pThreadNames = ConcurrentHashMap.newKeySet();

        Runnable virtualRunnable = () -> {
            double i = random.nextDouble(1000) % random.nextDouble(1000);
            String poolName = readPoolName();
            poolNames.add(poolName);
            String workerName = readWorkerName();
            pThreadNames.add(workerName);
        };

        Thread[] virtualThreads = new Thread[50000];
        for (int i = 0; i < 50000; i++) {
            virtualThreads[i] = Thread.startVirtualThread(virtualRunnable);
        }

        for (Thread thread : virtualThreads) {
            thread.join();
        }

        long finishVirtual = System.currentTimeMillis();
        long timeElapsedVirtual = finishVirtual - startVirtual;
        System.out.println("Virtual threads run time: " + timeElapsedVirtual + " ms");
        System.out.println("Pools = " + poolNames.size());
        System.out.println("Platform threads = " + pThreadNames.size());
    }

    private static String readPoolName() {
        String name = Thread.currentThread().toString();
        int i1 = name.indexOf("@ForkJoinPool");
        int i2 = name.indexOf("worker", i1);
        return name.substring(i1, i2);
    }

    private static String readWorkerName() {
        String name = Thread.currentThread().toString();
        int i1 = name.indexOf("worker");
        return name.substring(i1);
    }
}
