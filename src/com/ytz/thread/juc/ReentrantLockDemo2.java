package com.ytz.thread.juc;


import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: ReentrantLockDemo2
 * @Description: 线程安全问题
 *                  ReentrantLock用内部类Sync来管理锁，所以真正的获取锁和释放锁是由Sync的实现类来控制的。
 *                  Sync有两个实现，分别为NonfairSync（非公平锁）和FairSync（公平锁）
 *                      abstract static class Sync extends AbstractQueuedSynchronizer
 *                  最终获取到锁的标志就是sync.state>0且sync.exclusiveOwnerThread==当前线程。
 *                  判断锁的状态也是通过sync.state的值和sync.exclusiveOwnerThread来判断。
 *                  //获取锁
 *                  AbstractQueuedSynchronizer.hasQueuedPredecessors()：判断AQS同步队列中是否还有其他线程在等锁
 *                      返回true表示当前线程不能抢锁，需要到同步队列中排队；返回false表示当前线程可以去抢锁
 *                          1.队列为空不需要排队， head==tail，直接返回false
 *                          2.head后继节点的线程是当前线程，就算排队也轮到当前线程去抢锁了，返回false
 *                          3.其他情况都返回true，不允许抢锁
 *                  //释放锁
 *                  AbstractQueuedSynchronizer.release(int)-->ReentrantLock.Sync.tryRelease(int)：
 *                      释放重入锁。只有锁彻底释放，其他线程可以来竞争锁才返回true
 *                      锁可以重入，state记录锁的重入次数，所以state可以大于1
 *                      每执行一次tryRelease()将state减1，直到state==0，表示当前线程彻底把锁释放
 * @author: yangtianzeng
 * @date: 2020/3/29 17:24
 */
public class ReentrantLockDemo2 {

    private Lock lock = new ReentrantLock();

    private int number = 0;

    public void increaseNumber() {
        lock.lock();
        try {
            number++;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        final ReentrantLockDemo2 lockDemo2 = new ReentrantLockDemo2();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    lockDemo2.increaseNumber();
                }
            }).start();
        }

        while (Thread.activeCount() > 1) {
            //保证前面线程执行完毕
            Thread.yield();
        }
        //加锁前：结果小于 10000
        //加锁后：保证每次都等于 10000
        System.out.println(lockDemo2.number);
    }
}
