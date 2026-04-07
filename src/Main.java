/**
 * Book My Stay App
 *
 * Use Case 7: Add-On Service Selection
 *
 * Demonstrates adding optional services to reservations
 * without modifying booking or inventory logic.
 *
 * @author Samhita
 * @version 7.1
 */

import java.util.*;

// 🔹 Add-On Service Class
class AddOnService {
    private String serviceName;
    private double price;

    public AddOnService(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }
}

// 🔹 Add-On Service Manager
class AddOnServiceManager {

    // Map<ReservationID, List of Services>
    private Map<String, List<AddOnService>> serviceMap = new HashMap<>();

    // Add service to a reservation
    public void addService(String reservationId, AddOnService service) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(service);

        System.out.println("Added service: " + service.getServiceName() +
                " to Reservation ID: " + reservationId);
    }

    // Display services for a reservation
    public void displayServices(String reservationId) {
        System.out.println("\nServices for Reservation ID: " + reservationId);

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        for (AddOnService s : services) {
            System.out.println("- " + s.getServiceName() + " (₹" + s.getPrice() + ")");
        }
    }

    // Calculate total add-on cost
    public double calculateTotalCost(String reservationId) {
        double total = 0;

        List<AddOnService> services = serviceMap.get(reservationId);

        if (services != null) {
            for (AddOnService s : services) {
                total += s.getPrice();
            }
        }

        return total;
    }
}

// 🔹 Main Class
public class Main {

    public static void main(String[] args) {

        System.out.println("=====================================");
        System.out.println("   Book My Stay App - Version 7.1");
        System.out.println("=====================================\n");

        // 🔹 Assume reservation already exists (from UC6)
        String reservationId1 = "SI-1";
        String reservationId2 = "SU-2";

        // 🔹 Initialize Add-On Manager
        AddOnServiceManager manager = new AddOnServiceManager();

        // 🔹 Add services
        manager.addService(reservationId1, new AddOnService("Breakfast", 500));
        manager.addService(reservationId1, new AddOnService("Airport Pickup", 1200));

        manager.addService(reservationId2, new AddOnService("Extra Bed", 800));

        // 🔹 Display services
        manager.displayServices(reservationId1);
        manager.displayServices(reservationId2);

        // 🔹 Calculate total cost
        System.out.println("\nTotal Add-On Cost for " + reservationId1 + ": ₹" +
                manager.calculateTotalCost(reservationId1));

        System.out.println("Total Add-On Cost for " + reservationId2 + ": ₹" +
                manager.calculateTotalCost(reservationId2));

        System.out.println("\nBooking and inventory remain unchanged.");
    }
}