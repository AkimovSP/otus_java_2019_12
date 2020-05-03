package ru.otus.jmm;

import java.util.concurrent.TimeUnit;

public class AscendingDescending  {
    private int currentThreadNumber;
    private final Object monitor = new Object();

    public static void main(String[] args) throws InterruptedException {
        AscendingDescending asc = new AscendingDescending();
        asc.go();
    }

    private void go() throws InterruptedException {
        Thread thread1 = new MyThread(1, 1);
        Thread thread2 = new MyThread(3, 0);

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();
    }

    public class MyThread extends Thread {

        private final int sleepTime;
        private final int threadNumber;

        MyThread(int sleepTime, int threadNumber) {
            this.sleepTime = sleepTime;
            this.threadNumber = threadNumber;
        }

        @Override
        public void run() {
            inc(sleepTime, threadNumber);
        }

        private void inc(int sleepTime, int threadNumber) {
            int i = 0;
            boolean asc = true;
            while (true) {
                synchronized (monitor) {
                    if (currentThreadNumber % 2 == threadNumber) {
                        currentThreadNumber++;

                        if (asc) {
                            i++;
                            if (i == 10) {
                                asc = false;
                            }
                        } else {
                            i--;
                            if (i == 1) {
                                asc = true;
                            }
                        }
                        System.out.println(Thread.currentThread().getName() + ", currentThreadNumber=" + currentThreadNumber + ", sleepTime=" + sleepTime+", threadNumber = " + threadNumber + " :: i=" + i);
                        sleep(sleepTime);
                    }
                }
            }
        }

        private void sleep(int sleepTime) {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(sleepTime));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}