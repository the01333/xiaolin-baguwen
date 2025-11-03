package com.puxinxiaolin.xiaolinbaguwen.java.juc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Description: ABA - Demo
 * @Author: YCcLin
 * @Date: 2025/11/3 15:58
 */
public class ABDDemo {
    private static AtomicStampedReference<Integer> reference = new AtomicStampedReference<>(100, 0);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            int[] stampedHolder = new int[1];
            Integer value = reference.get(stampedHolder);
            System.out.println("---t1 initial value: " + value);

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            boolean isSuccess = reference.compareAndSet(value, value + 1, stampedHolder[0], stampedHolder[0] + 1);
            System.out.println("---t1 update value isSuccess: " + isSuccess);
        });
        t1.start();

        Thread t2 = new Thread(() -> {
            int[] stampedHolder = new int[1];
            Integer value = reference.get(stampedHolder);
            System.out.println("---t2 initial value: " + value);
            // 101 1
            reference.compareAndSet(value, value + 1, stampedHolder[0], stampedHolder[0] + 1);
            // 100 2
            reference.compareAndSet(value + 1, value, stampedHolder[0] + 1, stampedHolder[0] + 2);
        });
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int[] stampedHolder = new int[1];
        System.out.println("main thread - value: " + reference.get(stampedHolder) + " , stamped: " + reference.getStamp());
    }
}
