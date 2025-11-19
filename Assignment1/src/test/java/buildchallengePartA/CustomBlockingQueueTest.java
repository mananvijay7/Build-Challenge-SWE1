package buildchallengePartA;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Comprehensive unit tests for CustomBlockingQueue
 */
class CustomBlockingQueueTest {

    private CustomBlockingQueue<String> queue;

    @BeforeEach
    void setUp() {
        queue = new CustomBlockingQueue<>(3);
    }

    @Test
    @DisplayName("Test basic put and take operations")
    void testBasicPutAndTake() throws InterruptedException {
        queue.put("Item1");
        queue.put("Item2");

        assertEquals("Item1", queue.take());
        assertEquals("Item2", queue.take());
    }

    @Test
    @DisplayName("Test queue capacity enforcement")
    void testQueueCapacity() throws InterruptedException {
        queue.put("Item1");
        queue.put("Item2");
        queue.put("Item3");

        assertEquals(3, queue.size());
    }

    @Test
    @DisplayName("Test FIFO ordering")
    void testFIFOOrdering() throws InterruptedException {
        String[] items = {"First", "Second", "Third"};

        for (String item : items) {
            queue.put(item);
        }

        for (String expected : items) {
            assertEquals(expected, queue.take());
        }
    }

    @Test
    @DisplayName("Test blocking behavior when queue is full")
    void testBlockingOnFullQueue() throws InterruptedException {
        // Fill the queue
        queue.put("Item1");
        queue.put("Item2");
        queue.put("Item3");

        AtomicInteger putCount = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(1);

        // Try to put another item (should block)
        Thread producer = new Thread(() -> {
            try {
                latch.countDown();
                queue.put("Item4"); // This will block
                putCount.incrementAndGet();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        latch.await(); // Wait for producer to start
        Thread.sleep(100); // Give time for producer to block

        assertEquals(0, putCount.get(), "Producer should be blocked");

        // Unblock by taking an item
        queue.take();
        producer.join(1000);

        assertEquals(1, putCount.get(), "Producer should have completed");
        assertEquals(3, queue.size());
    }

    @Test
    @DisplayName("Test blocking behavior when queue is empty")
    void testBlockingOnEmptyQueue() throws InterruptedException {
        AtomicInteger takeCount = new AtomicInteger(0);
        CountDownLatch latch = new CountDownLatch(1);

        // Try to take from empty queue (should block)
        Thread consumer = new Thread(() -> {
            try {
                latch.countDown();
                queue.take(); // This will block
                takeCount.incrementAndGet();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        consumer.start();
        latch.await();
        Thread.sleep(100);

        assertEquals(0, takeCount.get(), "Consumer should be blocked");

        // Unblock by putting an item
        queue.put("Item1");
        consumer.join(1000);

        assertEquals(1, takeCount.get(), "Consumer should have completed");
    }

    @Test
    @DisplayName("Test concurrent put operations")
    void testConcurrentPuts() throws InterruptedException {
        CustomBlockingQueue<Integer> intQueue = new CustomBlockingQueue<>(10);
        int threadCount = 5;
        int itemsPerThread = 10;

        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int threadId = i;
            new Thread(() -> {
                try {
                    for (int j = 0; j < itemsPerThread; j++) {
                        intQueue.put(threadId * 100 + j);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            }).start();
        }

        latch.await(5, TimeUnit.SECONDS);

        // Consume all items
        Set<Integer> consumed = new HashSet<>();
        for (int i = 0; i < threadCount * itemsPerThread; i++) {
            consumed.add(intQueue.take());
        }

        assertEquals(threadCount * itemsPerThread, consumed.size(),
                "All items should be unique and present");
    }

    @Test
    @DisplayName("Test thread interruption handling")
    void testInterruptionHandling() throws InterruptedException {
        Thread consumer = new Thread(() -> {
            try {
                queue.take(); // Will block on empty queue
                fail("Should have been interrupted");
            } catch (InterruptedException e) {
                assertTrue(Thread.currentThread().isInterrupted());
            }
        });

        consumer.start();
        Thread.sleep(100);
        consumer.interrupt();
        consumer.join(1000);
    }
}
