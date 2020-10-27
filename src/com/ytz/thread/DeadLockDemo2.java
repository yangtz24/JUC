package com.ytz.thread;

/**
 * @ClassName: DeadLockDemo2
 * @Description: . 嵌套管程锁死
 * 问题：
 * 线程1获得A对象的锁。
 * 线程1获得对象B的锁（A对象锁还未释放）。
 * 线程1调用B.wait()，从而释放了B对象上的锁，但仍然持有对象A的锁。
 * 线程2需要同时持有对象A和对象B的锁，才能向线程1发信号B.notify()。
 * 线程2无法获得对象A上的锁，因为对象A上的锁当前正被线程1持有。
 * 线程2一直被阻塞，等待线程1释放对象A上的锁。
 * 线程1一直阻塞，等待线程2的信号，因此不会释放对象A上的锁。
 * @author: yangtianzeng
 * @date: 2020/3/28 14:17
 */
public class DeadLockDemo2 {

    protected Object Object = new Object();
    protected boolean isLocked = false;

    /**
     * 线程1调用lock()方法，Lock对象锁和monitorObject锁，调用monitorObject.wait()阻塞，但仍然持有Lock对象锁
     *
     * @throws InterruptedException
     */
    public void lock() throws InterruptedException {
        synchronized (this) {
            while (isLocked) {
                synchronized (this.Object) {
                    this.Object.wait();
                }
            }
            isLocked = true;
        }
    }

    /**
     * 线程2调用unlock()方法解锁时，无法获取Lock对象锁，因为线程1一直持有Lock锁，造成嵌套管程锁死
     */
    public void unLock() {
        synchronized (this) {
            this.isLocked = false;
            synchronized (this.Object) {
                this.Object.notify();
            }
        }
    }
}
