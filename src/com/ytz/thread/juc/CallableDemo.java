package com.ytz.thread.juc;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @ClassName: CallableDemo
 * @Description: 线程的第三种实现方法
 *                               Callable       与         Runnable区别
 *                     返回值       有，类型根据泛型而定           无
 *                     方法名       call()                     run()
 *                     异常        方法抛出异常                  不抛出异常
 *
 * @author: yangtianzeng
 * @date: 2020/4/7 9:57
 */
public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        MyThread myThread = new MyThread();

        //
        FutureTask<Integer> futureTask = new FutureTask<>(myThread);

        new Thread(futureTask, "A").start();

        //获取返回值
        final Integer result = futureTask.get();
        System.out.println(result);
    }
}

class MyThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        return 1024;
    }
}
