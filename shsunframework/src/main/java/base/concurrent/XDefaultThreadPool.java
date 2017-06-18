package base.concurrent;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池 、缓冲队列
 */
public class XDefaultThreadPool {

    /**
     *
     */
    public static final int THREAD_POOL_MAX_SIZE = 10;
    /**
     *
     */
    public static final int THREAD_POOL_SIZE = 6;


    /**
     * 缓冲HttpRequest任务队列, 阻塞队列最大任务数量设置为20
     */
    protected static ArrayBlockingQueue<Runnable> theBlockingQueue = new ArrayBlockingQueue<Runnable>(20);

    /**
     *     public ThreadPoolExecutor(int corePoolSize,
     int maximumPoolSize,
     */

    /**
     * 线程池，目前是十个线程，
     */
    protected static AbstractExecutorService theThreadPool
            = new ThreadPoolExecutor(THREAD_POOL_SIZE,
                                        THREAD_POOL_MAX_SIZE,
                                        15L,
                                        TimeUnit.SECONDS,
                                        theBlockingQueue,
                                        new ThreadPoolExecutor.DiscardOldestPolicy());

    /**
     * 
     */
    private static XDefaultThreadPool theInstance = null;

    /**
     * 
     * @return
     */
    public static synchronized XDefaultThreadPool getInstance() {
        if (theInstance == null) {
            theInstance = new XDefaultThreadPool();
        }
        return theInstance;
    }

    /**
     *
     */
    public static void removeAllTask() {
        theBlockingQueue.clear();
    }

    /**
     *
     * @param runnable
     */
    public static void removeTaskFromQueue(final Runnable runnable) {
        theBlockingQueue.remove(runnable);
    }

    /**
     * 关闭，并等待任务执行完成，不接受新任务
     */
    public static void shutdown() {
        if (theThreadPool != null) {
            theThreadPool.shutdown();
        }
    }

    /**
     * 关闭，立即关闭，并挂起所有正在执行的线程，不接受新任务
     */
    public static void shutdownRightnow() {
        if (theThreadPool != null) {
            theThreadPool.shutdownNow();
            try {
                // 设置超时极短，强制关闭所有任务
                theThreadPool.awaitTermination(1, TimeUnit.MICROSECONDS);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 执行任务
     *
     * @param runnable
     */
    public void execute(final Runnable runnable) {
        if (runnable != null) {
            try {
                theThreadPool.execute(runnable);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }
}
