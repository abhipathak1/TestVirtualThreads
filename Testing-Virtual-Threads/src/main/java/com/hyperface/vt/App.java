package com.hyperface.vt;

import java.util.Random;

public class App {
    public static void main(String[] args) {
        // Measure runtime for platform threads
        System.out.println("=== Platform Threads ===");
        long startPlatform = System.currentTimeMillis();

        Random random = new Random();
        Runnable runnable = () -> { double i = random.nextDouble(1000) % random.nextDouble(1000); };
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

        for (int i = 0; i < 50000; i++) {
            Thread.startVirtualThread(runnable);
        }

        long finishVirtual = System.currentTimeMillis();
        long timeElapsedVirtual = finishVirtual - startVirtual;
        System.out.println("Virtual threads run time: " + timeElapsedVirtual + " ms");
    }
}
