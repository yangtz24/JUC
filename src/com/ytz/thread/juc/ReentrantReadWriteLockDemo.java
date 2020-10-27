package com.ytz.thread.juc;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName: ReentrantReadWriteLockDemo
 * @Description: 读写锁
 * 读写锁允许共享资源在同一时刻可以被多个读线程访问，但是在写线程访问时，所有的读线程和其他的写线程都会被阻塞。
 * @author: yangtianzeng
 * @date: 2020/4/5 10:27
 */
public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        final Data data = new Data();

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                while (true) {
                    data.get();
                }
            }, "读线程---" + i).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                while (true) {
                    data.put(new Random().nextInt(10000));
                }
            }, "写线程---" + i).start();
        }
    }
}

class Data {
    /**
     * 共享数据，只能有一个线程能写该数据，但可以有多个线程同时读该数据。
     */
    private Object data;
    /**
     * 读写锁 加锁
     */
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 写入数据
     *
     * @param data
     */
    public void put(Object data) {
        // 加上写锁，不允许其他线程读也不允许写
        lock.writeLock().lock();
        System.out.println(Thread.currentThread().getName() + "开始写入数据");

        try {
            TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 1000));
            this.data = data;
            System.out.println(Thread.currentThread().getName() + "写入数据操作完成");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //释放写锁
            lock.writeLock().unlock();
        }
    }

    /**
     * 读取数据
     */
    public void get() {
        // 加 读锁 其他线程只能读不能写
        lock.readLock().lock();
        System.out.println(Thread.currentThread().getName() + "开始读取数据");

        try {
            TimeUnit.MILLISECONDS.sleep((long) (Math.random() * 1000));
            System.out.println(Thread.currentThread().getName() + "读取数据操作完成:   " + data);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            // 释放读锁
            lock.readLock().unlock();
        }
    }
}
