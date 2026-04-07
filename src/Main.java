/**
 * Book My Stay App
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Demonstrates read-only access to inventory without modifying state.
 *
 * @author Samhita
 * @version 4.1
 */

import java.util.HashMap;
import java.util.Map;

// 🔹 Abstract Room Class
abstract class Room {
    protected String roomType;
    protected int beds;
    protected double price;

    public Room(String roomType, int beds, double price) {
        this.roomType = roomType;
        this.beds = beds;
        this.price = price;
    }

    public abstract void displayRoomDetails();
}

// 🔹 Room Types
class SingleRoom extends Room {
    public SingleRoom() {
        super("Single Room", 1, 2000);
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super("Double Room", 2, 3500);
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super("Suite Room", 3, 6000);
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: " + roomType);
        System.out.println("Beds: " + beds);
        System.out.println("Price: ₹" + price);
    }
}

// 🔹 Inventory (Read-only usage here)
class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 0); // unavailable
        inventory.put("Suite Room", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public HashMap<String, Integer> getAllInventory() {
        return inventory; // read-only usage (no modification outside)
    }
}

// 🔹 Search Service (IMPORTANT CLASS)
class SearchService {

    public void searchAvailableRooms(RoomInventory inventory) {

        System.out.println("----- Available Rooms -----\n");

        for (Map.Entry<String, Integer> entry : inventory.getAllInventory().entrySet()) {

            String type = entry.getKey();
            int available = entry.getValue();

            // 🔹 Filter unavailable rooms
            if (available > 0) {

                Room room = createRoom(type);

                if (room != null) {
                    room.displayRoomDetails();
                    System.out.println("Available: " + available + "\n");
                }
            }
        }
    }

    // 🔹 Factory method to create room objects
    private Room createRoom(String type) {
        switch (type) {
            case "Single Room":
                return new SingleRoom();
            case "Double Room":
                return new DoubleRoom();
            case "Suite Room":
                return new SuiteRoom();
            default:
                return null;
        }
    }
}

// 🔹 Main Class
public class Main {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Book My Stay App - Version 4.1");
        System.out.println("=====================================\n");

        // 🔹 Initialize inventory
        RoomInventory inventory = new RoomInventory();

        // 🔹 Search (READ-ONLY)
        SearchService search = new SearchService();
        search.searchAvailableRooms(inventory);

        System.out.println("Search completed. No changes made to inventory.");
    }
}