/**
 * Book My Stay App
 *
 * Use Case 2: Basic Room Types & Static Availability
 *
 * Demonstrates abstraction, inheritance, and polymorphism.
 *
 * @author Samhita
 * @version 2.1
 */

// 🔹 Abstract Class
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

// 🔹 Single Room
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

// 🔹 Double Room
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

// 🔹 Suite Room
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

// 🔹 Main Class (IMPORTANT CHANGE)
public class Main {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Book My Stay App - Version 2.1");
        System.out.println("=====================================\n");

        // 🔹 Static Availability
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        // 🔹 Polymorphism
        Room r1 = new SingleRoom();
        Room r2 = new DoubleRoom();
        Room r3 = new SuiteRoom();

        // 🔹 Display
        System.out.println("----- Room Details -----\n");

        r1.displayRoomDetails();
        System.out.println("Available: " + singleAvailable + "\n");

        r2.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable + "\n");

        r3.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable + "\n");

        System.out.println("Thank you for using Book My Stay App!");
    }
}