package com.puxinxiaolin.xiaolinbaguwen.java.juc.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Description: 根据 AQS 实现独占锁
 * @Author: YCcLin
 * @Date: 2025/11/3 16:36
 */
public class SimpleExclusiveLockDemo {
    
    private final Sync sync = new Sync();

    private static class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected boolean tryAcquire(int arg) {
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }

            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            if (getState() == 0) {
                throw new IllegalMonitorStateException();
            }

            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }
    }

    public void lock() {
        sync.acquire(1);
    }

    public void unLock() {
        sync.release(1);
    }

    /**
     * 检查当前锁是否被持有
     *
     * @return
     */
    public boolean isLocked() {
        return sync.isHeldExclusively();
    }
    
}