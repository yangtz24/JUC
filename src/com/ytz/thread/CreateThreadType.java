package com.ytz.thread;

/**
 * @ClassName: CreateThreadType
 * @Description: 创建线程的方式
 *                  继承 Thread 类、实现 Runable 接口。
 *                  Thread init()方法
 *                      1.新构造的线程对象是由其 parent 线程来进行空间分配的。
 *                      2.新线程继承了 parent 线程的 group、是否为 Daemon、优先级 priority、加载资源的 contextClassLoader、
 *                          可继承的 ThreadLocal。
 *                      3.parent 线程会分配一个唯一的 ID 来标识这个 child 新线程。
 * @author: yangtianzeng
 * @date: 2020/3/28 14:47
 */
public class CreateThreadType {

    public static void main(String[] args) {
        MyThread myThread = new MyThread();
        //线程 start()方法的含义是：当前线程（即 parent 线程）同步告知 Java 虚拟机，只要线程规划器空闲，应立即启动调用 start()方法的线程。
        //底层源码：通过调用start0()方法启动线程
        myThread.start();//启动线程，最好为线程起一个名字，方便问题排查

        MyThread2 myThread2 = new MyThread2();
        Thread thread = new Thread(myThread2);
        thread.start();
    }


}

//继承 Thread类
class MyThread extends Thread {
    @Override
    public void run() {
        System.out.println("创建线程。。。Thread。。。。");
    }
}

//实现Runnable接口
class MyThread2 implements Runnable{

    @Override
    public void run() {
        System.out.println("创建线程。。。Runnable。。。。");
    }
}
