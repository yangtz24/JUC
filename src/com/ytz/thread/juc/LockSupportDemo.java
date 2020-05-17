package com.ytz.thread.juc;

import java.util.concurrent.locks.LockSupport;

/**
 * @ClassName: LockSupport
 * @Description:  LockSupport使用
 *                  原理：
 *                      每个线程都会与一个许可关联，这个许可对应一个 Parker 的实例，Parker 有一个 int 类型的属性_count。
 *                      park()方法：
 *                          将_count 变为 0
 *                          如果原_count==0，将线程阻塞
 *                      unpark()方法：
 *                          将_count 变为 1
 *                          如果原_count==0，将线程唤醒
 * @author: yangtianzeng
 * @date: 2020/3/29 10:14
 */
public class LockSupportDemo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> {
            //阻塞
            LockSupport.park();
            System.out.println("thread线程被唤醒............");
        });
        thread.start();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //唤醒
        LockSupport.unpark(thread);
    }
}
