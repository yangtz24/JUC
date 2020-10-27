package com.ytz.thread.juc;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName: CollectionNotSafeDemo
 * @Description: 集合不安全类
 * List   java.util.ConcurrentModificationException
 * 解决方法：
 * 1. Vector 不推荐
 * 2. Collections.synchronizedList(new ArrayList<String>()) 单线程可考虑
 * 3. CopyOnWriteArrayList<V> 并发性高
 * Set   java.util.ConcurrentModificationException
 * 解决方法：
 * 1. Collections.synchronizedSet(new HashSet<>())
 * 2. CopyOnWriteArraySet<>()
 * Map  java.util.ConcurrentModificationException
 * 解决方法：
 * 1. Collections.synchronizedMap(new HashMap<>())
 * 2. ConcurrentHashMap<>
 * @author: yangtianzeng
 * @date: 2020/4/7 9:06
 */
public class CollectionNotSafeDemo {

    public static void main(String[] args) {
//        list();
//        set();
        map();
    }

    public static void map() {
//        final Map<String, String> map = new HashMap<>();
//        final Map<String, String> map = Collections.synchronizedMap(new HashMap<>());
        final Map<String, String> map = new ConcurrentHashMap<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                map.put(Thread.currentThread().getName(), UUID.randomUUID().toString().substring(0, 8));
                System.out.println(map);
            }).start();
        }

    }

    public static void set() {
        Set<String> set = new HashSet<>();
//        Set<String> set = Collections.synchronizedSet(new HashSet<>());
//        Set<String> set = new CopyOnWriteArraySet<>();

        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                set.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(set);
            }, String.valueOf(i)).start();
        }
    }

    public static void list() {
//        final List<String> list = new ArrayList<>();
//        final List<String> list = new Vector<>();
//        final List<String> list = Collections.synchronizedList(new ArrayList<String>());
        final List<String> list = new CopyOnWriteArrayList<>();


        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 8));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }
    }
}
