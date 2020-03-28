package com.ytz.thread;

/**
 * @ClassName: ThreadJoin
 * @Description: 线程加入
 *                  当一个线程必须等待另一个线程执行时，就用到 join。
 *                  // 当前线程加入该线程后面，等待该线程终止。
 *                      void join()
 *                  // 当前线程等待该线程终止的时间最长为 millis 毫秒。如果在millis时间内，该线程没有执行完，那么当前线程进入就绪状态，重新等待cpu调度
 *                      void join(long millis)
 *                  // 等待该线程终止的时间最长为 millis 毫秒 + nanos 纳秒。如果在millis时间内，该线程没有执行完，那么当前线程进入就绪状态，重新等待cpu调度
 *                      void join(long millis,int nanos)
 * @author: yangtianzeng
 * @date: 2020/3/28 21:47
 */
public class ThreadJoin {

    public static void main(String[] args) throws InterruptedException {
        MyThread5 t = new MyThread5();
        t.start();
        // 将主线程加入到子线程后面，不过如果子线程在1毫秒时间内没执行完，则主线程便不再等待它执行完，进入就绪状态，等待cpu调度
        t.join(1);
        for (int i = 0; i < 30; i++) {
            System.out.println("main线程第" + i + "次执行！");
        }
    }
}

class MyThread5 extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println("MyThread线程第" + i + "次执行！");
        }
    }
}
