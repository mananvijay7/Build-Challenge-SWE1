package buildChallengePartB;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Core analysis engine for sales data.
 *
 * Architecture: Single-responsibility methods, each performing one type of analysis.
 * All methods are non-mutating and use functional stream operations.
 *
 * Design Pattern: This class follows the Service/Business Logic pattern.
 * It has no knowledge of how it's instantiated or where data comes from (Dependency Injection ready).
 *
 *
 * @author Manan Vijayvargiya
 * @version 1.0
 */
class SalesDataAnalyzer {
    /** In-memory storage of all sales records after loading from CSV */
    private List<SalesRecord> salesData;

    /**
     * Loads sales data from CSV file using functional stream approach.
     *
     * @param resourcePath Path to the CSV file
     * @throws IOException if file cannot be read
     * @return Number of records successfully loaded
     */
    public int loadDataFromFile(String resourcePath) throws IOException {
        // Load as classpath resource (from src/main/resources)
        try (InputStream is = SalesDataAnalyzer.class.getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new FileNotFoundException("Resource not found on classpath: " + resourcePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))) {

                // Functional approach: Stream pipeline for data loading
                salesData = reader.lines()
                        .skip(1)  // Skip header row
                        .filter(line -> !line.trim().isEmpty())  // Remove empty lines
                        .map(line -> line.split(","))   // ← use "," for normal CSV
                        .filter(fields -> fields.length >= 13)  // Validate field count
                        .map(fields -> {
                            try {
                                return new SalesRecord(fields);
                            } catch (Exception e) {
                                // Log and skip invalid records
                                System.err.println("Warning: Skipping invalid record - " + e.getMessage());
                                return null;
                            }
                        })
                        .filter(Objects::nonNull)  // Remove null entries from parsing errors
                        .collect(Collectors.toList());
            }
        }

        return salesData.size();
    }


    /**
     * Returns the number of loaded records.
     *
     * @return count of sales records, or 0 if data not loaded
     */
    public int getRecordCount() {
        return salesData != null ? salesData.size() : 0;
    }

    /**
     * Analyzes total sales amount aggregated by region.
     */
    public void analyzeSalesByRegion() {
        System.out.println("=== ANALYSIS 1: SALES BY REGION ===");

        Map<String, Double> salesByRegion = salesData.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getRegion,
                        Collectors.summingDouble(SalesRecord::getSalesAmount)
                ));

        salesByRegion.entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .forEach(e -> System.out.printf("%-10s: $%,12.2f%n", e.getKey(), e.getValue()));
        System.out.println();
    }

    /**
     * Identifies top performing sales representatives by total sales.
     *
     * @param topN Number of top performers to display
     */
    public void analyzeTopSalesReps(int topN) {
        System.out.println("=== ANALYSIS 2: TOP " + topN + " SALES REPRESENTATIVES ===");

        salesData.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getSalesRep,
                        Collectors.summingDouble(SalesRecord::getSalesAmount)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(topN)
                .forEach(e -> System.out.printf("%-10s: $%,12.2f%n", e.getKey(), e.getValue()));
        System.out.println();
    }

    /**
     * Analyzes product category performance with comprehensive statistics.
     */
    public void analyzeCategoryPerformance() {
        System.out.println("=== ANALYSIS 3: CATEGORY PERFORMANCE WITH STATISTICS ===");

        Map<String, DoubleSummaryStatistics> categoryStats = salesData.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getProductCategory,
                        Collectors.summarizingDouble(SalesRecord::getSalesAmount)
                ));

        categoryStats.forEach((category, stats) -> {
            System.out.printf("%-12s: %s%n", category, new SalesStats(stats));
        });
        System.out.println();
    }

    /**
     * Analyzes monthly sales trend over time.
     */
    public void analyzeMonthlyTrend() {
        System.out.println("=== ANALYSIS 4: MONTHLY SALES TREND ===");

        Map<String, Double> monthlyTrend = salesData.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getMonth,
                        TreeMap::new,
                        Collectors.summingDouble(SalesRecord::getSalesAmount)
                ));

        monthlyTrend.forEach((month, sales) ->
                System.out.printf("%s: $%,12.2f%n", month, sales));
        System.out.println();
    }

    /**
     * Performs comprehensive customer type analysis with multiple metrics.
     */
    public void analyzeCustomerTypes() {
        System.out.println("=== ANALYSIS 5: CUSTOMER TYPE ANALYSIS ===");

        Map<String, Map<String, Object>> customerAnalysis = salesData.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getCustomerType,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    Map<String, Object> metrics = new HashMap<>();
                                    metrics.put("count", list.size());
                                    metrics.put("totalSales", list.stream()
                                            .mapToDouble(SalesRecord::getSalesAmount).sum());
                                    metrics.put("avgSales", list.stream()
                                            .mapToDouble(SalesRecord::getSalesAmount)
                                            .average().orElse(0));
                                    metrics.put("avgDiscount", list.stream()
                                            .mapToDouble(SalesRecord::getDiscount)
                                            .average().orElse(0));
                                    return metrics;
                                }
                        )
                ));

        customerAnalysis.forEach((type, metrics) -> {
            System.out.printf("%-10s: Transactions=%,5d, Total=$%,12.2f, " +
                            "Avg Sale=$%,8.2f, Avg Discount=%.1f%%%n",
                    type, metrics.get("count"), metrics.get("totalSales"),
                    metrics.get("avgSales"), (Double)metrics.get("avgDiscount") * 100);
        });
        System.out.println();
    }

    /**
     * Identifies most profitable products based on profit margin.
     *
     * @param topN Number of top products to display
     */
    public void analyzeProfitableProducts(int topN) {
        System.out.println("=== ANALYSIS 6: TOP " + topN + " MOST PROFITABLE PRODUCTS ===");

        salesData.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getProductId,
                        Collectors.summingDouble(SalesRecord::getProfit)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Double>comparingByValue().reversed())
                .limit(topN)
                .forEach(e -> System.out.printf("Product %s: $%,10.2f profit%n",
                        e.getKey(), e.getValue()));
        System.out.println();
    }

    /**
     * Analyzes payment method distribution and calculates percentages.
     */
    public void analyzePaymentMethods() {
        System.out.println("=== ANALYSIS 7: PAYMENT METHOD DISTRIBUTION ===");

        Map<String, Long> paymentCounts = salesData.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getPaymentMethod,
                        Collectors.counting()
                ));

        long total = paymentCounts.values().stream()
                .mapToLong(Long::longValue)
                .sum();

        paymentCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(e -> System.out.printf("%-15s: %,6d transactions (%.1f%%)%n",
                        e.getKey(), e.getValue(), (e.getValue() * 100.0) / total));
        System.out.println();
    }

    /**
     * Performs multi-level grouping: quarterly performance by region.
     */
    public void analyzeQuarterlyPerformance() {
        System.out.println("=== ANALYSIS 8: QUARTERLY PERFORMANCE BY REGION ===");

        Map<String, Map<String, Double>> quarterlyByRegion = salesData.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getRegion,
                        Collectors.groupingBy(
                                SalesRecord::getQuarter,
                                Collectors.summingDouble(SalesRecord::getSalesAmount)
                        )
                ));

        quarterlyByRegion.forEach((region, quarters) -> {
            System.out.println(region + ":");
            quarters.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(e -> System.out.printf("  %s: $%,12.2f%n",
                            e.getKey(), e.getValue()));
        });
        System.out.println();
    }

    /**
     * Analyzes effectiveness of different sales channels.
     */
    public void analyzeSalesChannels() {
        System.out.println("=== ANALYSIS 9: SALES CHANNEL EFFECTIVENESS ===");

        Map<String, Map<String, Double>> channelMetrics = salesData.stream()
                .collect(Collectors.groupingBy(
                        SalesRecord::getSalesChannel,
                        Collectors.collectingAndThen(
                                Collectors.toList(),
                                list -> {
                                    double totalSales = list.stream()
                                            .mapToDouble(SalesRecord::getSalesAmount).sum();
                                    double avgProfit = list.stream()
                                            .mapToDouble(SalesRecord::getProfit)
                                            .average().orElse(0);
                                    return Map.of("total", totalSales, "avgProfit", avgProfit);
                                }
                        )
                ));

        channelMetrics.entrySet().stream()
                .sorted((e1, e2) -> Double.compare(
                        e2.getValue().get("total"),
                        e1.getValue().get("total")))
                .forEach(e -> System.out.printf("%-10s: Total=$%,12.2f, Avg Profit=$%,8.2f%n",
                        e.getKey(), e.getValue().get("total"),
                        e.getValue().get("avgProfit")));
        System.out.println();
    }

    /**
     * Identifies and analyzes high-value transactions above threshold.
     *
     * @param threshold Minimum sales amount to be considered high-value
     */
    public void analyzeHighValueTransactions(double threshold) {
        System.out.println("=== ANALYSIS 10: HIGH-VALUE TRANSACTIONS (>$"
                + String.format("%,.2f", threshold) + ") ===");

        List<SalesRecord> highValue = salesData.stream()
                .filter(s -> s.getSalesAmount() > threshold)
                .sorted(Comparator.comparingDouble(SalesRecord::getSalesAmount).reversed())
                .limit(10)
                .collect(Collectors.toList());

        System.out.printf("Found %d high-value transactions%n", highValue.size());
        highValue.forEach(s -> System.out.printf("Product %s: $%,10.2f (%s, Rep: %s)%n",
                s.getProductId(), s.getSalesAmount(), s.getRegion(), s.getSalesRep()));
        System.out.println();
    }

    /**
     * Analyzes correlation between discount levels and sales/profit metrics.
     */
    public void analyzeDiscountImpact() {
        System.out.println("=== ANALYSIS 11: DISCOUNT IMPACT ANALYSIS ===");

        Map<String, List<SalesRecord>> discountGroups = salesData.stream()
                .collect(Collectors.groupingBy(s -> {
                    double d = s.getDiscount();
                    if (d == 0) return "No Discount";
                    if (d <= 0.10) return "1-10%";
                    if (d <= 0.20) return "11-20%";
                    return "21%+";
                }));

        List<String> order = Arrays.asList("No Discount", "1-10%", "11-20%", "21%+");

        order.stream()
                .filter(discountGroups::containsKey)
                .forEach(group -> {
                    List<SalesRecord> records = discountGroups.get(group);
                    double avgSales = records.stream()
                            .mapToDouble(SalesRecord::getSalesAmount).average().orElse(0);
                    double avgProfit = records.stream()
                            .mapToDouble(SalesRecord::getProfit).average().orElse(0);
                    double profitMargin = avgSales > 0 ? (avgProfit / avgSales) * 100 : 0;

                    System.out.printf("%-12s: Count=%,5d, Avg Sales=$%,8.2f, " +
                                    "Avg Profit=$%,8.2f, Margin=%.1f%%%n",
                            group, records.size(), avgSales, avgProfit, profitMargin);
                });
        System.out.println();
    }

    /**
     * Displays overall summary statistics for the entire dataset.
     */
    public void displaySummary() {

        System.out.println("─".repeat(70));
        System.out.println("          OVERALL SUMMARY STATISTICS                        ");
        System.out.println("─".repeat(70));

        DoubleSummaryStatistics salesStats = salesData.stream()
                .mapToDouble(SalesRecord::getSalesAmount)
                .summaryStatistics();

        DoubleSummaryStatistics profitStats = salesData.stream()
                .mapToDouble(SalesRecord::getProfit)
                .summaryStatistics();

        System.out.println("Sales:  " + new SalesStats(salesStats));
        System.out.println("Profit: " + new SalesStats(profitStats));

        double totalProfit = profitStats.getSum();
        double totalSales = salesStats.getSum();
        System.out.printf("Overall Profit Margin: %.2f%%%n",
                (totalProfit / totalSales) * 100);
        System.out.println();
    }

    /**
     * Convenience method to run all analyses in sequence.
     */
    public void runAllAnalyses() {
        displaySummary();
        analyzeSalesByRegion();
        analyzeTopSalesReps(5);
        analyzeCategoryPerformance();
        analyzeMonthlyTrend();
        analyzeCustomerTypes();
        analyzeProfitableProducts(5);
        analyzePaymentMethods();
        analyzeQuarterlyPerformance();
        analyzeSalesChannels();
        analyzeHighValueTransactions(9000);
        analyzeDiscountImpact();
    }
}

