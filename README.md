# Multithreaded Store Sales Java Application

A Java console application simulating sales operations in a store with multithreading for concurrent customer transactions.

## Features

- Inventory management: Track and update product quantities.
- Customer baskets: Each customer has a unique shopping basket.
- Multithreading: Processes sales for 10+ customers across 4 threads.
- Post-sale reporting: Displays updated inventory after all transactions.

## How It Works

1. Initialize store inventory.
2. Simulate customer baskets with desired products.
3. Process sales as concurrent tasks using a thread pool.
4. Update and display inventory after transactions.

## How to Run

1. Clone the repository using the following command:
   ```bash
   git clone https://github.com/your-username/store-sales-console-app.git

3. Navigate to the project directory:
   ```bash
   cd store-sales-console-app

5. Compile the Java program:  
   ```bash
   javac Main.java

7. Run the program:
   ```bash
   java Main

## Example Output

```plaintext
Customer 1 is purchasing items...
Customer 2 is purchasing items...
...
All transactions completed.
Updated inventory:
- Product A: 10 units
- Product B: 5 units
```


## Technologies

- **Java**
- **Multithreading** (`java.util.concurrent`)
- **Collections** for managing inventory and baskets

