package buildchallengePartA;

import java.util.List;

class Consumer implements Runnable {
    private final CustomBlockingQueue<String> sharedQueue;
    private final List<String> destinationContainer;
    private final int itemsToConsume;

    /**
     * Creates a new Consumer with the specified queue, destination, and item count.
     *
     * @param sharedQueue the blocking queue from which items will be retrieved
     * @param destinationContainer the list where consumed items will be stored
     * @param itemsToConsume the number of items this consumer should process
     * @throws NullPointerException if sharedQueue or destinationContainer is null
     * @throws IllegalArgumentException if itemsToConsume is negative
     */


    public Consumer(CustomBlockingQueue<String> sharedQueue,
                    List<String> destinationContainer,
                    int itemsToConsume) {
        this.sharedQueue = sharedQueue;
        this.destinationContainer = destinationContainer;
        this.itemsToConsume = itemsToConsume;
    }

    /**
     * Executes the consumer logic: retrieves items from queue and stores them.
     * This method blocks when the queue is empty and resumes when items become available.
     */
    @Override
    public void run() {
        try {
            for (int i = 0; i < itemsToConsume; i++) {
                String item = sharedQueue.take();
                System.out.println("\n[CONSUMER] Processing: " + item);

                // Store in destination container with synchronization
                synchronized (destinationContainer) {
                    destinationContainer.add(item);
                }

                Thread.sleep(150); // Simulate processing time (slower than producer)
            }
            System.out.println("\n[CONSUMER] Finished - All items consumed");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Consumer interrupted");
        }
    }
}
