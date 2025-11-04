package com.puxinxiaolin.xiaolinbaguwen.java.juc.thread;

import java.util.concurrent.TimeUnit;

public class StopThreadByFlag implements Runnable {
    
    private volatile boolean isStop = false;
    
    public void run() {
        while (!isStop) {
            System.out.println("Thread is running...");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
    
    public void stop() {
        isStop = true;
    }

    public static void main(String[] args) throws InterruptedException {
        StopThreadByFlag std = new StopThreadByFlag();
        Thread thread = new Thread(std);
        thread.start();

        TimeUnit.SECONDS.sleep(5);
        
        std.stop(); 
    }
}
