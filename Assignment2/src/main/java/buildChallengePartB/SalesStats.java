package buildChallengePartB;

import java.util.DoubleSummaryStatistics;

/**
 * Wrapper class for DoubleSummaryStatistics with formatted output.
 * statistical summaries in a business-friendly format.
 */
class SalesStats {
    private final double total;
    private final double average;
    private final double min;
    private final double max;
    private final long count;

    /**
     * Constructs SalesStats from Java's DoubleSummaryStatistics.
     *
     * @param stats Statistics object from stream terminal operation
     */
    public SalesStats(DoubleSummaryStatistics stats) {
        this.total = stats.getSum();
        this.average = stats.getAverage();
        this.min = stats.getMin();
        this.max = stats.getMax();
        this.count = stats.getCount();
    }

    @Override
    public String toString() {
        return String.format("Count: %d, Total: $%.2f, Avg: $%.2f, Min: $%.2f, Max: $%.2f",
                count, total, average, min, max);
    }
}