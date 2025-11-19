package buildchallengePartA;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ConsumerTest {

    @Test
    @DisplayName("Test consumer stores all items in destination")
    void testConsumerStoresAllItems() throws InterruptedException {
        CustomBlockingQueue<String> queue = new CustomBlockingQueue<>(5);
        List<String> destination = Collections.synchronizedList(new ArrayList<>());

        // Pre-fill queue
        queue.put("X");
        queue.put("Y");
        queue.put("Z");

        Consumer consumer = new Consumer(queue, destination, 3);
        Thread thread = new Thread(consumer);
        thread.start();
        thread.join(2000);

        assertEquals(3, destination.size());
        assertTrue(destination.containsAll(Arrays.asList("X", "Y", "Z")));
    }

    @Test
    @DisplayName("Test consumer maintains order")
    void testConsumerMaintainsOrder() throws InterruptedException {
        CustomBlockingQueue<String> queue = new CustomBlockingQueue<>(5);
        List<String> destination = Collections.synchronizedList(new ArrayList<>());

        String[] items = {"First", "Second", "Third"};
        for (String item : items) {
            queue.put(item);
        }

        Consumer consumer = new Consumer(queue, destination, items.length);
        Thread thread = new Thread(consumer);
        thread.start();
        thread.join(2000);

        assertArrayEquals(items, destination.toArray(new String[0]));
    }

    @Test
    @DisplayName("Test consumer with zero items")
    void testConsumerWithZeroItems() throws InterruptedException {
        CustomBlockingQueue<String> queue = new CustomBlockingQueue<>(5);
        List<String> destination = Collections.synchronizedList(new ArrayList<>());

        Consumer consumer = new Consumer(queue, destination, 0);
        Thread thread = new Thread(consumer);
        thread.start();
        thread.join(1000);

        assertEquals(0, destination.size());
    }
}