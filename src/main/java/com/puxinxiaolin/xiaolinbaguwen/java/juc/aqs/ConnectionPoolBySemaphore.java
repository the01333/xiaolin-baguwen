package com.puxinxiaolin.xiaolinbaguwen.java.juc.aqs;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 基于 Semaphore 实现资源池 
 * @Author: YCcLin
 * @Date: 2025/11/4 22:30
 */
public class ConnectionPoolBySemaphore {
    private final Semaphore semaphore;
    private final LinkedBlockingDeque<Connection> pool;

    public ConnectionPoolBySemaphore(int poolSize) {
        this.semaphore = new Semaphore(poolSize);
        this.pool = new LinkedBlockingDeque<>(poolSize);
        for (int i = 0; i < poolSize; i++) {
            pool.offer(new Connection("Connection-" + i));
        }
    }
    
    public Connection acquireConnection() throws InterruptedException {
        semaphore.acquire();
        return pool.poll();
    }
    
    public void releaseConnection(Connection connection) {
        pool.offer(connection);
        semaphore.release();
    }

    public static void main(String[] args) {
        ConnectionPoolBySemaphore connectionPool = new ConnectionPoolBySemaphore(3);

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                try {
                    Connection connection = connectionPool.acquireConnection();
                    System.out.println(Thread.currentThread().getName() + " acquired " + connection.getName());
                    TimeUnit.SECONDS.sleep(2); // 模拟使用连接
                    connectionPool.releaseConnection(connection);
                    System.out.println(Thread.currentThread().getName() + " released " + connection.getName());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

class Connection {
    private final String name;

    public Connection(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
