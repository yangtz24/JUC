package com.ytz.thread.juc;

import java.util.concurrent.*;

/**
 * @ClassName: CyclicBarrierDemo
 * @Description:    CyclicBarrier：它的作用就是会让所有线程都等待完成后才会继续下一步行动
 *                                  可以用于多线程计算数据，最后合并计算结果的场景。
 * @author: yangtianzeng
 * @date: 2020/4/7 10:43
 */
public class CyclicBarrierDemo {

    /**
     * 线程池
     */
    private ExecutorService executorService;
    private CyclicBarrier cyclicBarrier;
    /**
     *   参与线程的个数
     */
    private int parties;

    public CyclicBarrierDemo() {
    }

    public CyclicBarrierDemo(int parties) {
        this.executorService = Executors.newFixedThreadPool(parties);
        this.cyclicBarrier = new CyclicBarrier(parties, () -> System.out.println(Thread.currentThread().getName() + "\t gets barrierCommand done"));
        this.parties = parties;
    }

    public static void main(String[] args) {
        CyclicBarrierDemo cyclicBarrierDemo = new CyclicBarrierDemo(10);
        cyclicBarrierDemo.example();
    }

    private void example() {
        for (int i = 0; i < parties; i++) {
            executorService.submit(() -> {
                try {
                    TimeUnit.MILLISECONDS.sleep(1000);
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"\t gets job done");
            });
        }
        executorService.shutdown();

    }




}
