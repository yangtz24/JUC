package com.ytz.thread.juc;

/**
 * @ClassName: ProducerAndConsumerDemo
 * @Description: 生产者和消费者
 *                  需求：两个线程，可以操作初始值为0的一个变量，一个线程对其加1，另一个线程对其减1
 *                      并交替打印。10次
 * @author: yangtianzeng
 * @date: 2020/4/6 23:33
 */
public class ProducerAndConsumerDemo {

    public static void main(String[] args) {
        final AirCondition airCondition = new AirCondition();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airCondition.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airCondition.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airCondition.increment();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    airCondition.decrement();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();
    }
}

class AirCondition{
    private int num = 0;

    public synchronized void increment() throws Exception {
        // 判断  while:防止虚假唤醒
        while (num != 0) {
            this.wait();
        }
        // 业务
        num++;
        System.out.println(Thread.currentThread().getName()+"\t"+num);
        // 通知
        this.notifyAll();
    }

    public synchronized void decrement() throws Exception {
        // 判断
        while (num == 0) {
            this.wait();
        }
        // 业务
        num--;
        System.out.println(Thread.currentThread().getName()+"\t"+num);
        // 通知
        this.notifyAll();
    }
}
