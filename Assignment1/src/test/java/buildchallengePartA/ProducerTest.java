package buildchallengePartA;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProducerTest {

    @Test
    @DisplayName("Test producer reads all items from source")
    void testProducerReadsAllItems() throws InterruptedException {
        List<String> source = Arrays.asList("A", "B", "C", "D");
        CustomBlockingQueue<String> queue = new CustomBlockingQueue<>(10);

        Producer producer = new Producer(source, queue);
        Thread thread = new Thread(producer);
        thread.start();
        thread.join(2000);

        assertEquals(source.size(), queue.size());
    }

    @Test
    @DisplayName("Test producer with empty source container")
    void testProducerWithEmptySource() throws InterruptedException {
        List<String> source = new ArrayList<>();
        CustomBlockingQueue<String> queue = new CustomBlockingQueue<>(5);

        Producer producer = new Producer(source, queue);
        Thread thread = new Thread(producer);
        thread.start();
        thread.join(1000);

        assertEquals(0, queue.size());
    }

    @Test
    @DisplayName("Test producer with small queue capacity")
    void testProducerWithSmallQueue() throws InterruptedException {
        List<String> source = Arrays.asList("1", "2", "3", "4", "5");
        CustomBlockingQueue<String> queue = new CustomBlockingQueue<>(2);

        CountDownLatch producerStarted = new CountDownLatch(1);
        CountDownLatch producerFinished = new CountDownLatch(1);
        AtomicInteger producedCount = new AtomicInteger(0);

        Thread producer = new Thread(() -> {
            try {
                producerStarted.countDown();
                for (String item : source) {
                    queue.put(item);
                    producedCount.incrementAndGet();
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                producerFinished.countDown();
            }
        });

        producer.start();
        producerStarted.await();
        Thread.sleep(300); // Give more time for producer to fill queue and block

        // Producer should be blocked after filling queue
        int countWhileBlocked = producedCount.get();
        assertTrue(countWhileBlocked >= 2 && countWhileBlocked <= 3,
                "Producer should block when queue is full, produced: " + countWhileBlocked);

        // Consume all items to unblock producer
        for (int i = 0; i < source.size(); i++) {
            queue.take();
        }

        // Wait for producer to finish
        assertTrue(producerFinished.await(2, TimeUnit.SECONDS),
                "Producer should finish after queue is consumed");
        assertEquals(source.size(), producedCount.get(),
                "All items should be produced eventually");
    }
}
