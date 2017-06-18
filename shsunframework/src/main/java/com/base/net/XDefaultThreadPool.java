package com.base.net;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池 、缓冲队列
 */
public class XDefaultThreadPool {

    // 阻塞队列最大任务数量
    static final int BLOCKING_QUEUE_SIZE = 20;

    static final int THREAD_POOL_MAX_SIZE = 10;

    static final int THREAD_POOL_SIZE = 6;

    /**
     * 缓冲BaseRequest任务队列
     */
    static ArrayBlockingQueue<Runnable> blockingQueue = new ArrayBlockingQueue<Runnable>(
            XDefaultThreadPool.BLOCKING_QUEUE_SIZE);

    private static XDefaultThreadPool instance = null;

    /**
     * 线程池，目前是十个线程，
     */
    static AbstractExecutorService pool = new ThreadPoolExecutor(
            XDefaultThreadPool.THREAD_POOL_SIZE,
            XDefaultThreadPool.THREAD_POOL_MAX_SIZE,
            15L,
            TimeUnit.SECONDS,
            XDefaultThreadPool.blockingQueue,
            new ThreadPoolExecutor.DiscardOldestPolicy());

    public static synchronized XDefaultThreadPool getInstance() {
        if (XDefaultThreadPool.instance == null) {
            XDefaultThreadPool.instance = new XDefaultThreadPool();
        }
        return XDefaultThreadPool.instance;
    }

    public static void removeAllTask() {
        XDefaultThreadPool.blockingQueue.clear();
    }

    public static void removeTaskFromQueue(final Object obj) {
        XDefaultThreadPool.blockingQueue.remove(obj);
    }

    /**
     * 关闭，并等待任务执行完成，不接受新任务
     */
    public static void shutdown() {
        if (XDefaultThreadPool.pool != null) {
            XDefaultThreadPool.pool.shutdown();
        }
    }

    /**
     * 关闭，立即关闭，并挂起所有正在执行的线程，不接受新任务
     */
    public static void shutdownRightnow() {
        if (XDefaultThreadPool.pool != null) {
            XDefaultThreadPool.pool.shutdownNow();
            try {
                // 设置超时极短，强制关闭所有任务
                XDefaultThreadPool.pool.awaitTermination(1, TimeUnit.MICROSECONDS);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行任务
     *
     * @param r
     */
    public void execute(final Runnable r) {
        if (r != null) {
            try {
                XDefaultThreadPool.pool.execute(r);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }
}
