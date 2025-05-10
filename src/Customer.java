import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class Customer extends User{
    private String customerId;
    private String address;
    private String bookingHistoryFile;
    // TODO What is preferences?
    private String preferences;
    private int AvailableSeats;
    private BookingSystem bookingSystem;
    //private int availableFlights;

    public Customer(String address, int availableSeats, String bookingHistoryFile, BookingSystem bookingSystem, String customerId, String preferences) {
        this.address = address;
        AvailableSeats = availableSeats;
        this.bookingHistoryFile = bookingHistoryFile;
        this.bookingSystem = bookingSystem;
        this.customerId = customerId;
        this.preferences = preferences;
    }


    public Customer(String userId, String userName, String password, String name, String email, String contactinfo, String role, String address, int availableSeats, String bookingHistoryFile, BookingSystem bookingSystem, String customerId, String preferences) {
        super(userId, userName, password, name, email, contactinfo, role);
        this.address = address;
        AvailableSeats = availableSeats;
        this.bookingHistoryFile = bookingHistoryFile;
        this.bookingSystem = bookingSystem;
        this.customerId = customerId;
        this.preferences = preferences;
    }

    public Customer(String customerId, String address, String bookingHistoryFile, String preferences, BookingSystem bookingSystem) {
        this.customerId = customerId;
        this.address = address;
        this.bookingHistoryFile = bookingHistoryFile;
        this.preferences = preferences;
        this.bookingSystem = bookingSystem;

    }

    public List<String> getBookingHistory() {
        List<String> history = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(bookingHistoryFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading booking history for customer " + customerId + ": " + e.getMessage());
        }
        return history;
    }


    public String getCustomerId() {
        return customerId;
    }


    public void setBookingSystem(BookingSystem bookingSystem) {
        this.bookingSystem = bookingSystem;
    } // ميثود لإضافة مرجع حجز جديد إلى الملف
    private void saveBookingReference(String bookingReference) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bookingHistoryFile, true))) {
            writer.write(bookingReference);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error saving booking reference for customer " + customerId + ": " + e.getMessage());
        }
    }

    // ميثود لإزالة مرجع حجز من الملف (عند الإلغاء)
    private void removeBookingReference(String bookingReference) {
        List<String> tempHistory = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(bookingHistoryFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals(bookingReference)) {
                    tempHistory.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading booking history for customer " + customerId + ": " + e.getMessage());
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(bookingHistoryFile))) {
            for (String ref : tempHistory) {
                writer.write(ref);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error updating booking history for customer " + customerId + ": " + e.getMessage());
        }
    }



    public String getAddress() {
        return address;
    }

    public String getPreferences() {
        return preferences;
    }

     public int getAvailableSeats() {
      return AvailableSeats;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPreferences(String preferences) {
        this.preferences = preferences;
    }

     public void setAvailableSeats(int AvailableSeats) {
      this.AvailableSeats = AvailableSeats;
    }


    public void searchFlights(Scanner scanner)
    {
        System.out.println("Search Flights");
        System.out.print("Enter Origin:");
        String origin = scanner.nextLine();
        System.out.print("Enter Destination:");
        String destination = scanner.nextLine();
        System.out.println("Enter Departure Date (YYYY-MM-DD):");
        String dateStr = scanner.nextLine();
        LocalDateTime departureDate;
        try {
            departureDate = LocalDateTime.parse(dateStr + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        } catch (Exception e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
            return;
        }
        List<Flight> availableFlights = bookingSystem.searchFlights(origin, destination, departureDate.toLocalDate());

        if (availableFlights.isEmpty()) {
            System.out.println("No flights found matching your criteria.");
        } else {
            System.out.println("\n--- Available Flights ---");
            for (Flight flight : availableFlights) {
                System.out.println(flight); // Assuming Flight class has a toString() method
            }
            createBooking(scanner, availableFlights);
        }
    }


    public void creataBooking(List<Flight>availableFlights)
    {
        System.out.println(" Create Booking ");
        System.out.print("Enter Flight Number to book: ");
        Scanner scanner = new Scanner(System.in);
        String flightNumber = scanner.nextLine();
        Flight selectedFlight = availableFlights.stream()
                .filter(flight -> flight.getFlightnumber().equalsIgnoreCase(flightNumber))
                .findFirst()
                .orElse(null);
        if (selectedFlight == null) {
            System.out.println("Invalid flight number.");
            return;
        }
        System.out.print("Enter number of passengers: ");
        int numPassengers = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < numPassengers; i++) {
            System.out.println("Passenger " + (i + 1) + " Information ---");
            System.out.print("Enter Passenger Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Passport Number: ");
            String passportNumber = scanner.nextLine();
            System.out.print("Enter Date of Birth (YYYY-MM-DD): ");
            String dobStr = scanner.nextLine();
            LocalDateTime dob;
            try {
                dob = LocalDateTime.parse(dobStr + "T00:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (Exception e) {
                System.out.println("Invalid date format for DOB. Please use YYYY-MM-DD.");
                return;
            }
            passengers.add(new Passenger(generatePassengerId(), name, passportNumber, dob.toLocalDate(), ""));
        }
        System.out.print("Select Seat Class (Economy, Business, First Class): ");
        String seatClass = scanner.nextLine();
        Booking newBooking = bookingSystem.createBooking(this, selectedFlight, passengers, seatClass);
        if (newBooking != null) {
            saveBookingReference(newBooking.getBookingReference()); // حفظ مرجع الحجز في الملف
            System.out.println("\nBooking successful! Your booking reference is: " + newBooking.getBookingReference());
            System.out.println("Booking details:\n" + newBooking); // Assuming Booking has a toString()
            bookingSystem.getFileManager().saveBookings(bookingSystem.getBookings()); // تحديث ملف الحجوزات العام
        } else {
            System.out.println("Failed to create booking.");
        }
    }

    public void viewBooking()
    {
        System.out.println("Your Bookings ");
        boolean foundBookings = false;
        try (BufferedReader reader = new BufferedReader(new FileReader(bookingHistoryFile))) {
            String bookingReference;
            while ((bookingReference = reader.readLine()) != null) {
                Booking booking = bookingSystem.getBookings().stream()
                        .filter(b -> b.getBookingReference().equals(bookingReference) && b.getCustomer().getCustomerId().equals(this.customerId))
                        .findFirst()
                        .orElse(null);
                if (booking != null) {
                    System.out.println(booking); // Assuming Booking has a toString()
                    foundBookings = true;
                } else {
                    System.out.println("Error: Booking with reference " + bookingReference + " not found.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading booking history for customer " + customerId + ": " + e.getMessage());
        }
        if (!foundBookings) {
            System.out.println("No bookings found for your account.");
        }
    }


    public void cancelBooking()
    {
        System.out.println("Cancel Booking");
        System.out.print("Enter Booking Reference to cancel");
        Scanner scanner = new Scanner(System.in);
        String BookingRefernceToCancel = scanner.nextLine();
        Booking bookingToRemove = bookingSystem.getBooking().stream.filter(b -> b.getBookingReference().equalsIgnoreCase(bookingReferenceToCancel) && b.getCustomer().getCustomerId().equals(this.customerId)) .findFirst() .orElse(null);
        if (bookingToRemove != null) {
            bookingSystem.cancelBooking(bookingToRemove.getBookingReference());
            removeBookingReference(bookingToRemove.getBookingReference());
            System.out.println("Booking with reference " + bookingReferenceToCancel + " cancelled successfully.");
            bookingSystem.getFileManager().saveBookings(bookingSystem.getBookings());
        } else {
            System.out.println("Booking with reference " + bookingReferenceToCancel + " not found for your account.");
        }
    }

    public void viewSeatsAvailability() {
        System.out.println("View Seat Availability");
        System.out.print("Enter Flight Number to view availability: ");
        Scanner scanner = new Scanner(System.in);
        String flightNumber = scanner.nextLine();

        Flight flight = bookingSystem.getFlights().stream()
                .filter(f -> f.getFlightnumber().equalsIgnoreCase(flightNumber))
                .findFirst()
                .orElse(null);

        if (flight != null) {
            System.out.println("Seat Availability for Flight " + flight.getFlightnumber() + ":");
            System.out.println("Economy: " + flight.getAvailableEconomySeats() + " seats");
            System.out.println("Business: " + flight.getAvailableBusinessSeats() + " seats");
            System.out.println("First Class: " + flight.getAvailableFirstClassSeats() + " seats");
        } else {
            System.out.println("Flight with number " + flightNumber + " not found.");
        }
    }


    @Override
    public void SHOWMENU(){
        System.out.println("\n--- Customer Dashboard ---");
        System.out.println("Welcome, " + getName() + "!");
        int choice;
        do {
            System.out.println("\nChoose an action:");
            System.out.println("1. Search Flights");
            System.out.println("2. View My Bookings");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View Seat Availability");
            System.out.println("5. Logout");
            System.out.print("Enter your choice: ");
            Scanner scanner = new Scanner(System.in);
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    searchFlights(scanner);
                    break;
                case 2:
                    viewBooking();
                    break;
                case 3:
                    cancelBooking();
                    break;
                case 4:
                    viewSeatsAvailability();
                    break;
                case 5:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }
}