package com.ytz.thread.juc;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName: SemaporeDemo
 * @Description: Semaphore 是一个计数信号量，必须由获取它的线程释放。
 * 常用于限制可以访问某些资源的线程数量，例如通过 Semaphore 限流。
 * Semaphore 只有3个操作：
 * 初始化
 * 增加
 * 减少
 * @author: yangtianzeng
 * @date: 2020/4/7 11:12
 */
public class SemaphoreDemo {

    public static void main(String[] args) {
        // 模拟抢车位
        final Semaphore semaphore = new Semaphore(3);

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                try {
                    // 获取许可 获取成功，信号量 -1 否则 等待其他线程释放信号量，或超时
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + "\t 抢到车位");
                    try {
                        TimeUnit.MILLISECONDS.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    // 释放  将信号量 +1 唤醒等待线程
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
