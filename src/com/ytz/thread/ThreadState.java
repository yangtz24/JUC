package com.ytz.thread;

/**
 * @ClassName: ThreadState
 * @Description: 线程运行状态
 * @author: yangtianzeng
 * @date: 2020/3/28 19:27
 */
public class ThreadState {
    public static void main(String[] args) {
        new Thread(new RunThread(), "RunThread").start();// 一直处于RUNNABLE线程
        new Thread(new TimeWaiting(), "TimeWaitingThread").start();// TIMED_WAITING线程
        new Thread(new Waiting(), "WaitingThread").start();// WAITING线程
        new Thread(new Blocked(), "BlockedThread-1").start();// 获取锁之后TIMED_WAITING
        new Thread(new Blocked(), "BlockedThread-2").start();// 获取不到锁，被阻塞BLOCKED
    }

    // 该线程一直运行
    static class RunThread implements Runnable {
        @Override
        public void run() {
            while (true) {
                System.out.println("always running .......");
            }
        }
    }

    // 该线程不断地进行睡眠 TIMED_WAITING
    static class TimeWaiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 该线程在Waiting.class实例上等待 WAITING
    static class Waiting implements Runnable {
        @Override
        public void run() {
            while (true) {
                synchronized (Waiting.class) {
                    try {
                        Waiting.class.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    // 该线程在Blocked.class实例上加锁后，不会释放该锁
    static class Blocked implements Runnable {
        @Override
        public void run() {
            synchronized (Blocked.class) {
                while (true) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
