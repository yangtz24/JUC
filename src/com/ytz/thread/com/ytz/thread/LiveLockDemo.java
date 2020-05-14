package com.ytz.thread;

import java.util.concurrent.TimeUnit;

/**
 * @ClassName: LiveLockDemo
 * @Description: 活锁   出现两个线程互相改变对方的结束条件，最后谁也无法结束
 *                  解决方式：将线程的执行时间错开，可用随机数
 * @author: yangtianzeng
 * @date: 2020/5/5 10:10
 */
public class LiveLockDemo {

    static volatile int count = 10;
    static final Object O = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            while (count > 0) {
                try { TimeUnit.MILLISECONDS.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
                count--;
                System.out.println("Count = " + count);
            }
        }, "t1").start();

        new Thread(() -> {
            while (count < 20) {
                try { TimeUnit.MILLISECONDS.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
                count++;
                System.out.println("Count*** = " + count);
            }
        }, "t2").start();
    }
}
