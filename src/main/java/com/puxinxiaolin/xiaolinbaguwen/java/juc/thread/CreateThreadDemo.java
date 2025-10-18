package com.puxinxiaolin.xiaolinbaguwen.java.juc.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class CreateThreadDemo {
    static class CallableDemo implements Callable<String> {
        @Override
        public String call() throws Exception {
            return "Callable";
        }
    }
    
    static class RunnableDemo implements Runnable {
        @Override
        public void run() {
            System.out.println("Runnable");
        }
    }

    public static void main(String[] args) {
        CallableDemo cd = new CallableDemo();
        FutureTask<String> futureTask = new FutureTask<>(cd);
        try {
            System.out.println(futureTask.get());
        } catch (Exception e) {
            e.printStackTrace();
        }

        RunnableDemo rd = new RunnableDemo();
        Thread rt = new Thread(rd);
        rt.start();
    }
    
}
