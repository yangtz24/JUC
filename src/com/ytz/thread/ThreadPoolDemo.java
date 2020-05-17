import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName: ThreadPoolDemo
 * @Description: TODO  自定义线程池
 * @author: basketBoy
 * @date: 2020/5/17
 * @Version: V1.0
 */
public class ThreadPoolDemo {

    public static final int LOOP = 5;

    public static void main(String[] args) {
        ThreadPool threadPool = new ThreadPool(2, 1000, TimeUnit.MILLISECONDS, 10);
        for (int i = 0; i < LOOP; i++) {
            int j = i;
            threadPool.execute(() -> {
                System.out.println(j);
            });
        }

    }
}

class ThreadPool {
    /**
     * 任务队列
     */
    private BlockingQueue<Runnable> taskQueue;

    /**
     * 线程集合
     */
    private HashSet<Worker> workers = new HashSet<>();

    /**
     * 核心线程数
     */
    private int coreSize;

    /**
     * 超时时间
     */
    private long timeOut;

    /**
     * 单位名称
     */
    private TimeUnit unit;

    /**
     * 执行任务
     * @param task
     */
    public void execute(Runnable task) {
        // 当任务数 < coreSize 时，直接交给 worker 对象。 否则，加入任务队列暂存
        synchronized (workers) {
            if (workers.size() < coreSize) {
                Worker worker = new Worker(task);
                workers.add(worker);
                worker.start();
            } else {
                taskQueue.put(task);
            }
        }
    }

    public ThreadPool(int coreSize, long timeOut, TimeUnit unit, int queueCapcity) {
        this.coreSize = coreSize;
        this.timeOut = timeOut;
        this.unit = unit;
        this.taskQueue = new BlockingQueue<>(queueCapcity);
    }

    class Worker extends Thread {
        private Runnable task;

        public Worker(Runnable task) {
            this.task = task;
        }

        @Override
        public void run() {
            // 执行任务
            // 1 task 不为空 执行任务
            // 2 task执行完毕，任务队列获取任务并执行任务
            while (task != null || (task = taskQueue.poll(timeOut, unit)) != null) {
                try {
                    task.run();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    task = null;
                }
            }

            synchronized (workers) {
                workers.remove(this);
            }
        }
    }


}

/**
 * 自定义阻塞队列
 * @param <T>
 */
class BlockingQueue<T> {
    /**
     * 任务队列
     */
    private Deque<T> queue = new ArrayDeque<>();

    /**
     * 锁
     */
    private ReentrantLock lock = new ReentrantLock();

    /**
     * 生产者条件变量
     */
    private Condition fullWaitSet = lock.newCondition();

    /**
     * 消费者条件变量
     */
    private Condition emptyWaitSet = lock.newCondition();

    /**
     * 容量
     */
    private int capcity;

    public BlockingQueue(int capcity) {
        this.capcity = capcity;
    }

    /**
     * 阻塞获取
     * @return
     */
    public T take() {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                try {
                    emptyWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            fullWaitSet.signal();
            // 获取队列头元素
            T t = queue.removeFirst();
            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取 带超时时间
     * @param timeOut  超时时间
     * @param unit  单位
     * @return
     */
    public T poll(long timeOut, TimeUnit unit) {
        lock.lock();
        try {
            // 统一时间单位
            long nanos = unit.toNanos(timeOut);
            while (queue.isEmpty()) {
                try {
                    if (nanos <= 0) {
                        return null;
                    }
                    // 返回的是剩余时间
                    nanos = emptyWaitSet.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            fullWaitSet.signal();
            // 获取队列头元素
            T t = queue.removeFirst();
            return t;
        } finally {
            lock.unlock();
        }
    }

    /**
     * 阻塞添加
     * @param element
     */
    public void put(T element) {
        lock.lock();
        try {
            while (queue.size() == capcity) {
                try {
                    fullWaitSet.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 添加到队列
            queue.addLast(element);
            emptyWaitSet.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * 获取队列大小
     * @return
     */
    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}
