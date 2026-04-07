/**
 * Book My Stay App
 *
 * Use Case 6: Reservation Confirmation & Room Allocation
 *
 * Demonstrates FIFO processing, unique room allocation,
 * and prevention of double booking.
 *
 * @author Samhita
 * @version 6.1
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

// 🔹 Booking Queue
class BookingQueue {
    Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNextRequest() {
        return queue.poll(); // FIFO
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

// 🔹 Inventory Service
class RoomInventory {
    private HashMap<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrement(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public void display() {
        System.out.println("\n--- Updated Inventory ---");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// 🔹 Booking Service (CORE LOGIC)
class BookingService {

    private Set<String> allocatedRoomIds = new HashSet<>();
    private HashMap<String, Set<String>> roomAllocations = new HashMap<>();
    private int idCounter = 1;

    public void processBookings(BookingQueue queue, RoomInventory inventory) {

        while (!queue.isEmpty()) {

            Reservation r = queue.getNextRequest();
            String type = r.roomType;

            System.out.println("\nProcessing request for " + r.guestName);

            // 🔹 Check availability
            if (inventory.getAvailability(type) > 0) {

                // 🔹 Generate unique room ID
                String roomId = generateRoomId(type);

                // 🔹 Ensure uniqueness
                while (allocatedRoomIds.contains(roomId)) {
                    roomId = generateRoomId(type);
                }

                // 🔹 Store allocated ID
                allocatedRoomIds.add(roomId);

                roomAllocations.putIfAbsent(type, new HashSet<>());
                roomAllocations.get(type).add(roomId);

                // 🔹 Update inventory
                inventory.decrement(type);

                // 🔹 Confirm booking
                System.out.println("Booking CONFIRMED for " + r.guestName);
                System.out.println("Room Type: " + type);
                System.out.println("Allocated Room ID: " + roomId);

            } else {
                System.out.println("Booking FAILED for " + r.guestName + " (No rooms available)");
            }
        }
    }

    // 🔹 Generate Room ID
    private String generateRoomId(String type) {
        return type.substring(0, 2).toUpperCase() + "-" + (idCounter++);
    }
}

// 🔹 Main Class
public class Main {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Book My Stay App - Version 6.1");
        System.out.println("=====================================");

        // 🔹 Initialize
        BookingQueue queue = new BookingQueue();
        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService();

        // 🔹 Add booking requests (FIFO)
        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Single Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room")); // should fail
        queue.addRequest(new Reservation("David", "Suite Room"));

        // 🔹 Process bookings
        service.processBookings(queue, inventory);

        // 🔹 Show final inventory
        inventory.display();
    }
}