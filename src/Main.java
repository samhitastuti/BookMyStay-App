/**
 * Book My Stay App
 *
 * Use Case 9: Error Handling & Validation
 *
 * Demonstrates input validation, custom exceptions,
 * and fail-fast error handling.
 *
 * @author Samhita
 * @version 9.1
 */

import java.util.*;

// 🔹 Custom Exception
class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

// 🔹 Inventory Class
class RoomInventory {
    private Map<String, Integer> inventory = new HashMap<>();

    public RoomInventory() {
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 0);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    public void decrement(String type) {
        inventory.put(type, inventory.get(type) - 1);
    }

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }
}

// 🔹 Validator Class
class BookingValidator {

    public void validate(String roomType, RoomInventory inventory) throws InvalidBookingException {

        // 🔹 Validate room type
        if (!inventory.isValidRoomType(roomType)) {
            throw new InvalidBookingException("Invalid room type selected.");
        }

        // 🔹 Validate availability
        if (inventory.getAvailability(roomType) <= 0) {
            throw new InvalidBookingException("No rooms available for selected type.");
        }
    }
}

// 🔹 Booking Service
class BookingService {

    private RoomInventory inventory;
    private BookingValidator validator;

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        this.validator = new BookingValidator();
    }

    public void bookRoom(String guestName, String roomType) {

        try {
            // 🔹 Validate first (fail-fast)
            validator.validate(roomType, inventory);

            // 🔹 Proceed with booking
            inventory.decrement(roomType);

            System.out.println("Booking SUCCESS for " + guestName +
                    " | Room Type: " + roomType);

        } catch (InvalidBookingException e) {
            // 🔹 Graceful error handling
            System.out.println("Booking FAILED for " + guestName +
                    " | Reason: " + e.getMessage());
        }
    }
}

// 🔹 Main Class
public class Main {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Book My Stay App - Version 9.1");
        System.out.println("=====================================\n");

        RoomInventory inventory = new RoomInventory();
        BookingService service = new BookingService(inventory);

        // 🔹 Test cases (valid + invalid)

        service.bookRoom("Alice", "Single Room");   // ✅ valid
        service.bookRoom("Bob", "Suite Room");      // ❌ no availability
        service.bookRoom("Charlie", "Deluxe Room"); // ❌ invalid type

        System.out.println("\nSystem continues running safely after errors.");
    }
}