/**
 * Book My Stay App
 *
 * Use Case 10: Booking Cancellation & Inventory Rollback
 *
 * Demonstrates safe cancellation using Stack (LIFO)
 * and consistent state rollback.
 *
 * @author Samhita
 * @version 10.1
 */

import java.util.*;

// 🔹 Reservation Class
class Reservation {
    String reservationId;
    String roomType;

    public Reservation(String reservationId, String roomType) {
        this.reservationId = reservationId;
        this.roomType = roomType;
    }
}

// 🔹 Inventory Class
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void increment(String type) {
        inventory.put(type, inventory.get(type) + 1);
    }

    public void display() {
        System.out.println("\n--- Inventory Status ---");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

// 🔹 Booking History
class BookingHistory {
    Map<String, Reservation> history = new HashMap<>();

    public void add(Reservation r) {
        history.put(r.reservationId, r);
    }

    public Reservation get(String id) {
        return history.get(id);
    }

    public void remove(String id) {
        history.remove(id);
    }
}

// 🔹 Cancellation Service
class CancellationService {

    private Stack<String> rollbackStack = new Stack<>();

    public void cancelBooking(String reservationId,
                              BookingHistory history,
                              RoomInventory inventory) {

        System.out.println("\nProcessing cancellation for ID: " + reservationId);

        // 🔹 Validate reservation
        Reservation r = history.get(reservationId);

        if (r == null) {
            System.out.println("Cancellation FAILED: Reservation not found.");
            return;
        }

        // 🔹 Push to rollback stack (LIFO)
        rollbackStack.push(reservationId);

        // 🔹 Restore inventory
        inventory.increment(r.roomType);

        // 🔹 Remove from history
        history.remove(reservationId);

        // 🔹 Confirmation
        System.out.println("Cancellation SUCCESS for ID: " + reservationId);
    }

    public void displayRollbackStack() {
        System.out.println("\n--- Rollback Stack ---");
        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}

// 🔹 Main Class
public class Main {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Book My Stay App - Version 10.1");
        System.out.println("=====================================");

        // 🔹 Initialize
        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();
        CancellationService cancelService = new CancellationService();

        // 🔹 Simulate confirmed bookings (from UC6)
        history.add(new Reservation("SI-1", "Single Room"));
        history.add(new Reservation("DB-1", "Double Room"));

        // 🔹 Cancel bookings
        cancelService.cancelBooking("SI-1", history, inventory); // ✅ valid
        cancelService.cancelBooking("XX-1", history, inventory); // ❌ invalid

        // 🔹 Display results
        inventory.display();
        cancelService.displayRollbackStack();
    }
}