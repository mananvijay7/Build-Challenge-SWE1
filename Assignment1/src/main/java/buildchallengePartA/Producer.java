package buildchallengePartA;

import java.util.List;

/**
 * Producer thread that reads items from a source container and places them
 * into a shared blocking queue.
 *
 * The producer demonstrates the producer side of the producer-consumer pattern.
 * It continuously reads items from the source and attempts to add them to the
 * shared queue. If the queue is full, the producer blocks until space becomes
 * available.
 *
 * Thread Safety: This class is thread-safe. Multiple producer instances can
 * safely operate on different source containers and the same shared queue.
 *
 *
 * @author Manan Vijayvargiya
 * @version 1.0
 * @since 2025-11-19
 */

class Producer implements Runnable {
    private final List<String> sourceContainer;
    private final CustomBlockingQueue<String> sharedQueue;

    /**
     * Creates a new Producer with the specified source container and shared queue.
     *
     * @param sourceContainer the list containing items to be produced
     * @param sharedQueue the blocking queue where items will be placed
     * @throws NullPointerException if sourceContainer or sharedQueue is null
     */

    public Producer(List<String> sourceContainer, CustomBlockingQueue<String> sharedQueue) {
        this.sourceContainer = sourceContainer;
        this.sharedQueue = sharedQueue;
    }

    /**
     * Executes the producer logic: reads items from source and places them in the queue.
     * This method blocks when the queue is full and resumes when space becomes available.
     */
    @Override
    public void run() {
        try {
            for (String item : sourceContainer) {
                System.out.println("\n[PRODUCER] Reading from source: " + item);
                sharedQueue.put(item);
                Thread.sleep(100); // Simulate processing time
            }
            System.out.println("\n[PRODUCER] Finished - All items produced");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Producer interrupted");
        }
    }
}
