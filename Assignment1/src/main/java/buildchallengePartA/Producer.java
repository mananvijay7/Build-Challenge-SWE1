package buildchallengePartA;

import java.util.List;

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
