# Build-Challenge-SWE1

## Assignment 1

A robust implementation of the classic **Producer-Consumer pattern** demonstrating thread synchronization, concurrent programming, and inter-thread communication mechanisms in Java.

### 📋 Table of Contents
- [Overview](#overview)
- [Core Concepts Demonstrated](#core-concepts-demonstrated)
- [Architecture](#architecture)
- [Prerequisites](#prerequisites)
- [Project Setup](#project-setup)
- [Running the Application](#running-the-application)
- [Running Tests](#running-tests)
- [Design Decisions](#design-decisions)
- [Sample Output](#sample-output)
- [Project Structure](#project-structure)

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

#### Key Components:

1. **CustomBlockingQueue**: Thread-safe bounded queue with wait/notify
2. **Producer**: Reads from source, blocks when queue is full
3. **Consumer**: Writes to destination, blocks when queue is empty
4. **Lock & Conditions**: Ensures thread safety and coordination

### 📦 Prerequisites

- **Java**: JDK 21 or higher
- **Maven**: 3.6.0 or higher

#### Check Installation:
```bash
java -version
mvn --version
```

### 🚀 Project Setup

#### Clone the Repository:
```bash
git clone <repository-url>
cd Assignment1
```

#### Build the Project:
```bash
mvn clean install
```

This will:
- Compile all source files
- Run all unit tests
- Generate code coverage report
- Package the application as a JAR file

### ▶️ Running the Application

#### Method 1: Using Maven
```bash
mvn exec:java -Dexec.mainClass="buildchallengePartA.ProducerConsumerSystem"
```

#### Method 2: Using JAR
```bash
java -jar target/buildchallengePartA-1.0-SNAPSHOT.jar
```

#### Method 3: Using IDE (IntelliJ IDEA)
1. Open the project in IntelliJ
2. Navigate to `ProducerConsumerSystem.java`
3. Right-click and select **Run 'ProducerConsumerSystem.main()'**

### 🧪 Running Tests

#### Run All Tests:
```bash
mvn test
```

#### Run Tests with Coverage:
```bash
mvn clean test jacoco:report
```

Coverage report will be generated at: `target/site/jacoco/index.html`

#### Run Specific Test Class:
```bash
mvn test -Dtest=CustomBlockingQueueTest
mvn test -Dtest=ProducerConsumerIntegrationTest
```

#### Test Coverage:
- **CustomBlockingQueue**: Basic operations, blocking behavior, concurrency
- **Producer**: Source reading, queue interaction, edge cases
- **Consumer**: Destination writing, order preservation, edge cases
- **Integration**: End-to-end workflow, multiple producers/consumers, thread safety

### 🎨 Design Decisions

#### Why Queue Capacity = 3?

The small queue capacity (3 items) is **intentionally chosen** to:

1. **Demonstrate Blocking Behavior**: With 10 source items and capacity of 3, the producer frequently blocks, clearly showing the wait/notify mechanism in action

2. **Highlight Thread Coordination**: Forces both threads to actively coordinate rather than one thread dominating

3. **Visible in Logs**: Makes it easy to observe queue states (FULL/EMPTY) in console output

4. **Test Concurrency Issues**: Stresses the synchronization mechanisms more than a large queue would

#### Other Key Decisions:

- **Custom Implementation**: Built blocking queue from scratch to demonstrate understanding of wait/notify
- **ReentrantLock over Synchronized**: More flexible and explicit control
- **Separate Conditions**: `notFull` and `notEmpty` for precise signaling
- **Thread-Safe Collections**: Used `Collections.synchronizedList()` for destination container
- **Comprehensive Testing**: 19+ test cases covering edge cases and race conditions

### 📊 Sample Output

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

#### Key Observations:
- ✅ Producer **blocks** when queue reaches capacity 3
- ✅ Consumer **blocks** when queue is empty
- ✅ Threads **signal** each other to resume operations
- ✅ All items transferred successfully maintaining order
- ✅ No data loss or race conditions

### 📁 Project Structure

```
Assignment1/
├── pom.xml                                 # Maven configuration                               # This file
├── src/
│   ├── main/
│   │   └── java/
│   │       └── buildchallengePartA/
│   │           ├── CustomBlockingQueue.java    # Thread-safe queue implementation
│   │           ├── Producer.java               # Producer thread
│   │           ├── Consumer.java               # Consumer thread
│   │           └── ProducerConsumerSystem.java # Main application
│   └── test/
│       └── java/
│          gitstatus └── buildchallengePartA/
│               └── ProducerConsumerTest.java   # Comprehensive unit tests
└── target/                                 # Compiled classes and reports
    ├── classes/
    ├── generated-sources/
    └── generated-test-sources/
    ├── maven-status/
    ├── surefire-reports/
    ├── test-classes/

Assignment2
│
├── pom.xml                                 # Maven configuration                               # This file
├── src/
│   ├── main/
│   │   └── java/
    
```

## 🔍 Understanding the Flow

1. **Initialization**: Source container populated with 10 items, queue created with capacity 3

2. **Producer Starts**:
    - Reads items from source one by one
    - Calls `queue.put(item)`
    - Blocks if queue is full, waits for consumer

3. **Consumer Starts**:
    - Calls `queue.take()`
    - Blocks if queue is empty, waits for producer
    - Processes and stores in destination

4. **Coordination**:
    - Producer signals consumer when item added
    - Consumer signals producer when item removed
    - Both threads coordinate until all items transferred

5. **Completion**: Both threads finish, results verified


## 📝 Notes

- **Thread Sleep**: Used `Thread.sleep()` to simulate processing time and make blocking behavior visible
- **Production Ready**: While simplified for demonstration, follows production-level practices
- **Extensible**: Can easily add multiple producers/consumers by creating additional threads

