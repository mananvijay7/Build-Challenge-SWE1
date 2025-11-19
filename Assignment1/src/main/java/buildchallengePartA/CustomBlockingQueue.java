package buildchallengePartA;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A thread-safe blocking queue implementation with bounded capacity.
 *
 * This queue uses ReentrantLock and Condition variables to coordinate
 * between producer and consumer threads. When the queue is full, producers
 * block until space becomes available. When empty, consumers block until
 * items are available.
 *
 * @param <T> the type of elements held in this queue
 * @author Manan Vijayvargiya
 * @version 1.0
 * @since 2025-11-19
 */

/**
 * Custom Blocking Queue implementation demonstrating wait/notify mechanism
 */
class CustomBlockingQueue<T> {
    private final Queue<T> queue;
    private final int capacity;
    private final Lock lock;
    private final Condition notFull;
    private final Condition notEmpty;

    public CustomBlockingQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
        this.lock = new ReentrantLock();
        this.notFull = lock.newCondition();
        this.notEmpty = lock.newCondition();
    }

    /**
     * Adds item to queue, waits if queue is full
     */
    public void put(T item) throws InterruptedException {
        lock.lock();
        try {
            while (queue.size() == capacity) {
                System.out.println(Thread.currentThread().getName() + " - Queue FULL, waiting...");
                notFull.await(); // Wait until queue has space
            }
            queue.offer(item);
            System.out.println(Thread.currentThread().getName() + " - Added: " + item + " | Queue size: " + queue.size());
            notEmpty.signal(); // Signal consumer that queue is not empty
        } finally {
            lock.unlock();
        }
    }

    /**
     * Removes item from queue, waits if queue is empty
     */
    public T take() throws InterruptedException {
        lock.lock();
        try {
            while (queue.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " - Queue EMPTY, waiting...");
                notEmpty.await(); // Wait until queue has items
            }
            T item = queue.poll();
            System.out.println(Thread.currentThread().getName() + " - Removed: " + item + " | Queue size: " + queue.size());
            notFull.signal(); // Signal producer that queue has space
            return item;
        } finally {
            lock.unlock();
        }
    }

    public int size() {
        lock.lock();
        try {
            return queue.size();
        } finally {
            lock.unlock();
        }
    }
}
