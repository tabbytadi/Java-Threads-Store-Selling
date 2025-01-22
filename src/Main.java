import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class Product {
    private final String name;
    private int quantity;

    public Product(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public synchronized boolean reduceQuantity(int amount) {
        if (quantity >= amount) {
            quantity -= amount;
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public synchronized int getQuantity() {
        return quantity;
    }
}

class Store {
    private final Map<String, Product> inventory = new HashMap<>();

    public void addProduct(Product product) {
        inventory.put(product.getName(), product);
    }

    public Product getProduct(String name) {
        return inventory.get(name);
    }

    public void displayInventory() {
        System.out.println("Final Inventory:");
        for (Product product : inventory.values()) {
            System.out.printf("%s: %d%n", product.getName(), product.getQuantity());
        }
    }
}

class Customer implements Runnable {
    private final String name;
    private final Map<String, Integer> cart;
    private final Store store;

    public Customer(String name, Map<String, Integer> cart, Store store) {
        this.name = name;
        this.cart = cart;
        this.store = store;
    }

    @Override
    public void run() {
        System.out.printf("%s started shopping.%n", name);
        for (Map.Entry<String, Integer> entry : cart.entrySet()) {
            String productName = entry.getKey();
            int quantity = entry.getValue();

            Product product = store.getProduct(productName);
            if (product != null) {
                synchronized (product) {
                    if (product.reduceQuantity(quantity)) {
                        System.out.printf("%s bought %d of %s.%n", name, quantity, productName);
                    } else {
                        System.out.printf("%s could not buy %d of %s (not enough stock).%n",
                                name, quantity, productName);
                    }
                }
            } else {
                System.out.printf("%s could not find %s in the store.%n", name, productName);
            }
        }
        System.out.printf("%s finished shopping.%n", name);
    }
}

class StoreApplication {
    public static void main(String[] args) {
        Store store = new Store();

        store.addProduct(new Product("Apple", 50));
        store.addProduct(new Product("Banana", 30));
        store.addProduct(new Product("Milk", 20));

        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer("Customer1", Map.of("Apple", 5, "Banana", 3), store));
        customers.add(new Customer("Customer2", Map.of("Apple", 10, "Milk", 2), store));
        customers.add(new Customer("Customer3", Map.of("Banana", 15), store));
        customers.add(new Customer("Customer4", Map.of("Milk", 5, "Apple", 7), store));
        customers.add(new Customer("Customer5", Map.of("Apple", 8, "Banana", 10), store));
        customers.add(new Customer("Customer6", Map.of("Milk", 4), store));
        customers.add(new Customer("Customer7", Map.of("Banana", 12), store));
        customers.add(new Customer("Customer8", Map.of("Apple", 6, "Milk", 3), store));
        customers.add(new Customer("Customer9", Map.of("Apple", 2), store));
        customers.add(new Customer("Customer10", Map.of("Banana", 7, "Milk", 1), store));

        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (Customer customer : customers) {
            executor.submit(customer);
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
            Thread.yield();
        }

        store.displayInventory();
    }
}
