package buildchallengePartA;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Main application class demonstrating the Producer-Consumer pattern with
 * thread synchronization and blocking queue.
 *
 * This application showcases:
 *
 *   Custom blocking queue implementation using ReentrantLock
 *   Thread coordination using wait/notify (Condition variables)
 *   Concurrent data transfer between producer and consumer threads
 *   Handling of queue full and empty conditions
 *
 * The application creates a producer thread that reads from a source container
 * and a consumer thread that writes to a destination container, with a bounded
 * blocking queue mediating between them.
 *
 * @author Manan Vijayvargiya
 * @version 1.0
 * @since 2025-11-19
 */

public class ProducerConsumerSystem {
    /** Default queue capacity demonstrating blocking behavior */
    private static final int QUEUE_CAPACITY = 3;

    /**
     * Main entry point for the Producer-Consumer demonstration.
     * <p>
     * Initializes the source container, creates producer and consumer threads,
     * and orchestrates their execution. Finally, verifies that all items were
     * successfully transferred from source to destination.
     * </p>
     *
     * @param args command line arguments (not used)
     */

    public static void main(String[] args) {
        // Initialize source container with data
        List<String> sourceContainer = Arrays.asList(
                "Item-1", "Item-2", "Item-3", "Item-4", "Item-5",
                "Item-6", "Item-7", "Item-8", "Item-9", "Item-10"
        );

        // Shared blocking queue (capacity: 3)
        CustomBlockingQueue<String> sharedQueue = new CustomBlockingQueue<>(QUEUE_CAPACITY);

        // Destination container for consumed items
        List<String> destinationContainer = Collections.synchronizedList(new ArrayList<>());

        System.out.println("=== PRODUCER-CONSUMER DEMONSTRATION ===");
        System.out.println("Source items: " + sourceContainer.size());
        System.out.println("Queue capacity: " + QUEUE_CAPACITY);
        System.out.println("=====================================\n");

        // Create producer and consumer threads
        Thread producerThread = new Thread(
                new Producer(sourceContainer, sharedQueue),
                "Producer-Thread"
        );

        Thread consumerThread = new Thread(
                new Consumer(sharedQueue, destinationContainer, sourceContainer.size()),
                "Consumer-Thread"
        );

        // Start both threads
        producerThread.start();
        consumerThread.start();

        // Wait for both threads to complete
        try {
            producerThread.join();
            consumerThread.join();

            // Verify results
            System.out.println("\n=== RESULTS ===");
            System.out.println("Source container size: " + sourceContainer.size());
            System.out.println("Destination container size: " + destinationContainer.size());
            System.out.println("\nDestination contents:");
            destinationContainer.forEach(item -> System.out.println("  " + item));
            System.out.println("\nTransfer successful: " +
                    (sourceContainer.size() == destinationContainer.size()));

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Main thread interrupted");
        }
    }
}
