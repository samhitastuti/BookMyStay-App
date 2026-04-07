/**
 * Book My Stay App
 *
 * Use Case 8: Booking History & Reporting
 *
 * Demonstrates storing confirmed bookings and generating reports.
 *
 * @author Samhita
 * @version 8.1
 */

import java.util.*;

// 🔹 Reservation Class
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println("Reservation ID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType);
    }
}

// 🔹 Booking History (List = ordered storage)
class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    // Add confirmed booking
    public void addReservation(Reservation r) {
        history.add(r);
    }

    // Get all bookings (read-only usage)
    public List<Reservation> getAllReservations() {
        return history;
    }
}

// 🔹 Report Service
class BookingReportService {

    // Display all bookings
    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("\n----- Booking History -----\n");

        for (Reservation r : reservations) {
            r.display();
        }
    }

    // Summary report
    public void generateSummary(List<Reservation> reservations) {

        Map<String, Integer> countByRoom = new HashMap<>();

        for (Reservation r : reservations) {
            String type = r.getRoomType();
            countByRoom.put(type, countByRoom.getOrDefault(type, 0) + 1);
        }

        System.out.println("\n----- Booking Summary Report -----\n");

        for (Map.Entry<String, Integer> entry : countByRoom.entrySet()) {
            System.out.println(entry.getKey() + " bookings: " + entry.getValue());
        }
    }
}

// 🔹 Main Class
public class Main {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Book My Stay App - Version 8.1");
        System.out.println("=====================================\n");

        // 🔹 Initialize history
        BookingHistory history = new BookingHistory();

        // 🔹 Simulate confirmed bookings (from UC6)
        history.addReservation(new Reservation("SI-1", "Alice", "Single Room"));
        history.addReservation(new Reservation("SI-2", "Bob", "Single Room"));
        history.addReservation(new Reservation("SU-1", "Charlie", "Suite Room"));

        // 🔹 Reporting
        BookingReportService report = new BookingReportService();

        report.displayAllBookings(history.getAllReservations());
        report.generateSummary(history.getAllReservations());

        System.out.println("\nReports generated successfully.");
    }
}