package com.ytz.thread;

/**
 * @ClassName: DeadLockDemo3
 * @Description: ReentrantLock 重入锁死
 *                  如果一个线程持有某个对象上的锁，那么它就有权访问所有在该对象上同步的块，这就叫可重入。
 *                      synchronized、ReentrantLock都是可重入锁。
 *                  如果一个线程持有锁A，锁A是不可重入的，该线程再次请求锁A时被阻塞，就是重入锁死。
 *
 *                  饥饿和公平:
 *                      如果一个线程因为CPU时间全部被其他线程抢走而得不到CPU运行时间，这种状态被称之为饥饿。
 *                      导致线程饥饿原因：
 *                          高优先级线程吞噬所有的低优先级线程的CPU时间。
 *                          线程始终竞争不到锁。
 *                          线程调用object.wait()后没有被唤醒。
 *                          解决饥饿的方案被称之为公平性，即所有线程均能公平地获得运行机会。
 *                          饥饿一般不占有资源，死锁进程一定占有资源
 * @author: yangtianzeng
 * @date: 2020/3/28 14:29
 */
public class DeadLockDemo3 {

    /**
     * 如果一个线程两次调用lock()间没有调用unlock()方法，那么第二次调用lock()就会被阻塞，这就出现了重入锁死。
     */

    private boolean isLocked = false;

    public synchronized void lock() throws InterruptedException {
        while (isLocked) {
            wait();
        }
        isLocked = true;
    }

    public synchronized void unLock() {
        isLocked = false;
        notify();
    }
}
