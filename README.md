#   JUC

## 1. JUC简介

​	JUC是java.util .concurrent工具包的简称。这是一个处理线程的工具包，JDK 1.5开始出现的。在此包中增加了在并发编程中很常用 的实用工具类，用于定义类似于线程的自定义子 系统，包括线程池、异步 IO 和轻量级任务框架。 提供可调的、灵活的线程池。还提供了设计用于 多线程上下文中的 Collection 实现等。

## **2. 进程和线程**

​	进程：进程是一个具有独立功能的程序关于某个集合的一次运行活动。它是操作系统动态执行的基本单元。

​	线程：是进程的一个实体,是CPU调度和分派的基本单位,它是比进程更小的能独立运行的基本单位。

## 3. 线程状态

```java
NEW、RUNNABLE、BLOCKED、WAITING、TIMED_WAITING、TERMINATED
```

![](image\threadState.png)

## 4. Synchoronized关键字

## 5. Lock接口

## 6. 线程间通信

## 7. 多线程锁

## 8. Callable接口

## 9. JUC辅助类

## 10.ReentrantReadWriteLock（读写锁）

## 11.BlockingQueue阻塞队列

## 12.ThreadPool线程池

## 13.分支合并框架

## 14.异步回调