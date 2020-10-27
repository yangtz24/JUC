package com.ytz.thread;

/**
 * @ClassName: ThreadDaemon
 * @Description: 守护线程
 * Daemon 线程是一种支持型线程，在后台守护一些系统服务，比如 JVM 的垃圾回收、内存管理等线程都是守护线程。
 * 与之对应的就是用户线程，用户线程就是系统的工作线程，它会完成整个系统的业务操作。
 * 用户线程结束后就意味着整个系统的任务全部结束了，因此系统就没有对象需要守护的了，守护线程自然而然就会退出。
 * 所以当一个 Java 应用只有守护线程的时候，虚拟机就会自然退出。
 * 注意：
 * 调用 setDaemon(boolean on)设置守护线程要在线程启动前，否则会抛出异常。java.lang.IllegalThreadStateException
 * 守护线程在退出的时候并不会执行 finnaly 块中的代码，所以将释放资源等操作不要放在 finnaly 块中执行，这种操作是不安全的。
 * @author: yangtianzeng
 * @date: 2020/3/28 20:33
 */
public class ThreadDaemon {

    public static void main(String[] args) {
        Thread t1 = new MyCommon();
        Thread t2 = new Thread(new MyDaemon());
        // 设置为守护线程
        t2.setDaemon(true);

        t2.start();
        t1.start();

        /**
         * main 线程在启动了线程 DaemonRunner 之后随着 main 方法执行完毕而终止，而此时 Java 虚拟机中已经没有非 Daemon 线程，虚拟机需要退出。
         * Daemon 线程 DaemonRunner 立即终止，DaemonRunner 中的 finally 块没有执行。
         */
        Thread thread = new Thread(new DaemonRunner(), "DaemonRunner");
        thread.setDaemon(true);
        thread.start();
        //结果：输出 --》 主线程执行完毕
        System.out.println("主线程执行完毕");
    }

    static class DaemonRunner implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("DaemonThread finally run.");
            }
        }
    }
}

class MyCommon extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println("线程1第" + i + "次执行！");
            try {
                Thread.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class MyDaemon implements Runnable {
    @Override
    public void run() {
        for (long i = 0; i < 9999999L; i++) {
            System.out.println("后台线程第" + i + "次执行！");
            try {
                Thread.sleep(7);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("finally code ...........");
            }
        }
    }
}
