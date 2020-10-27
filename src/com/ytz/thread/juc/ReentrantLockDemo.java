package com.ytz.thread.juc;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: ReentrantLockDemo
 * @Description: 可重入锁
 * 1. synchronized同步块执行完成或者遇到异常是锁会自动释放，而lock必须调用unlock()方法释放锁。
 * 2. 为了保证在获取到锁之后，最终能够被释放，在finally块中释放锁。
 * @author: yangtianzeng
 * @date: 2020/3/29 10:24
 */
public class ReentrantLockDemo {
    private Lock lock = new ReentrantLock();

    private void createOrder() {
        //获取锁
        lock.lock();
        try {
            //doSomething()
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //释放锁
            lock.unlock();
        }
    }
}
