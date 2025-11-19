# Build-Challenge-SWE1

A comprehensive demonstration of advanced Java programming concepts including concurrent programming, functional programming, and data analysis using modern Java features.

## 📚 Table of Contents
- [Assignment 1: Producer-Consumer Pattern](#assignment-1-producer-consumer-pattern)
- [Assignment 2: Sales Data Analysis with Streams](#assignment-2-sales-data-analysis-with-streams)
- [Prerequisites](#prerequisites)
- [Project Structure](#project-structure)

---

## Assignment 1: Producer-Consumer Pattern

A robust implementation of the classic **Producer-Consumer pattern** demonstrating thread synchronization, concurrent programming, and inter-thread communication mechanisms in Java.

### 🎯 Overview

This project implements a **thread-safe Producer-Consumer pattern** where:
- A **Producer** thread reads items from a source container and places them into a shared queue
- A **Consumer** thread retrieves items from the queue and stores them in a destination container
- Both threads coordinate using **wait/notify mechanisms** to handle queue full/empty conditions

### 🔑 Core Concepts Demonstrated

#### 1. **Thread Synchronization**
- Explicit locking using `ReentrantLock`
- Condition variables for coordinated waiting
- Thread-safe operations on shared data structures

#### 2. **Concurrent Programming**
- Multiple threads running simultaneously
- Safe concurrent data transfer between threads
- Proper thread lifecycle management

#### 3. **Blocking Queue Implementation**
- Custom blocking queue with bounded capacity
- Thread-safe `put()` and `take()` operations
- Automatic blocking when queue is full or empty

#### 4. **Wait/Notify Mechanism**
- `Condition.await()` - Threads wait when conditions aren't met
- `Condition.signal()` - Threads notify each other when state changes
- Prevents busy-waiting and ensures efficient CPU utilization

### 🏗️ Architecture

```
┌─────────────┐         ┌──────────────────┐         ┌─────────────┐
│   Source    │ ──────> │  Blocking Queue  │ ──────> │ Destination │
│  Container  │         │   (Capacity: 3)  │         │  Container  │
└─────────────┘         └──────────────────┘         └─────────────┘
      ↓                          ↓                           ↓
  [Producer]  ── put() ──>  [Shared Queue]  ── take() ──> [Consumer]
   (Thread)                   (Locked)                    (Thread)
```

### 🚀 Running Assignment 1

#### Method 1: Using Maven
```bash
cd Assignment1
mvn clean install
mvn exec:java -Dexec.mainClass="buildchallengePartA.ProducerConsumerSystem"
```

#### Method 2: Using JAR
```bash
cd Assignment1
mvn clean package
java -jar target/buildchallengePartA-1.0-SNAPSHOT.jar
```

### 🧪 Running Tests - Assignment 1

```bash
cd Assignment1
mvn test
```

#### Test Coverage:
- **CustomBlockingQueue**: Basic operations, blocking behavior, concurrency
- **Producer/Consumer**: Thread coordination, edge cases

### 📊 Sample Output - Assignment 1

```
=== PRODUCER-CONSUMER DEMONSTRATION ===
Source items: 10
Queue capacity: 3
=====================================


[PRODUCER] Reading from source: Item-1
Consumer-Thread - Queue EMPTY, waiting...
Producer-Thread - Added: Item-1 | Queue size: 1
Consumer-Thread - Removed: Item-1 | Queue size: 0

[CONSUMER] Processing: Item-1

[PRODUCER] Reading from source: Item-2
Producer-Thread - Added: Item-2 | Queue size: 1
Consumer-Thread - Removed: Item-2 | Queue size: 0

[CONSUMER] Processing: Item-2

[PRODUCER] Reading from source: Item-3
Producer-Thread - Added: Item-3 | Queue size: 1
Consumer-Thread - Removed: Item-3 | Queue size: 0

[CONSUMER] Processing: Item-3

[PRODUCER] Reading from source: Item-4
Producer-Thread - Added: Item-4 | Queue size: 1

[PRODUCER] Reading from source: Item-5
Producer-Thread - Added: Item-5 | Queue size: 2
Consumer-Thread - Removed: Item-4 | Queue size: 1

[CONSUMER] Processing: Item-4

[PRODUCER] Reading from source: Item-6
Producer-Thread - Added: Item-6 | Queue size: 2
Consumer-Thread - Removed: Item-5 | Queue size: 1

[CONSUMER] Processing: Item-5

[PRODUCER] Reading from source: Item-7
Producer-Thread - Added: Item-7 | Queue size: 2

[PRODUCER] Reading from source: Item-8
Producer-Thread - Added: Item-8 | Queue size: 3
Consumer-Thread - Removed: Item-6 | Queue size: 2

[CONSUMER] Processing: Item-6

[PRODUCER] Reading from source: Item-9
Producer-Thread - Added: Item-9 | Queue size: 3
Consumer-Thread - Removed: Item-7 | Queue size: 2

[CONSUMER] Processing: Item-7

[PRODUCER] Reading from source: Item-10
Producer-Thread - Added: Item-10 | Queue size: 3

[PRODUCER] Finished - All items produced
Consumer-Thread - Removed: Item-8 | Queue size: 2

[CONSUMER] Processing: Item-8
Consumer-Thread - Removed: Item-9 | Queue size: 1

[CONSUMER] Processing: Item-9
Consumer-Thread - Removed: Item-10 | Queue size: 0

[CONSUMER] Processing: Item-10

[CONSUMER] Finished - All items consumed

=== RESULTS ===
Source container size: 10
Destination container size: 10

Destination contents:
  Item-1
  Item-2
  Item-3
  Item-4
  Item-5
  Item-6
  Item-7
  Item-8
  Item-9
  Item-10

Transfer successful: true
```

---

## Assignment 2: Sales Data Analysis with Streams

A comprehensive **data analysis application** demonstrating functional programming paradigms, Java Streams API, and advanced data aggregation techniques on sales data.

### 🎯 Overview

This project analyzes sales data from CSV files using:
- **Java Streams API** for efficient data processing
- **Lambda expressions** for concise functional code
- **Collectors** for complex aggregations
- **Functional programming** paradigms throughout

### 🔑 Core Concepts Demonstrated

#### 1. **Functional Programming**
- Lambda expressions and method references
- Higher-order functions
- Immutable data structures
- Pure functions for data transformation

#### 2. **Stream Operations**
- **Intermediate operations**: `filter()`, `map()`, `sorted()`, `limit()`
- **Terminal operations**: `collect()`, `forEach()`, `reduce()`
- Complex multi-level grouping and aggregation
- Parallel stream capability

#### 3. **Data Aggregation**
- `groupingBy()` for single and multi-level grouping
- `summingDouble()`, `counting()`, `averagingDouble()`
- `summarizingDouble()` for comprehensive statistics
- Custom collectors with `collectingAndThen()`

#### 4. **Lambda Expressions**
- Simple predicates: `s -> s.getSalesAmount() > threshold`
- Complex multi-statement lambdas
- Functional interfaces usage
- Comparator composition

### 🏗️ Architecture

```
┌──────────────┐
│  CSV File    │
│ (sales_data) │
└──────┬───────┘
       │ Load & Parse
       ↓
┌──────────────────────┐
│  SalesDataAnalyzer   │  ← Business Logic Layer
│  - 11 Analysis Types │
└──────────┬───────────┘
           │ Stream Processing
           ↓
┌─────────────────────────┐
│  Analysis Results       │
│  - Regional Performance │
│  - Top Sales Reps       │
│  - Category Stats       │
│  - Trend Analysis       │
│  - Customer Insights    │
└─────────────────────────┘
```

#### Key Components:

1. **SalesRecord**: Immutable value object representing a transaction
2. **SalesDataAnalyzer**: Core analysis engine with 11 analytical methods
3. **SalesStats**: Statistical summary wrapper
4. **SalesDataAnalysisApp**: Application entry point with configuration

### 📊 Analysis Types (11 Total)

1. **Sales by Region** - Geographic performance analysis
2. **Top Sales Representatives** - Individual performance ranking
3. **Category Performance** - Product category statistics
4. **Monthly Trend** - Time-series sales analysis
5. **Customer Type Analysis** - New vs. Returning customers
6. **Profitable Products** - Profit margin analysis
7. **Payment Methods** - Payment distribution analysis
8. **Quarterly Performance** - Seasonal trends by region
9. **Sales Channels** - Online vs. Retail effectiveness
10. **High-Value Transactions** - Large deal identification
11. **Discount Impact** - Discount correlation analysis

### 🚀 Running Assignment 2

#### Setup:
1. **Download the dataset** from [Kaggle Sales Dataset](https://www.kaggle.com/datasets/vinothkannaece/sales-dataset)
2. **Place `sales_data.csv`** in the Assignment2 project root directory (same level as `pom.xml`)
3. **Verify CSV format**: Should be tab-delimited with 14 columns

#### File Location:
```
Assignment2/
├── pom.xml
├── sales_data.csv          ← Placed CSV here
└── src/
```

#### Method 1: Using Maven
```bash
cd Assignment2
mvn clean compile
mvn exec:java -Dexec.mainClass="buildchallengePartB.SalesDataAnalysisApp"
```

#### Method 2: Direct Java Execution
```bash
cd Assignment2/src/main/java
javac buildchallengePartB/*.java
java buildchallengePartB.SalesDataAnalysisApp
```

#### Method 3: Using IDE
1. Open `SalesDataAnalysisApp.java`
2. Right-click → Run

### 🧪 Running Tests - Assignment 2

```bash
cd Assignment2
mvn test
```

#### Test Coverage (25 Test Cases):
- **SalesRecord Tests**: Constructor, profit calculation, quarter determination
- **Analysis Tests**: All 11 analysis methods verified
- **Edge Cases**: Empty data, single records, zero values

### 📊 Sample Output - Assignment 2

```
──────────────────────────────────────────────────────────────────────
           SALES DATA ANALYSIS APPLICATION                      
──────────────────────────────────────────────────────────────────────
 Loading sales data from CSV...
   File: /sales_data.csv
 Successfully loaded 1000 sales records

 Starting comprehensive sales data analysis...

──────────────────────────────────────────────────────────────────────
          OVERALL SUMMARY STATISTICS                        
──────────────────────────────────────────────────────────────────────
Sales:  Count: 1000, Total: $5019265.23, Avg: $5019.27, Min: $100.12, Max: $9989.04
Profit: Count: 1000, Total: $-58822828.41, Avg: $-58822.83, Min: $-235588.27, Max: $8865.37
Overall Profit Margin: -1171.94%

=== ANALYSIS 1: SALES BY REGION ===
North     : $1,369,612.51
East      : $1,259,792.93
West      : $1,235,608.93
South     : $1,154,250.86

=== ANALYSIS 2: TOP 5 SALES REPRESENTATIVES ===
David     : $1,141,737.36
Bob       : $1,080,990.63
Eve       : $  970,183.99
Alice     : $  965,541.77
Charlie   : $  860,811.48

=== ANALYSIS 3: CATEGORY PERFORMANCE WITH STATISTICS ===
Clothing    : Count: 268, Total: $1313474.36, Avg: $4901.02, Min: $100.12, Max: $9972.66
Electronics : Count: 246, Total: $1243499.64, Avg: $5054.88, Min: $189.48, Max: $9914.15
Furniture   : Count: 260, Total: $1260517.69, Avg: $4848.14, Min: $113.40, Max: $9976.52
Food        : Count: 226, Total: $1201773.54, Avg: $5317.58, Min: $114.59, Max: $9989.04

=== ANALYSIS 4: MONTHLY SALES TREND ===
2023-01: $  476,092.36
2023-02: $  368,919.36
2023-03: $  402,638.77
2023-04: $  438,992.61
2023-05: $  389,078.76
2023-06: $  418,458.34
2023-07: $  374,242.88
2023-08: $  443,171.28
2023-09: $  367,837.60
2023-10: $  460,378.78
2023-11: $  467,482.90
2023-12: $  392,643.58
2024-01: $   19,328.01

=== ANALYSIS 5: CUSTOMER TYPE ANALYSIS ===
New       : Transactions=  504, Total=$2,506,258.30, Avg Sale=$4,972.73, Avg Discount=15.2%
Returning : Transactions=  496, Total=$2,513,006.93, Avg Sale=$5,066.55, Avg Discount=15.3%

=== ANALYSIS 6: TOP 5 MOST PROFITABLE PRODUCTS ===
Product 1043: $-154,359.67 profit
Product 1031: $-196,321.98 profit
Product 1011: $-207,960.78 profit
Product 1065: $-232,256.14 profit
Product 1018: $-238,172.02 profit

=== ANALYSIS 7: PAYMENT METHOD DISTRIBUTION ===
Credit Card    :    345 transactions (34.5%)
Bank Transfer  :    342 transactions (34.2%)
Cash           :    313 transactions (31.3%)

=== ANALYSIS 8: QUARTERLY PERFORMANCE BY REGION ===
West:
  Q1: $  318,078.77
  Q2: $  290,198.72
  Q3: $  277,917.04
  Q4: $  349,414.40
South:
  Q1: $  278,357.03
  Q2: $  332,733.12
  Q3: $  248,014.27
  Q4: $  295,146.44
North:
  Q1: $  301,083.07
  Q2: $  354,263.41
  Q3: $  370,325.54
  Q4: $  343,940.49
East:
  Q1: $  369,459.63
  Q2: $  269,334.46
  Q3: $  288,994.91
  Q4: $  332,003.93

=== ANALYSIS 9: SALES CHANNEL EFFECTIVENESS ===
Retail    : Total=$2,560,431.30, Avg Profit=$-56,290.96
Online    : Total=$2,458,833.93, Avg Profit=$-61,479.22

=== ANALYSIS 10: HIGH-VALUE TRANSACTIONS (>$9,000.00) ===
Found 10 high-value transactions
Product 1036: $  9,989.04 (North, Rep: David)
Product 1050: $  9,976.52 (North, Rep: David)
Product 1079: $  9,972.66 (North, Rep: Alice)
Product 1075: $  9,972.11 (North, Rep: Charlie)
Product 1016: $  9,961.96 (South, Rep: David)
Product 1063: $  9,956.75 (North, Rep: Bob)
Product 1099: $  9,948.71 (East, Rep: Alice)
Product 1089: $  9,933.22 (West, Rep: Eve)
Product 1015: $  9,914.15 (South, Rep: David)
Product 1010: $  9,907.72 (North, Rep: Charlie)

=== ANALYSIS 11: DISCOUNT IMPACT ANALYSIS ===
No Discount : Count=   16, Avg Sales=$4,511.50, Avg Profit=$-48,000.50, Margin=-1064.0%
1-10%       : Count=  334, Avg Sales=$4,969.69, Avg Profit=$-62,652.90, Margin=-1260.7%
11-20%      : Count=  309, Avg Sales=$4,966.84, Avg Profit=$-55,471.63, Margin=-1116.8%
21%+        : Count=  341, Avg Sales=$5,139.15, Avg Profit=$-58,615.89, Margin=-1140.6%

======================================================================
Analysis complete! All 11 analyses executed successfully.

```

### 🎨 Design Decisions - Assignment 2

#### CSV File Selection & Justification

**Chosen Dataset**: Kaggle Sales Dataset (1,000 transactions)

**Why This Dataset?**
1. **Real-World Structure**: Contains typical sales transaction fields found in business systems
2. **Appropriate Size**: 1,000 records - large enough to demonstrate stream efficiency, small enough for quick testing
3. **Data Variety**: Multiple dimensions (regions, reps, categories, dates) enable diverse analytical queries
4. **Time Series Data**: Spans full year (2023) enabling trend and seasonal analysis
5. **Quality Data**: Clean, consistent format with proper data types

**Dataset Fields (14 columns)**:
```
Product_ID       - Unique product identifier (1001-1100)
Sale_Date        - Transaction date (YYYY-MM-DD format)
Sales_Rep        - Sales representative (Alice, Bob, Charlie, David, Eve)
Region           - Geographic region (North, South, East, West)
Sales_Amount     - Total sale amount ($100 - $10,000)
Quantity_Sold    - Units sold (1-50)
Product_Category - Product type (Electronics, Furniture, Clothing, Food)
Unit_Cost        - Cost per unit
Unit_Price       - Price per unit
Customer_Type    - Customer classification (New, Returning)
Discount         - Discount percentage (0% - 30%)
Payment_Method   - Payment type (Credit Card, Cash, Bank Transfer)
Sales_Channel    - Channel type (Online, Retail)
```



#### Why 11 Analysis Types?

**Selected Analyses Cover**:
1. **Geographic** (Sales by Region, Quarterly by Region)
2. **Personnel** (Top Sales Reps)
3. **Product** (Category Performance, Profitable Products)
4. **Temporal** (Monthly Trend, Quarterly Performance)
5. **Customer** (Customer Type Analysis, Discount Impact)
6. **Operational** (Payment Methods, Sales Channels)
7. **Strategic** (High-Value Transactions)


### 📋 Data Schema Documentation

**Complete Field Definitions**:

| Field | Type | Range/Values | Description | Example |
|-------|------|--------------|-------------|---------|
| Product_ID | String | 1001-1100 | Unique product identifier | "1052" |
| Sale_Date | Date | 2023-01-01 to 2024-01-01 | Transaction date (ISO format) | "2023-02-03" |
| Sales_Rep | String | 5 values | Sales representative name | "Alice" |
| Region | String | 4 values | Geographic sales region | "North" |
| Sales_Amount | Double | $100 - $10,000 | Final transaction amount | 5053.97 |
| Quantity_Sold | Integer | 1-50 | Number of units sold | 18 |
| Product_Category | String | 4 values | Product classification | "Furniture" |
| Unit_Cost | Double | Variable | Cost per unit to business | 152.75 |
| Unit_Price | Double | Variable | Selling price per unit | 267.22 |
| Customer_Type | String | 2 values | Customer classification | "New"/"Returning" |
| Discount | Double | 0.00 - 0.30 | Discount as decimal (0-30%) | 0.09 |
| Payment_Method | String | 3 values | Payment type | "Credit Card" |
| Sales_Channel | String | 2 values | Sales channel | "Online"/"Retail" |

**Categorical Value Distributions**:
- **Sales Reps**: Alice, Bob, Charlie, David, Eve (5 total)
- **Regions**: North, South, East, West (4 total)
- **Categories**: Electronics, Furniture, Clothing, Food (4 total)
- **Customer Types**: New, Returning (2 total)
- **Payment Methods**: Credit Card, Cash, Bank Transfer (3 total)
- **Sales Channels**: Online, Retail (2 total)

**Code Quality Measures**:
- ✅ **DRY Principle**: No code duplication
- ✅ **SOLID Principles**: Single responsibility, open/closed
- ✅ **Clean Code**: Meaningful names, small methods
- ✅ **Comprehensive JavaDoc**: All public APIs documented
- ✅ **Defensive Programming**: Null checks, validation
- ✅ **Error Handling**: Graceful degradation

---

## 📦 Prerequisites

- **Java**: JDK 17 or higher
- **Maven**: 3.6.0 or higher

#### Check Installation:
```bash
java -version
mvn --version
```

### Installation:
- **Java**: [Download from Oracle](https://www.oracle.com/java/technologies/downloads/)
- **Maven**: [Download from Apache](https://maven.apache.org/download.cgi)

---

## 📁 Project Structure

```
Build-Challenge-SWE1/
│
├── Assignment1/                            # Producer-Consumer Implementation
│   ├── pom.xml                            # Maven configuration
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── buildchallengePartA/
│   │   │           ├── CustomBlockingQueue.java
│   │   │           ├── Producer.java
│   │   │           ├── Consumer.java
│   │   │           └── ProducerConsumerSystem.java
│   │   └── test/
│   │       └── java/
│   │           └── buildchallengePartA/
│   │               └── ProducerConsumerTest.java
│   └── target/                            # Build output
│
├── Assignment2/                            # Sales Data Analysis
│   ├── pom.xml                            # Maven configuration
│   ├── sales_data.csv                     # Input data file
│   ├── src/
│   │   ├── main/
│   │   │   └── java/
│   │   │       └── buildchallengePartB/
│   │   │           ├── SalesRecord.java          # Data model
│   │   │           ├── SalesStats.java           # Statistics helper
│   │   │           ├── SalesDataAnalyzer.java    # Business logic
│   │   │           └── SalesDataAnalysisApp.java # Main entry point
│   │   └── test/
│   │       └── java/
│   │           └── buildchallengePartB/
│   │               └── SalesDataAnalysisTest.java # 25 test cases
│   └── target/                            # Build output
│
└── README.md                              # This file
```

---

## 🚀 Quick Start Guide

### Clone the Repository
```bash
git clone <repository-url>
cd Build-Challenge-SWE1
```

### Run Assignment 1 (Producer-Consumer)
```bash
cd Assignment1
mvn clean install
mvn exec:java -Dexec.mainClass="buildchallengePartA.ProducerConsumerSystem"
```

### Run Assignment 2 (Data Analysis)
```bash
cd ../Assignment2
# Ensure sales_data.csv is in Assignment2 directory
mvn clean compile
mvn exec:java -Dexec.mainClass="buildchallengePartB.SalesDataAnalysisApp"
```

### Run All Tests
```bash
# From Assignment1
mvn test

# From Assignment2
cd ../Assignment2
mvn test
```

---

## 🧪 Testing

### Assignment 1 Tests
- **19+ test cases** covering:
   - Basic queue operations
   - Blocking behavior verification
   - Concurrent access scenarios
   - Producer-Consumer integration
   - Edge cases (empty queue, full queue)

### Assignment 2 Tests
- **25 test cases** covering:
   - Data model validation
   - All 11 analysis methods
   - Stream operations verification
   - Edge cases (empty data, single record)
  
---

## 📝 Additional Notes

### Assignment 1
- Queue capacity intentionally small (3) to demonstrate blocking behavior
- Can extend to multiple producers/consumers easily

### Assignment 2
- Supports both comma and tab-delimited CSV files
- Uses relative paths for portability across systems
- Designed for easy integration into larger applications
- Can process large datasets efficiently with streams

---

---

## 👨‍💻 Author

**Manan Vijayvargiya**

---