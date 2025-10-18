package com.puxinxiaolin.xiaolinbaguwen.java.juc.threadPool;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @Description: 自定义拒绝策略
 * @Author: YCcLin
 * @Date: 2025/10/18 18:31
 */
public class CustomRejectedExecutionHandlerDemo implements RejectedExecutionHandler {

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
        // 自定义拒绝策略
        System.out.println("该任务被拒绝: " + r);
    }

}
