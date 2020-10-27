package com.ytz.thread.juc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: ThreadOrderAccessDemo
 * @Description: 线程精准调用
 * 需求：多线程按顺序调用，实现A->B->C
 * A打印5次 B打印10次  C打印15次   10轮
 * @author: yangtianzeng
 * @date: 2020/4/7 8:49
 */
public class ThreadOrderAccessDemo {
    public static void main(String[] args) {
        final ShareResource shareResource = new ShareResource();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.printA();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.printB();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                shareResource.printC();
            }
        }, "C").start();
    }
}

class ShareResource {
    /**
     * A 1
     * B 2
     * C 3
     */
    private int num = 1;
    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void printA() {
        lock.lock();
        try {
            // 判断
            while (num != 1) {
                condition1.await();
            }

            // 业务
            for (int j = 0; j < 5; j++) {
                System.out.println(Thread.currentThread().getName() + "\t" + j);
            }
            // 修改标志位
            num = 2;
            // 唤醒
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            // 判断
            while (num != 2) {
                condition2.await();
            }

            // 业务
            for (int j = 0; j < 10; j++) {
                System.out.println(Thread.currentThread().getName() + "\t" + j);
            }
            // 修改标志位
            num = 3;
            // 唤醒
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            // 判断
            while (num != 3) {
                condition3.await();
            }

            // 业务
            for (int j = 0; j < 15; j++) {
                System.out.println(Thread.currentThread().getName() + "\t" + j);
            }
            // 修改标志位
            num = 1;
            // 唤醒
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
