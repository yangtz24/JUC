package com.ytz.thread;

/**
 * @ClassName: ThreadPriority
 * @Description: 线程优先级
 * Thread 类通过一个整型成员变量 priority 来控制优先级，优先级的范围从 1 ～ 10，默认优先级是 5。
 * public static final int MIN_PRIORITY = 1;
 * public static final int NORM_PRIORITY = 5;
 * public static final int MAX_PRIORITY = 10;
 * @author: yangtianzeng
 * @date: 2020/3/28 20:19
 */
public class ThreadPriority {

    public static void main(String[] args) throws InterruptedException {
        new MyThread3("高级", 10).start();
        new MyThread3("低级", 1).start();
    }
}

class MyThread3 extends Thread {
    public MyThread3(String name, int pro) {
        // 设置线程的名称
        super(name);
        // 设置线程的优先级
        setPriority(pro);
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            System.out.println(this.getName() + "线程第" + i + "次执行！");
        }
    }
}


