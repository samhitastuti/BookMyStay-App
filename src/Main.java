/**
 * Book My Stay App
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * Demonstrates use of HashMap for centralized inventory management.
 *
 * @author Samhita
 * @version 3.1
 */

import java.util.HashMap;
import java.util.Map;

// 🔹 Inventory Class (Single Source of Truth)
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initialize room availability
        inventory.put("Single Room", 5);
        inventory.put("Double Room", 3);
        inventory.put("Suite Room", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability (controlled update)
    public void updateAvailability(String roomType, int count) {
        if (inventory.containsKey(roomType)) {
            inventory.put(roomType, count);
        } else {
            System.out.println("Room type not found!");
        }
    }

    // Display all inventory
    public void displayInventory() {
        System.out.println("----- Current Inventory -----\n");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println("Room Type: " + entry.getKey());
            System.out.println("Available: " + entry.getValue());
            System.out.println();
        }
    }
}

// 🔹 Main Class
public class Main {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Book My Stay App - Version 3.1");
        System.out.println("=====================================\n");

        // 🔹 Initialize Inventory
        RoomInventory inventory = new RoomInventory();

        // 🔹 Display Inventory
        inventory.displayInventory();

        // 🔹 Simulate update
        System.out.println("Updating availability for Double Room...\n");
        inventory.updateAvailability("Double Room", 1);

        // 🔹 Display updated inventory
        inventory.displayInventory();

        System.out.println("Thank you for using Book My Stay App!");
    }
}