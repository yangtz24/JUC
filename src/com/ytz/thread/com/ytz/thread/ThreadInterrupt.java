package com.ytz.thread;

/**
 * @ClassName: ThreadInterrupt
 * @Description: 线程中断
 *                  中断代表线程状态，每个线程都关联了一个中断状态，用 boolean 值表示，初始值为 false。
 *                  中断一个线程，其实就是设置了这个线程的中断状态 boolean 值为 true。
 *                  中断只是一个状态，处于中断状态的线程不一定要停止运行。
 *                  // 设置一个线程的中断状态为true
 *                      public void interrupt() {}
 *                  // 检测线程中断状态，处于中断状态返回true
 *                      public boolean isInterrupted() {}
 *                  // 静态方法，检测调用这个方法的线程是否已经中断，处于中断状态返回true
 *                  // 注意：这个方法返回中断状态的同时，会将此线程的中断状态重置为false
 *                      public static boolean interrupted() {}
 *
 *               自动感知中断
 *                  以下方法会自动感知中断：
 *                      Object 类的 wait()、wait(long)、wait(long, int)
 *                      Thread 类的 join()、join(long)、join(long, int)、sleep(long)、sleep(long, int)
 *                      当一个线程处于 sleep、wait、join 这三种状态之一时，如果此时线程中断状态为 true，
 *                          那么就会抛出一个 InterruptedException 的异常，并将中断状态重新设置为 false。
 * @author: yangtianzeng
 * @date: 2020/3/28 21:19
 */
public class ThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {
        MyThread4 thread = new MyThread4();
        thread.start();
        Thread.sleep(3000);
        thread.interrupt();
    }
}

    class MyThread4 extends Thread {
        int i = 0;

        @Override
        public void run() {
            while (true) {
                System.out.println(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("中断异常被捕获了");
                    return;
                }
                i++;
            }
        }
    }
