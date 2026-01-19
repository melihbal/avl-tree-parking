# AVL Tree Parking Lot Management System

A Java-based simulation that optimizes truck parking logistics using a custom-built **AVL Tree**. This project efficiently handles dynamic capacity constraints, truck routing, and load distribution with logarithmic time complexity.

## Overview

This project simulates a complex logistics network where parking lots are dynamically allocated and managed based on specific capacity constraints. The goal is to efficiently route trucks to the optimal parking lot while maintaining balanced access speeds.

It implements a custom data structure to solve this:
* **AVL Tree (Self-Balancing BST):** Unlike standard lists, this system uses an AVL Tree to index parking lots by capacity. This ensures that finding, adding, or deleting a lot remains efficient ($O(\log N)$) even as the simulation scales to thousands of lots.
* **Queue-Based Logistics:** Implements custom FIFO queues to handle the real-time state transitions of trucks (from "Waiting" to "Ready").

## Key Features

* **Custom Data Structures:** Built entirely from scratch (AVL Tree, Linked List Queues) without relying on Java Collections.
* **Smart Routing Algorithm:** Automatically finds the "Best Fit" lot for a truck, or the "Next Smallest Available" if the best fit is full.
* **Dynamic Input:** Load different simulation scenarios (text files) via command line arguments.
* **Performance Optimization:** Uses buffered I/O to process large datasets (e.g., `type3-large2.txt`) in milliseconds.

## Technology Stack

* **Language:** Java
* **Concepts:** AVL Trees, Binary Search Trees (BST), FIFO Queues, File I/O
* **Complexity:** $O(\log N)$ for Search/Insert/Delete

## How to Run

### Prerequisites
* Java Development Kit (JDK) 8 or higher.
* Input files (e.g., `type1-large.txt`) should be placed in the `inputs/` folder.

### 1. Compile
```bash
javac *.java
```

### 2. Run with Different Input Files
You can run the simulation on different datasets by specifying the file path as the first argument.

**Syntax:**
```bash
java Main [filepath]
```

**Examples:**

* **Run on a specific large dataset:**
    ```bash
    java Main inputs/type1-large.txt
    ```

* **Run on a small test set:**
    ```bash
    java Main inputs/type4-small.txt
    ```

* **Run on default file:**
  *(If no argument is provided, it defaults to `inputs/type1-large.txt`)*
    ```bash
    java Main
    ```

## Input Commands

The system processes a text file containing a sequence of commands. These act as the simulation instructions:

| Command | Arguments | Description |
| :--- | :--- | :--- |
| `create_parking_lot` | `capacity`, `limit` | Creates a new lot and inserts it into the AVL Tree. |
| `add_truck` | `id`, `capacity` | Assigns a new truck to the best-fitting parking lot. |
| `ready` | `capacity` | Moves a truck from "Waiting" to "Ready" state in a specific lot. |
| `load` | `capacity`, `amount` | Distributes load into available trucks in a specific lot. |
| `count` | `capacity` | Returns the total number of trucks in all lots larger than `capacity`. |

## Input Format

Input files (e.g., `inputs/type1-large.txt`) must follow the command structure above. Each line represents a single simulation step:

```text
create_parking_lot 15 5
add_truck 101 10
ready 15
load 15 50
...
```