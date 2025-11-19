package buildChallengePartB;

import java.io.IOException;

public class SalesDataAnalysisApp {

    /**
     * Configuration: Relative file path to the CSV data file.
     *
     * Location: Place sales_data.csv in your project root directory
     * (same level as src/ folder).
     *
     */
    private static final String CSV_FILE_PATH = "/sales_data.csv";

    /**
     * Main method - Application entry point.
     *
     */
    public static void main(String[] args) {
        // Display application header
        printHeader();

        try {
            // Step 1: Create analyzer instance
            SalesDataAnalyzer analyzer = new SalesDataAnalyzer();

            // Step 2: Load data from CSV file
            System.out.println(" Loading sales data from CSV...");
            System.out.println("   File: " + CSV_FILE_PATH);

            int recordCount = analyzer.loadDataFromFile(CSV_FILE_PATH);

            System.out.println(" Successfully loaded " + recordCount + " sales records");
            System.out.println();

            // Step 3: Execute all analyses
            System.out.println(" Starting comprehensive sales data analysis...\n");

            analyzer.runAllAnalyses();

            // Step 4: Display completion message
            System.out.println("=".repeat(70));
            System.out.println("Analysis complete! All 11 analyses executed successfully.");

        } catch (IOException e) {
            // Handle file-related errors
            System.err.println("\n ERROR: Unable to read CSV file");
            System.err.println("   Reason: " + e.getMessage());
            System.err.println("   File path: " + CSV_FILE_PATH);

        } catch (Exception e) {
            // Handle unexpected errors
            System.err.println("\n UNEXPECTED ERROR during analysis");
            System.err.println("   Message: " + e.getMessage());
            System.err.println("\n Stack trace:");
            e.printStackTrace();
        }
    }

    /**
     * Prints application header with branding and information.
     */
    private static void printHeader() {
        System.out.println("─".repeat(70));
        System.out.println("           SALES DATA ANALYSIS APPLICATION                      ");
        System.out.println("─".repeat(70));
    }

}
