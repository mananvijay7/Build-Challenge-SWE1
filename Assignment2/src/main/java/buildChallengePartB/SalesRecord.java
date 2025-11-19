package buildChallengePartB;

import java.time.*;
import java.time.format.*;

/**
 * Represents a single sales transaction record.
 *
 * This is an immutable value object that encapsulates all fields from the CSV.
 * Immutability ensures thread-safety and prevents accidental modifications during
 * stream operations.
 *
 * Design Decision: All fields are final and set through constructor,
 * following immutable object pattern for safer functional programming.
 */
class SalesRecord {
    // Core fields from CSV
    private final String productId;
    private final LocalDate saleDate;
    private final String salesRep;
    private final String region;
    private final double salesAmount;
    private final int quantitySold;
    private final String productCategory;
    private final double unitCost;
    private final double unitPrice;
    private final String customerType;
    private final double discount;
    private final String paymentMethod;
    private final String salesChannel;

    /**
     * Constructs a SalesRecord from CSV fields.
     *
     * <p><b>Approach:</b> Constructor performs parsing and validation, throwing
     * exceptions for invalid data. This ensures all SalesRecord objects are valid.
     *
     * @param fields Array of strings from CSV line (tab-separated)
     * @throws DateTimeParseException if date format is invalid
     * @throws NumberFormatException if numeric fields are invalid
     * @throws ArrayIndexOutOfBoundsException if insufficient fields
     */
    public SalesRecord(String[] fields) {
        this.productId = fields[0].trim();
        this.saleDate = LocalDate.parse(fields[1].trim());
        this.salesRep = fields[2].trim();
        this.region = fields[3].trim();
        this.salesAmount = Double.parseDouble(fields[4].trim());
        this.quantitySold = Integer.parseInt(fields[5].trim());
        this.productCategory = fields[6].trim();
        this.unitCost = Double.parseDouble(fields[7].trim());
        this.unitPrice = Double.parseDouble(fields[8].trim());
        this.customerType = fields[9].trim();
        this.discount = Double.parseDouble(fields[10].trim());
        this.paymentMethod = fields[11].trim();
        this.salesChannel = fields[12].trim();
    }

    // Getters - Essential for method references in streams
    public String getProductId() { return productId; }
    public LocalDate getSaleDate() { return saleDate; }
    public String getSalesRep() { return salesRep; }
    public String getRegion() { return region; }
    public double getSalesAmount() { return salesAmount; }
    public int getQuantitySold() { return quantitySold; }
    public String getProductCategory() { return productCategory; }
    public double getUnitCost() { return unitCost; }
    public double getUnitPrice() { return unitPrice; }
    public String getCustomerType() { return customerType; }
    public double getDiscount() { return discount; }
    public String getPaymentMethod() { return paymentMethod; }
    public String getSalesChannel() { return salesChannel; }

    /**
     * Calculates profit for this transaction.
     *
     * @return profit amount in dollars
     */
    public double getProfit() {
        return salesAmount - (unitCost * quantitySold);
    }

    /**
     * Calculates profit margin percentage.
     *
     * @return profit margin as percentage (0-100)
     */
    public double getProfitMargin() {
        return salesAmount > 0 ? (getProfit() / salesAmount) * 100 : 0;
    }

    /**
     * Determines the quarter of the year for this sale.
     *
     * @return Quarter string (Q1, Q2, Q3, or Q4)
     */
    public String getQuarter() {
        int month = saleDate.getMonthValue();
        return "Q" + ((month - 1) / 3 + 1);
    }

    /**
     * Gets the month in YYYY-MM format for time-series analysis.
     *
     * @return formatted month string
     */
    public String getMonth() {
        return saleDate.format(DateTimeFormatter.ofPattern("yyyy-MM"));
    }
}
