/**
 * Book My Stay App
 *
 * Use Case 11: Concurrent Booking Simulation (Thread Safety)
 *
 * Demonstrates multi-threaded booking with synchronization
 * to prevent race conditions and double booking.
 *
 * @author Samhita
 * @version 11.1
 */

import java.util.*;

// 🔹 Reservation Class
class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

// 🔹 Thread-Safe Booking Queue
class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    // synchronized method for safe access
    public synchronized void addRequest(Reservation r) {
        queue.add(r);
    }

    public synchronized Reservation getNextRequest() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

// 🔹 Thread-Safe Inventory
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
    }

    // 🔹 Critical section (synchronized)
    public synchronized boolean allocateRoom(String type) {

        int available = inventory.getOrDefault(type, 0);

        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }

        return false;
    }

    public void display() {
        System.out.println("\n--- Final Inventory ---");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// 🔹 Booking Processor (Thread)
class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(String name, BookingQueue queue, RoomInventory inventory) {
        super(name);
        this.queue = queue;
        this.inventory = inventory;
    }

    @Override
    public void run() {

        while (true) {

            Reservation r;

            // 🔹 synchronized access to queue
            synchronized (queue) {
                if (queue.isEmpty()) break;
                r = queue.getNextRequest();
            }

            if (r != null) {

                // 🔹 Critical section for allocation
                boolean success = inventory.allocateRoom(r.roomType);

                if (success) {
                    System.out.println(getName() + " CONFIRMED booking for " + r.guestName);
                } else {
                    System.out.println(getName() + " FAILED booking for " + r.guestName);
                }
            }
        }
    }
}

// 🔹 Main Class
public class Main {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Book My Stay App - Version 11.1");
        System.out.println("=====================================\n");

        // 🔹 Shared resources
        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();

        // 🔹 Add multiple requests (more than available rooms)
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room"));
        queue.addRequest(new Reservation("David", "Single Room"));

        // 🔹 Create threads (simulate multiple users)
        Thread t1 = new BookingProcessor("Thread-1", queue, inventory);
        Thread t2 = new BookingProcessor("Thread-2", queue, inventory);

        // 🔹 Start threads
        t1.start();
        t2.start();

        // 🔹 Wait for completion
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 🔹 Final inventory
        inventory.display();

        System.out.println("\nAll bookings processed safely under concurrency.");
    }
}