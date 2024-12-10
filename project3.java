
import java.util.*;

public class project3 {
    static Scanner sc = new Scanner(System.in);
    static List<Room> rooms = new ArrayList<>();
    static List<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {
        initializeRooms();
        System.out.println("Welcome to my hotel reservation system");

        int choice;
        do {
            System.out.println("\n=== Main Menu ===");
            System.out.println("1. Search for Available Rooms");
            System.out.println("2. Make a Reservation");
            System.out.println("3. View Booking Details");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    searchAvailableRooms();
                    break;
                case 2:
                    makeReservation();
                    break;
                case 3:
                    viewBookings();
                    break;
                case 4:
                    System.out.println("Thank you for using this");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    // Initialize rooms with dummy data
    private static void initializeRooms() {
        rooms.add(new Room(101, "Single", 100.0, true));
        rooms.add(new Room(102, "Single", 100.0, true));
        rooms.add(new Room(201, "Double", 150.0, true));
        rooms.add(new Room(202, "Double", 150.0, true));
        rooms.add(new Room(301, "Suite", 300.0, true));
    }

    // Search for available rooms
    private static void searchAvailableRooms() {
        System.out.println("\n=== Available Rooms ===");
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println(room);
            }
        }
    }

    // Make a reservation
    private static void makeReservation() {
        System.out.print("\nEnter Room Number to book: ");
        int roomNumber = sc.nextInt();

        Room room = findRoomByNumber(roomNumber);
        if (room != null && room.isAvailable()) {
            System.out.print("Enter Customer Name: ");
            sc.nextLine(); // Consume newline
            String customerName = sc.nextLine();

            System.out.print("Enter Number of Days: ");
            int days = sc.nextInt();

            double totalAmount = room.getPrice() * days;
            System.out.println("Total Amount: $" + totalAmount);

            System.out.print("Enter Payment (full amount required): ");
            double payment = sc.nextDouble();

            if (payment >= totalAmount) {
                room.setAvailable(false);
                Booking booking = new Booking(customerName, room, days, totalAmount);
                bookings.add(booking);
                System.out.println("Booking confirmed! Booking ID: " + booking.getId());
            } else {
                System.out.println("Insufficient payment. Booking failed.");
            }
        } else {
            System.out.println("Room is not available or does not exist.");
        }
    }

    // View all bookings
    private static void viewBookings() {
        if (bookings.isEmpty()) {
            System.out.println("\nNo bookings found.");
        } else {
            System.out.println("\n=== Booking Details ===");
            for (Booking booking : bookings) {
                System.out.println(booking);
            }
        }
    }

    // Find room by room number
    private static Room findRoomByNumber(int roomNumber) {
        for (Room room : rooms) {
            if (room.getNumber() == roomNumber) {
                return room;
            }
        }
        return null;
    }
}

// Room class
class Room {
    private int number;
    private String category;
    private double price;
    private boolean available;

    public Room(int number, String category, double price, boolean available) {
        this.number = number;
        this.category = category;
        this.price = price;
        this.available = available;
    }

    public int getNumber() {
        return number;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Room " + number + " (" + category + ") - $" + price + " per night - " +
                (available ? "Available" : "Booked");
    }
}

// Booking class
class Booking {
    private static int idCounter = 1;
    private int id;
    private String customerName;
    private Room room;
    private int days;
    private double totalAmount;

    public Booking(String customerName, Room room, int days, double totalAmount) {
        this.id = idCounter++;
        this.customerName = customerName;
        this.room = room;
        this.days = days;
        this.totalAmount = totalAmount;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Booking ID: " + id + ", Customer: " + customerName + ", Room: " + room.getNumber() +
                " (" + room.getCategory() + "), Days: " + days + ", Total: $" + totalAmount;
    }
}

