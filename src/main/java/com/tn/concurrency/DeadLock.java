package com.tn.concurrency;

import lombok.SneakyThrows;

public class DeadLock {

    @SneakyThrows
    public static void main(String[] args) {
        Thread thread1 = new Thread(DeadLock::processA);
        Thread thread2 = new Thread(DeadLock::processB);

        Thread thread3 = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Thread1 state is: " + thread1.getState());
                System.out.println("Thread2 state is: " + thread2.getState());
            }
        });

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
    }

    @SneakyThrows
    private static synchronized void processA() {
        System.out.println("processing..");
        Thread.sleep(10000);
        processB();
    }

    @SneakyThrows
    private static synchronized void processB() {
        System.out.println("processing..");
        Thread.sleep(10000);
        processA();
    }
}
