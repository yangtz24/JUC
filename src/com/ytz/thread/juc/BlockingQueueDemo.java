package com.ytz.thread.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;

/**
 * @ClassName: BlockingQueueDemo
 * @Description: 阻塞队列
 *                      当队列中是空时，获取元素会被阻塞
 *                      当队列中是满时，添加元素会被阻塞
 *               BlockingQueue实现类：
 *                     ArrayBlockingQueue:由数组构成的有界阻塞队列
 *                     LinkedBlockingQueue:由链表构成的有界阻塞队列（默认值大小：Integer.MAX_VALUE）
 *
 *               方法类型    抛出异常            特殊值(true/false)           阻塞              超时
 *                插入       add(e)            offer(e)                   put(e)        offer(e,time,unit)
 *                移除       remove()          poll()                     take()         poll(time,unit)
 *                检查       element()         peek()                     *               *
 * @author: yangtianzeng
 * @date: 2020/4/7 12:09
 */
public class BlockingQueueDemo {

    public static void main(String[] args) {
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        blockingQueue.add("a");
        blockingQueue.add("b");
        blockingQueue.add("c");
        // java.lang.IllegalStateException: Queue full
        System.out.println(blockingQueue.add("d"));
        // java.util.NoSuchElementException
//        System.out.println(blockingQueue.remove());

        Executors.newFixedThreadPool(int i);
        Executors.newSingleThreadExecutor();
        Executors.newCachedThreadPool()

    }


}
