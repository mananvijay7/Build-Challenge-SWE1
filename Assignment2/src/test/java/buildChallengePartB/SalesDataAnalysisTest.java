package buildChallengePartB;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.lang.reflect.Field;

/**
 * Comprehensive unit test suite for Sales Data Analysis Application.
 *
 * Testing Strategy:
 * Uses in-memory test data injection via reflection to avoid file I/O issues.
 *
 * @author Manan Vijayvargiya
 * @version 1.0
 * @since 2025-11-19
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SalesDataAnalysisTest {

    private SalesDataAnalyzer analyzer;

    /**
     * Setup method - initializes analyzer with test data.
     * Runs before each test method.
     */
    @BeforeEach
    public void setUp() throws Exception {
        analyzer = new SalesDataAnalyzer();
        injectTestData(analyzer);
    }

    /**
     * Injects test data directly into analyzer using reflection.
     * This avoids file I/O issues and makes tests faster and more reliable.
     *
     * @param analyzer The analyzer to inject data into
     * @throws Exception if reflection fails
     */
    private void injectTestData(SalesDataAnalyzer analyzer) throws Exception {
        List<SalesRecord> testData = createTestSalesRecords();

        // Use reflection to set the private salesData field
        Field salesDataField = SalesDataAnalyzer.class.getDeclaredField("salesData");
        salesDataField.setAccessible(true);
        salesDataField.set(analyzer, testData);
    }

    /**
     * Creates a list of test SalesRecord objects programmatically.
     *
     * <p><b>Test Data Characteristics:</b>
     * - 10 records spanning multiple months and quarters
     * - Multiple regions (North, South, East, West)
     * - Multiple sales reps (Alice, Bob, Charlie)
     * - Multiple categories (Electronics, Furniture, Clothing, Food)
     * - Various customer types, payment methods, channels
     * - Different discount levels (0%, 5%, 10%, 15%, 20%)
     *
     * @return List of test SalesRecord objects
     */
    private List<SalesRecord> createTestSalesRecords() {
        List<SalesRecord> records = new ArrayList<>();

        records.add(createRecord("1001", "2023-01-15", "Alice", "North", 5000.00, 10,
                "Electronics", 200.00, 500.00, "New", 0.10, "Credit Card", "Online"));

        records.add(createRecord("1002", "2023-01-20", "Bob", "South", 3000.00, 5,
                "Furniture", 300.00, 600.00, "Returning", 0.05, "Cash", "Retail"));

        records.add(createRecord("1003", "2023-02-10", "Charlie", "East", 7500.00, 15,
                "Electronics", 250.00, 500.00, "New", 0.15, "Bank Transfer", "Online"));

        records.add(createRecord("1004", "2023-02-15", "Alice", "West", 4200.00, 7,
                "Clothing", 300.00, 600.00, "Returning", 0.20, "Credit Card", "Retail"));

        records.add(createRecord("1005", "2023-03-05", "Bob", "North", 9000.00, 20,
                "Electronics", 200.00, 450.00, "New", 0.00, "Cash", "Online"));

        records.add(createRecord("1006", "2023-03-20", "Charlie", "South", 2500.00, 5,
                "Food", 200.00, 500.00, "Returning", 0.10, "Bank Transfer", "Retail"));

        records.add(createRecord("1007", "2023-04-12", "Alice", "East", 6000.00, 12,
                "Furniture", 250.00, 500.00, "New", 0.05, "Credit Card", "Online"));

        records.add(createRecord("1008", "2023-05-18", "Bob", "West", 8500.00, 17,
                "Electronics", 300.00, 500.00, "Returning", 0.10, "Cash", "Retail"));

        records.add(createRecord("1009", "2023-06-22", "Charlie", "North", 4500.00, 9,
                "Clothing", 250.00, 500.00, "New", 0.15, "Bank Transfer", "Online"));

        records.add(createRecord("1010", "2023-07-30", "Alice", "South", 10000.00, 25,
                "Electronics", 200.00, 400.00, "Returning", 0.00, "Credit Card", "Retail"));

        return records;
    }

    /**
     * Helper method to create a SalesRecord from string parameters.
     */
    private SalesRecord createRecord(String productId, String saleDate, String salesRep,
                                     String region, double salesAmount, int quantitySold,
                                     String category, double unitCost, double unitPrice,
                                     String customerType, double discount,
                                     String paymentMethod, String channel) {
        String[] fields = {
                productId, saleDate, salesRep, region, String.valueOf(salesAmount),
                String.valueOf(quantitySold), category, String.valueOf(unitCost),
                String.valueOf(unitPrice), customerType, String.valueOf(discount),
                paymentMethod, channel
        };
        return new SalesRecord(fields);
    }

    // ==================== SALESRECORD UNIT TESTS ====================

    /**
     * Test Case 1: SalesRecord constructor with valid data.
     */
    @Test
    @Order(1)
    @DisplayName("Test SalesRecord Constructor - Valid Data")
    public void testSalesRecordConstructor_ValidData() {
        String[] fields = {
                "1001", "2023-01-15", "Alice", "North", "5000.00", "10",
                "Electronics", "200.00", "500.00", "New", "0.10",
                "Credit Card", "Online"
        };

        SalesRecord record = new SalesRecord(fields);

        assertAll("SalesRecord Fields",
                () -> assertEquals("1001", record.getProductId()),
                () -> assertEquals(LocalDate.of(2023, 1, 15), record.getSaleDate()),
                () -> assertEquals("Alice", record.getSalesRep()),
                () -> assertEquals("North", record.getRegion()),
                () -> assertEquals(5000.00, record.getSalesAmount(), 0.01),
                () -> assertEquals(10, record.getQuantitySold()),
                () -> assertEquals("Electronics", record.getProductCategory()),
                () -> assertEquals(200.00, record.getUnitCost(), 0.01),
                () -> assertEquals(500.00, record.getUnitPrice(), 0.01),
                () -> assertEquals("New", record.getCustomerType()),
                () -> assertEquals(0.10, record.getDiscount(), 0.01),
                () -> assertEquals("Credit Card", record.getPaymentMethod()),
                () -> assertEquals("Online", record.getSalesChannel())
        );
    }

    /**
     * Test Case 2: SalesRecord profit calculation.
     */
    @Test
    @Order(2)
    @DisplayName("Test SalesRecord Profit Calculation")
    public void testSalesRecordProfitCalculation() {
        String[] fields = {
                "1001", "2023-01-15", "Alice", "North", "5000.00", "10",
                "Electronics", "200.00", "500.00", "New", "0.10",
                "Credit Card", "Online"
        };

        SalesRecord record = new SalesRecord(fields);

        // Profit = 5000 - (200 * 10) = 5000 - 2000 = 3000
        assertEquals(3000.00, record.getProfit(), 0.01);
    }

    /**
     * Test Case 3: SalesRecord profit margin calculation.
     */
    @Test
    @Order(3)
    @DisplayName("Test SalesRecord Profit Margin Calculation")
    public void testSalesRecordProfitMargin() {
        String[] fields = {
                "1001", "2023-01-15", "Alice", "North", "5000.00", "10",
                "Electronics", "200.00", "500.00", "New", "0.10",
                "Credit Card", "Online"
        };

        SalesRecord record = new SalesRecord(fields);

        // Profit Margin = (3000 / 5000) * 100 = 60%
        assertEquals(60.00, record.getProfitMargin(), 0.01);
    }

    /**
     * Test Case 4: SalesRecord quarter calculation.
     */
    @Test
    @Order(4)
    @DisplayName("Test SalesRecord Quarter Calculation")
    public void testSalesRecordQuarter() {
        String[][] testCases = {
                {"1001", "2023-01-15", "Alice", "North", "5000.00", "10",
                        "Electronics", "200.00", "500.00", "New", "0.10",
                        "Credit Card", "Online"}, // Q1
                {"1002", "2023-04-15", "Bob", "South", "3000.00", "5",
                        "Furniture", "300.00", "600.00", "Returning", "0.05",
                        "Cash", "Retail"}, // Q2
                {"1003", "2023-07-15", "Charlie", "East", "7500.00", "15",
                        "Electronics", "250.00", "500.00", "New", "0.15",
                        "Bank Transfer", "Online"}, // Q3
                {"1004", "2023-10-15", "Alice", "West", "4200.00", "7",
                        "Clothing", "300.00", "600.00", "Returning", "0.20",
                        "Credit Card", "Retail"} // Q4
        };

        String[] expectedQuarters = {"Q1", "Q2", "Q3", "Q4"};

        for (int i = 0; i < testCases.length; i++) {
            SalesRecord record = new SalesRecord(testCases[i]);
            assertEquals(expectedQuarters[i], record.getQuarter());
        }
    }

    /**
     * Test Case 5: SalesRecord invalid date format.
     */
    @Test
    @Order(5)
    @DisplayName("Test SalesRecord Invalid Date Format")
    public void testSalesRecordInvalidDate() {
        String[] fields = {
                "1001", "15-01-2023", "Alice", "North", "5000.00", "10",
                "Electronics", "200.00", "500.00", "New", "0.10",
                "Credit Card", "Online"
        };

        assertThrows(java.time.format.DateTimeParseException.class,
                () -> new SalesRecord(fields));
    }

    /**
     * Test Case 6: SalesRecord invalid numeric field.
     */
    @Test
    @Order(6)
    @DisplayName("Test SalesRecord Invalid Numeric Field")
    public void testSalesRecordInvalidNumeric() {
        String[] fields = {
                "1001", "2023-01-15", "Alice", "North", "invalid", "10",
                "Electronics", "200.00", "500.00", "New", "0.10",
                "Credit Card", "Online"
        };

        assertThrows(NumberFormatException.class,
                () -> new SalesRecord(fields));
    }

    // ==================== SALESSTATS UNIT TESTS ====================

    /**
     * Test Case 7: SalesStats with DoubleSummaryStatistics.
     */
    @Test
    @Order(7)
    @DisplayName("Test SalesStats Formatting")
    public void testSalesStatsFormatting() {
        DoubleSummaryStatistics stats = new DoubleSummaryStatistics();
        stats.accept(1000.00);
        stats.accept(2000.00);
        stats.accept(3000.00);

        SalesStats salesStats = new SalesStats(stats);
        String result = salesStats.toString();

        assertTrue(result.contains("Count: 3"));
        assertTrue(result.contains("Total: $6000.00"));
        assertTrue(result.contains("Avg: $2000.00"));
        assertTrue(result.contains("Min: $1000.00"));
        assertTrue(result.contains("Max: $3000.00"));
    }

    // ==================== SALESDATAANALYZER INTEGRATION TESTS ====================

    /**
     * Test Case 8: Verify test data injection worked.
     */
    @Test
    @Order(8)
    @DisplayName("Test Data Injection")
    public void testDataInjection() {
        assertEquals(10, analyzer.getRecordCount());
    }

    /**
     * Test Case 9: Sales by region analysis.
     */
    @Test
    @Order(9)
    @DisplayName("Test Sales By Region Analysis")
    public void testAnalyzeSalesByRegion() {
        // Capture console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        analyzer.analyzeSalesByRegion();

        System.setOut(originalOut);
        String output = outContent.toString();

        // Verify output contains expected regions
        assertTrue(output.contains("North"));
        assertTrue(output.contains("South"));
        assertTrue(output.contains("East"));
        assertTrue(output.contains("West"));
        assertTrue(output.contains("ANALYSIS 1"));
    }

    /**
     * Test Case 10: Top sales representatives analysis.
     */
    @Test
    @Order(10)
    @DisplayName("Test Top Sales Reps Analysis")
    public void testAnalyzeTopSalesReps() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        analyzer.analyzeTopSalesReps(3);

        System.setOut(originalOut);
        String output = outContent.toString();

        assertTrue(output.contains("Alice"));
        assertTrue(output.contains("Bob"));
        assertTrue(output.contains("Charlie"));
        assertTrue(output.contains("TOP 3"));
    }

    /**
     * Test Case 11: Category performance analysis.
     */
    @Test
    @Order(11)
    @DisplayName("Test Category Performance Analysis")
    public void testAnalyzeCategoryPerformance() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        analyzer.analyzeCategoryPerformance();

        System.setOut(originalOut);
        String output = outContent.toString();

        assertTrue(output.contains("Electronics"));
        assertTrue(output.contains("Furniture"));
        assertTrue(output.contains("Clothing"));
        assertTrue(output.contains("Count:"));
        assertTrue(output.contains("Total:"));
    }

    /**
     * Test Case 12: Monthly trend analysis.
     */
    @Test
    @Order(12)
    @DisplayName("Test Monthly Trend Analysis")
    public void testAnalyzeMonthlyTrend() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        analyzer.analyzeMonthlyTrend();

        System.setOut(originalOut);
        String output = outContent.toString();

        assertTrue(output.contains("2023-01"));
        assertTrue(output.contains("2023-02"));
        assertTrue(output.contains("MONTHLY SALES TREND"));
    }

    /**
     * Test Case 13: Customer type analysis.
     */
    @Test
    @Order(13)
    @DisplayName("Test Customer Type Analysis")
    public void testAnalyzeCustomerTypes() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        analyzer.analyzeCustomerTypes();

        System.setOut(originalOut);
        String output = outContent.toString();

        assertTrue(output.contains("New"));
        assertTrue(output.contains("Returning"));
        assertTrue(output.contains("Transactions="));
        assertTrue(output.contains("Discount"));
    }

    /**
     * Test Case 14: Profitable products analysis.
     */
    @Test
    @Order(14)
    @DisplayName("Test Profitable Products Analysis")
    public void testAnalyzeProfitableProducts() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        analyzer.analyzeProfitableProducts(5);

        System.setOut(originalOut);
        String output = outContent.toString();

        assertTrue(output.contains("Product"));
        assertTrue(output.contains("profit"));
        assertTrue(output.contains("TOP 5"));
    }

    /**
     * Test Case 15: Payment method distribution.
     */
    @Test
    @Order(15)
    @DisplayName("Test Payment Methods Analysis")
    public void testAnalyzePaymentMethods() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        analyzer.analyzePaymentMethods();

        System.setOut(originalOut);
        String output = outContent.toString();

        assertTrue(output.contains("Credit Card"));
        assertTrue(output.contains("Cash"));
        assertTrue(output.contains("Bank Transfer"));
        assertTrue(output.contains("transactions"));
    }

    /**
     * Test Case 16: Quarterly performance analysis.
     */
    @Test
    @Order(16)
    @DisplayName("Test Quarterly Performance Analysis")
    public void testAnalyzeQuarterlyPerformance() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        analyzer.analyzeQuarterlyPerformance();

        System.setOut(originalOut);
        String output = outContent.toString();

        assertTrue(output.contains("Q1") || output.contains("Q2") ||
                output.contains("Q3") || output.contains("Q4"));
        assertTrue(output.contains("QUARTERLY"));
    }

    /**
     * Test Case 17: Sales channel effectiveness.
     */
    @Test
    @Order(17)
    @DisplayName("Test Sales Channels Analysis")
    public void testAnalyzeSalesChannels() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        analyzer.analyzeSalesChannels();

        System.setOut(originalOut);
        String output = outContent.toString();

        assertTrue(output.contains("Online"));
        assertTrue(output.contains("Retail"));
        assertTrue(output.contains("CHANNEL"));
    }

    /**
     * Test Case 18: High-value transactions.
     */
    @Test
    @Order(18)
    @DisplayName("Test High Value Transactions Analysis")
    public void testAnalyzeHighValueTransactions() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        analyzer.analyzeHighValueTransactions(8000);

        System.setOut(originalOut);
        String output = outContent.toString();

        assertTrue(output.contains("Found"));
        assertTrue(output.contains("HIGH-VALUE"));
    }

    /**
     * Test Case 19: Discount impact analysis.
     */
    @Test
    @Order(19)
    @DisplayName("Test Discount Impact Analysis")
    public void testAnalyzeDiscountImpact() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        analyzer.analyzeDiscountImpact();

        System.setOut(originalOut);
        String output = outContent.toString();

        assertTrue(output.contains("Discount") || output.contains("No Discount"));
        assertTrue(output.contains("Avg Sales"));
    }

    /**
     * Test Case 20: Overall summary statistics.
     */
    @Test
    @Order(20)
    @DisplayName("Test Summary Display")
    public void testDisplaySummary() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outContent));

        analyzer.displaySummary();

        System.setOut(originalOut);
        String output = outContent.toString();

        assertTrue(output.contains("SUMMARY STATISTICS"));
        assertTrue(output.contains("Sales:"));
        assertTrue(output.contains("Profit:"));
        assertTrue(output.contains("Profit Margin"));
    }

    // ==================== EDGE CASE TESTS ====================

    /**
     * Test Case 21: Empty dataset handling.
     */
    @Test
    @Order(21)
    @DisplayName("Test Empty Dataset Handling")
    public void testEmptyDataset() throws Exception {
        SalesDataAnalyzer emptyAnalyzer = new SalesDataAnalyzer();

        // Inject empty list
        Field salesDataField = SalesDataAnalyzer.class.getDeclaredField("salesData");
        salesDataField.setAccessible(true);
        salesDataField.set(emptyAnalyzer, new ArrayList<SalesRecord>());

        assertEquals(0, emptyAnalyzer.getRecordCount());
        assertDoesNotThrow(() -> emptyAnalyzer.runAllAnalyses());
    }

    /**
     * Test Case 22: Single record dataset.
     */
    @Test
    @Order(22)
    @DisplayName("Test Single Record Dataset")
    public void testSingleRecordDataset() throws Exception {
        SalesDataAnalyzer singleAnalyzer = new SalesDataAnalyzer();

        List<SalesRecord> singleRecord = new ArrayList<>();
        singleRecord.add(createRecord("1001", "2023-01-15", "Alice", "North",
                5000.00, 10, "Electronics", 200.00, 500.00, "New", 0.10,
                "Credit Card", "Online"));

        Field salesDataField = SalesDataAnalyzer.class.getDeclaredField("salesData");
        salesDataField.setAccessible(true);
        salesDataField.set(singleAnalyzer, singleRecord);

        assertEquals(1, singleAnalyzer.getRecordCount());
        assertDoesNotThrow(() -> singleAnalyzer.runAllAnalyses());
    }

    /**
     * Test Case 23: Run all analyses integration test.
     */
    @Test
    @Order(23)
    @DisplayName("Test Run All Analyses")
    public void testRunAllAnalyses() {
        assertDoesNotThrow(() -> analyzer.runAllAnalyses());
    }

    /**
     * Test Case 24: Zero sales amount handling.
     */
    @Test
    @Order(24)
    @DisplayName("Test Zero Sales Amount Handling")
    public void testZeroSalesAmount() {
        String[] fields = {
                "1001", "2023-01-15", "Alice", "North", "0.00", "10",
                "Electronics", "200.00", "500.00", "New", "0.10",
                "Credit Card", "Online"
        };

        SalesRecord record = new SalesRecord(fields);

        assertEquals(0.00, record.getSalesAmount(), 0.01);
        assertEquals(0.00, record.getProfitMargin(), 0.01);
    }

    /**
     * Test Case 25: Month format verification.
     */
    @Test
    @Order(25)
    @DisplayName("Test Month Format")
    public void testMonthFormat() {
        String[] fields = {
                "1001", "2023-01-15", "Alice", "North", "5000.00", "10",
                "Electronics", "200.00", "500.00", "New", "0.10",
                "Credit Card", "Online"
        };

        SalesRecord record = new SalesRecord(fields);
        assertEquals("2023-01", record.getMonth());
    }
}
