package com.ytz.thread.juc;

import java.util.concurrent.CountDownLatch;

/**
 * @ClassName: CountDownLatchDemo
 * @Description: CountDownLatch: 倒计时计数器
 * 使用场景：某个线程需要等待一个或多个线程操作结束（或达到某种状态）才开始执行。
 * @author: yangtianzeng
 * @date: 2020/4/7 10:23
 */
public class CountDownLatchDemo {

    public static final int AMOUNT = 8;

    public static void main(String[] args) throws InterruptedException {
        final CountDownLatch countDownLatch = new CountDownLatch(AMOUNT);
        for (int i = 0; i < AMOUNT; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName() + "\t下班");
                // 将计数器减1，当计数到达0时，则所有等待者或单个等待者开始执行。
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }
        // 等待阻塞状态
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + "\t关门");

    }
}
