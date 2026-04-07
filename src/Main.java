/**
 * Book My Stay App
 *
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Demonstrates Queue (FIFO) for fair booking request handling.
 *
 * @author Samhita
 * @version 5.1
 */

import java.util.LinkedList;
import java.util.Queue;

// 🔹 Reservation Class (Represents a booking request)
class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}

// 🔹 Booking Queue Class
class BookingQueue {

    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    // Add request (enqueue)
    public void addRequest(Reservation r) {
        queue.add(r);
        System.out.println("Request added for " + r.getGuestName());
    }

    // View all requests (without removing)
    public void displayQueue() {
        System.out.println("\n----- Booking Request Queue -----\n");

        for (Reservation r : queue) {
            r.display();
        }
    }
}

// 🔹 Main Class
public class Main {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Book My Stay App - Version 5.1");
        System.out.println("=====================================\n");

        // 🔹 Initialize queue
        BookingQueue bookingQueue = new BookingQueue();

        // 🔹 Simulate booking requests
        bookingQueue.addRequest(new Reservation("Alice", "Single Room"));
        bookingQueue.addRequest(new Reservation("Bob", "Double Room"));
        bookingQueue.addRequest(new Reservation("Charlie", "Suite Room"));

        // 🔹 Display queue (FIFO order)
        bookingQueue.displayQueue();

        System.out.println("\nAll requests are stored in arrival order.");
        System.out.println("No rooms allocated yet.");
    }
}