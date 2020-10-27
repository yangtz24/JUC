package com.ytz.thread;

/**
 * @ClassName: DeadLockDemo
 * @Description: 死锁
 * 死锁：多个线程同时但以不同的顺序请求同一组锁的时候，线程之间互相循环等待锁导致线程一直阻塞。
 * 比如：如果线程1锁住了A，然后尝试对B进行加锁，同时线程2已经锁住了B，
 * 接着尝试对A进行加锁，这样线程1持有锁A等待锁B，线程2持有锁B等待锁A，就会发生死锁。
 * 死锁可能不止包含2个线程，可以包含多个线程。如线程1等待线程2，线程2等待线程3，线程3等待线程4，线程4等待线程1。
 * 产生死锁的四个必要条件：
 * 1.互斥条件
 * 2.不可剥夺条件
 * 3.请求与保持条件
 * 4.循环等待条件
 * 处理死锁的方法：
 * 预防死锁：通过设置某些限制条件，去破坏产生死锁的四个必要条件中的一个或几个条件，来防止死锁的发生。
 * 避免死锁：在资源的动态分配过程中，用某种方法去防止系统进入不安全状态，从而避免死锁的发生。
 * 检测死锁：允许系统在运行过程中发生死锁，但可设置检测机构及时检测死锁的发生，并采取适当措施加以清除。
 * 解除死锁：当检测出死锁后，便采取适当措施将进程从死锁状态中解脱出来。
 * 解决方法：
 * 1. 按顺序加锁：需要事先知道所有可能会用到的锁，并对这些锁做适当的排序。
 * 规定锁A和锁B的顺序，某个线程需要同时获取锁A和锁B时，必须先拿锁A再拿锁B。
 * 线程1和线程2都先锁A再锁B，不会发生死锁。
 * 2. 加锁时限（超时重试机制）：
 * 设置一个超时时间，在尝试获取锁的过程中若超过了这个时限该线程则放弃对该锁请求，
 * 回退并释放所有已经获得的锁，然后等待一段随机的时间再重试。
 * 问题：
 * 1. 当线程很多时，等待的这一段随机的时间会一样长或者很接近，因此就算出现竞争而导致超时后，
 * 由于超时时间一样，它们又会同时开始重试，导致新一轮的竞争，带来了新的问题。
 * 2. 不能对synchronized同步块设置超时时间。需要创建一个自定义锁，或使用java.util.concurrent包下的工具。
 * 3. 死锁检测
 * 每当一个线程获得了锁，会在线程和锁相关的数据结构中（比如map）将其记下。
 * 当一个线程请求锁失败时，这个线程可以遍历锁的关系图看看是否有死锁发生。
 * 例如：线程1请求锁A，但是锁A这个时候被线程2持有，这时线程1就可以检查一下线程2是否已经请求了线程1当前所持有的锁。
 * 如果线程2确实有这样的请求，那么就是发生了死锁（线程1拥有锁B，请求锁A；线程B拥有锁A，请求锁B）。
 * 检测出死锁时，解决方法：
 * 1.释放所有锁，回退，并且等待一段随机的时间后重试。（类似超时重试机制）
 * 2.给这些线程设置优先级，让一个（或几个）线程回退，剩下的线程就像没发生死锁一样继续保持着它们需要的锁。
 * @author: yangtianzeng
 * @date: 2020/3/28 13:56
 */
public class DeadLockDemo {

    private static Object object1 = new Object();
    private static Object object2 = new Object();

    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                synchronized (object1) {
                    try {
                        System.out.println("t1 Lock");
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (object2) {
                        System.out.println("doSomething.........");
                    }
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                synchronized (object2) {
                    System.out.println("t2 Lock");
                    synchronized (object1) {
                        System.out.println("doSomething..........");
                    }
                }
            }
        }.start();
    }
}
