package com.puxinxiaolin.xiaolinbaguwen.java.juc.thread;

import java.util.concurrent.TimeUnit;

public class StopThreadByInterrupt implements Runnable {
    
    public void run() {
        // 判断是否有中断信号
        while (!Thread.currentThread().isInterrupted()) {
            System.out.println("Thread is running...");
            try {
                // 如果在睡眠的时候被打断, 打断标记会被清除（这里的打断标记 = false）
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                // 重新设置打断标记（设置打断标记 = true）
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
    
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopThreadByInterrupt());
        thread.start();

        TimeUnit.SECONDS.sleep(5);
        
        thread.interrupt();
    }
}
