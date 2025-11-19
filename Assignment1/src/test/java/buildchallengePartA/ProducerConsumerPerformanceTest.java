package buildchallengePartA;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Performance and stress tests
 */
class ProducerConsumerPerformanceTest {

    @Test
    @DisplayName("Test with large dataset")
    void testLargeDataset() throws InterruptedException {
        int itemCount = 1000;
        List<Integer> source = new ArrayList<>();
        for (int i = 0; i < itemCount; i++) {
            source.add(i);
        }

        CustomBlockingQueue<Integer> queue = new CustomBlockingQueue<>(50);
        List<Integer> destination = Collections.synchronizedList(new ArrayList<>());

        Thread producer = new Thread(() -> {
            try {
                for (Integer item : source) {
                    queue.put(item);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 0; i < itemCount; i++) {
                    destination.add(queue.take());
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        long startTime = System.currentTimeMillis();

        producer.start();
        consumer.start();

        producer.join(30000);
        consumer.join(30000);

        long duration = System.currentTimeMillis() - startTime;

        assertEquals(itemCount, destination.size());
        System.out.println("Transferred " + itemCount + " items in " + duration + "ms");
    }
}
