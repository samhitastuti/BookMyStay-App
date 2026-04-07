/**
 * Book My Stay App
 *
 * Use Case 12: Data Persistence & System Recovery
 *
 * Demonstrates saving and restoring system state using
 * serialization and file handling.
 *
 * @author Samhita
 * @version 12.1
 */

import java.io.*;
import java.util.*;

// 🔹 Reservation Class (Serializable)
class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    String reservationId;
    String guestName;
    String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

// 🔹 System State (Inventory + Booking History)
class SystemState implements Serializable {
    private static final long serialVersionUID = 1L;

    Map<String, Integer> inventory;
    List<Reservation> bookings;

    public SystemState(Map<String, Integer> inventory, List<Reservation> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

// 🔹 Persistence Service
class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    // Save state
    public void save(SystemState state) {
        try (ObjectOutputStream oos =
                     new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {

            oos.writeObject(state);
            System.out.println("\nSystem state saved successfully.");

        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    // Load state
    public SystemState load() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream(FILE_NAME))) {

            SystemState state = (SystemState) ois.readObject();
            System.out.println("System state loaded successfully.");
            return state;

        } catch (FileNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        } catch (Exception e) {
            System.out.println("Error loading data. Starting safe state.");
        }

        // return default safe state
        return new SystemState(new HashMap<>(), new ArrayList<>());
    }
}

// 🔹 Main Class
public class Main {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Book My Stay App - Version 12.1");
        System.out.println("=====================================\n");

        PersistenceService service = new PersistenceService();

        // 🔹 LOAD existing state
        SystemState state = service.load();

        Map<String, Integer> inventory = state.inventory;
        List<Reservation> bookings = state.bookings;

        // 🔹 If first run, initialize data
        if (inventory.isEmpty()) {
            inventory.put("Single Room", 2);
            inventory.put("Double Room", 1);

            bookings.add(new Reservation("SI-1", "Alice", "Single Room"));
            bookings.add(new Reservation("DB-1", "Bob", "Double Room"));
        }

        // 🔹 Display restored data
        System.out.println("\n--- Restored Inventory ---");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }

        System.out.println("\n--- Booking History ---");
        for (Reservation r : bookings) {
            r.display();
        }

        // 🔹 Simulate update before shutdown
        bookings.add(new Reservation("SI-2", "Charlie", "Single Room"));
        inventory.put("Single Room", inventory.get("Single Room") - 1);

        // 🔹 SAVE state before exit
        service.save(new SystemState(inventory, bookings));

        System.out.println("\nSystem ready for shutdown/restart.");
    }
}