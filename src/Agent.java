import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import shared.*;

public class Agent extends User {
    private UUID agentID;
    private String department;
    private double commision;

    public Agent(UUID agentID, String department, double commision, UUID userId, String username, 
    String password, String name, String email, String contactinfo, UserStatus status) {
        super(userId, username, password, name, email, contactinfo, Role.AGENT, status);
        this.agentID = agentID;
        this.department = department;
        this.commision = commision;
    }

    public Agent(String username, String password, String name, String email, String contactinfo, 
    String department, double commision) {
        super(username, password, name, email, contactinfo, Role.AGENT);
        this.agentID = UUID.randomUUID();
        this.department = department;
        this.commision = commision;
    }

    public UUID getAgentID() {
        return agentID;
    }

    public String getDepartment() {
        return department;
    }

    public double getCommision() {
        return commision;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public void setCommision(double commision) {
        this.commision = commision;
    }

    @Override
    public void showMenu() {
        System.out.println("---- Agent Menu ----");
        System.out.println("1. Manage Flights");
        System.out.println("2. Create Booking for Customer");
        System.out.println("3. Modify Booking");
        System.out.println("4. Generate Reports");
        System.out.println("5. Update Profile");
        System.out.println("6. Logout");
        // Scanner scanner = new Scanner(System.in);
        // int choice = scanner.nextInt();
        // scanner.nextLine();
        // switch (choice) {
        //     case 1:
        //         manageFlights();
        //         break;
        //     case 2:
        //         System.out.println("Creating Booking for Customer...");
        //         createBooking();
        //         break;
        //     case 3:
        //         // Call a method to modify booking (to be implemented)
        //         System.out.println("Modifying Booking...");
        //         break;
        //     case 4:
        //         generateReports();
        //         break;
        //     case 5:
        //         updateprofile(username, password, name, email, contactinfo);
        //         break;
        //     case 6:
        //         logout();
        //         break;
        //     default:
        //         System.out.println("Invalid option. Try again.");
        //         showMenu();
        //         break;
        // }
    }

    public void manageFlights() {
        System.out.println("Managing flights...");
        System.out.println("---- Manage Flights ----");
        System.out.println("1. Add New Flight"); // add new flight directly to the file and Booking system class
        System.out.println("2. Edit Existing Flight"); // search for the flight then pass the parameters after validation
        System.out.println("3. Remove Flight"); // external with exception if there's any booking
        System.out.println("4. View All Flights"); // external
        System.out.println("5. Back to Menu");
        // Scanner scanner = new Scanner(System.in);
        // int choice = scanner.nextInt();
        // scanner.nextLine();

        // switch (choice) {
        //     case 1:
        //         addNewFlight();
        //         break;
        //     case 2:
        //         editFlight();
        //         break;
        //     case 3:
        //         removeFlight();
        //         break;
        //     case 4:
        //         viewFlights();
        //         break;
        //     case 5:
        //         showMenu();  // العودة إلى القائمة الرئيسية
        //         break;
        //     default:
        //         System.out.println("Invalid option. Try again.");
        //         manageFlights();
        //         break;
        // }
    }

    public void CreateFlight() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        System.out.println("=== Flight Data Entry ===");

        System.out.print("Enter airline name: ");
        String airline = scanner.nextLine();

        System.out.print("Enter origin airport/city: ");
        String origin = scanner.nextLine();

        System.out.print("Enter destination airport/city: ");
        String destination = scanner.nextLine();

        LocalDateTime departureTime = null;
        while (departureTime == null) {
            System.out.print("Enter departure date and time (yyyy-MM-dd HH:mm): ");
            String depInput = scanner.nextLine();
            try {
                departureTime = LocalDateTime.parse(depInput, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please use yyyy-MM-dd HH:mm (e.g., 2025-05-13 14:30)");
            }
        }

        LocalDateTime arrivalTime = null;
        while (arrivalTime == null) {
            System.out.print("Enter arrival date and time (yyyy-MM-dd HH:mm): ");
            String arrInput = scanner.nextLine();
            try {
                arrivalTime = LocalDateTime.parse(arrInput, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid format. Please use yyyy-MM-dd HH:mm (e.g., 2025-05-13 18:45)");
            }
        }

        System.out.print("Enter available Economy seats: ");
        int availableEconomySeats = scanner.nextInt();
        System.out.print("Enter booked Economy seats: ");
        int bookedEconomySeats = scanner.nextInt();
        System.out.print("Enter Economy seat price: ");
        double economyPrice = scanner.nextDouble();

        System.out.print("Enter available Business seats: ");
        int availableBusinessSeats = scanner.nextInt();
        System.out.print("Enter booked Business seats: ");
        int bookedBusinessSeats = scanner.nextInt();
        System.out.print("Enter Business seat price: ");
        double businessPrice = scanner.nextDouble();

        System.out.print("Enter available First Class seats: ");
        int availableFirstClassSeats = scanner.nextInt();
        System.out.print("Enter booked First Class seats: ");
        int bookedFirstClassSeats = scanner.nextInt();
        System.out.print("Enter First Class seat price: ");
        double firstClassPrice = scanner.nextDouble();

        scanner.close();

        System.out.println("\n=== Flight Data Summary ===");
        System.out.printf("%-18s: %s%n", "Airline", airline);
        System.out.printf("%-18s: %s%n", "Origin", origin);
        System.out.printf("%-18s: %s%n", "Destination", destination);
        System.out.printf("%-18s: %s%n", "Departure", departureTime.format(formatter));
        System.out.printf("%-18s: %s%n", "Arrival", arrivalTime.format(formatter));
        System.out.printf("%-18s: %d available, %d booked, $%.2f each%n", "Economy Seats", availableEconomySeats, bookedEconomySeats, economyPrice);
        System.out.printf("%-18s: %d available, %d booked, $%.2f each%n", "Business Seats", availableBusinessSeats, bookedBusinessSeats, businessPrice);
        System.out.printf("%-18s: %d available, %d booked, $%.2f each%n", "First Class Seats", availableFirstClassSeats, bookedFirstClassSeats, firstClassPrice);

        Flight flight = new Flight(airline, origin, destination, departureTime, arrivalTime,
        availableEconomySeats, bookedEconomySeats, economyPrice, 
        availableBusinessSeats, bookedBusinessSeats, businessPrice,
        availableFirstClassSeats, bookedFirstClassSeats,  firstClassPrice);

        BookingSystem.getInstance().saveFlight(flight);
    }

    // search for the customer (assuming existance)
    // public Booking createBookingForCustomer(Customer customer, Flight flight) {
    //     Scanner scanner = new Scanner(System.in);

    //     System.out.print("Enter customer name: ");
    //     String customerName = scanner.nextLine();

    //     System.out.print("Enter flight number: ");
    //     String flightNumber = scanner.nextLine();

    //     Flight selectedFlight = findFlight(flightNumber);
    //     if (selectedFlight == null) {
    //         System.out.println("❌ Flight not found.");
    //         return;
    //     }

    //     System.out.println("Choose seat class (economy/business/first): ");
    //     String seatClass = scanner.nextLine().trim().toLowerCase();

    //     boolean success = false;

    //     // التحقق من نوع المقعد الذي اختاره العميل
    //     switch (seatClass.toLowerCase()) {
    //         case "economy":
    //             if (selectedFlight.getAvailableEconomySeats() > 0) {
    //                 selectedFlight.setAvailableEconomySeats(selectedFlight.getAvailableEconomySeats() - 1); // تحديث المقاعد
    //                 selectedFlight.setAvailableBusinessSeats(selectedFlight.getAvailableEconomySeats() + 1);
    //                 success = true;
    //             } else {
    //                 System.out.println("Unavailable seats in economic class");
    //             }
    //             break;

    //         case "business":
    //             if (selectedFlight.getAvailableBusinessSeats() > 0) {
    //                 selectedFlight.setAvailableBusinessSeats(selectedFlight.getAvailableBusinessSeats() - 1); // تحديث المقاعد
    //                 selectedFlight.setAvailableBusinessSeats(selectedFlight.getBookedBusinessSeats() + 1);
    //                 success = true;
    //             } else {
    //                 System.out.println("There is no available seats in business class ");
    //             }
    //             break;

    //         case "first":
    //             if (selectedFlight.getAvailableFirstClassSeats() > 0) {
    //                 selectedFlight.setAvailableFirstClassSeats(selectedFlight.getAvailableFirstClassSeats() - 1); // تحديث المقاعد
    //                 selectedFlight.setAvailableBusinessSeats(selectedFlight.getAvailableFirstClassSeats() + 1);
    //                 success = true;
    //             } else {
    //                 System.out.println("Unavailable seats in First Class");
    //             }
    //             break;

    //         default:
    //             System.out.println("❌ Invalid seat class.");
    //             return;
    //     }

    //     if (success) {
    //         System.out.println("✅ Booking successful for " + customerName + " on flight " + flightNumber +".");
    //     }
    // }





    // public void modifyBooking(Booking booking, String name, ) {
    //     Scanner scanner = new Scanner(System.in);

    //     System.out.print("Enter customer name: ");
    //     String customerName = scanner.nextLine();

    //     System.out.print("Enter flight number: ");
    //     String flightNumber = scanner.nextLine();

    //     Flight flight = findFlight(flightNumber);
    //     if (flight == null) {
    //         System.out.println("❌ Flight not found.");
    //         return;
    //     }

    //     System.out.println("Do you want to change the date/time? (yes/no)");
    //     String changeTime = scanner.nextLine().trim().toLowerCase();
    //     if (changeTime.equals("yes")) {
    //         try {
    //             System.out.print("Enter new departure time (yyyy-MM-dd HH:mm): ");
    //             String dep = scanner.nextLine();
    //             System.out.print("Enter new arrival time (yyyy-MM-dd HH:mm): ");
    //             String arr = scanner.nextLine();

    //             flight.setDepartureTime(LocalDateTime.parse(dep));
    //             flight.setArrivalTime(LocalDateTime.parse(arr));
    //             System.out.println("✅ Schedule updated.");
    //         } catch (Exception e) {
    //             System.out.println("❌ Invalid date format.");
    //             return;
    //         }
    //     }

    //     System.out.println("Do you want to change seat class? (yes/no)");
    //     String changeClass = scanner.nextLine().trim().toLowerCase();
    //     if (changeClass.equals("yes")) {
    //         System.out.print("Enter your current seat class (economy/business/first): ");
    //         String oldClass = scanner.nextLine().trim().toLowerCase();

    //         System.out.print("Enter new seat class (economy/business/first): ");
    //         String newClass = scanner.nextLine().trim().toLowerCase();

    //         // تحقق من توفر مقعد في الكلاس الجديد
    //         boolean canChange = false;

    //         if (newClass.equals("economy") && flight.getAvailableEconomySeats() > 0) {
    //             flight.setAvailableEconomySeats(flight.getAvailableEconomySeats() - 1);
    //             canChange = true;
    //         } else if (newClass.equals("business") && flight.getAvailableBusinessSeats() > 0) {
    //             flight.setAvailableBusinessSeats(flight.getAvailableBusinessSeats() - 1);
    //             canChange = true;
    //         } else if (newClass.equals("first") && flight.getAvailableFirstClassSeats() > 0) {
    //             flight.setAvailableFirstClassSeats(flight.getAvailableFirstClassSeats() - 1);
    //             canChange = true;
    //         }

    //         if (canChange) {
    //             // استرجاع المقعد من الكلاس القديم
    //             if (oldClass.equals("economy")) {
    //                 flight.setAvailableEconomySeats(flight.getAvailableEconomySeats() + 1);
    //             } else if (oldClass.equals("business")) {
    //                 flight.setAvailableBusinessSeats(flight.getAvailableBusinessSeats() + 1);
    //             } else if (oldClass.equals("first")) {
    //                 flight.setAvailableFirstClassSeats(flight.getAvailableFirstClassSeats() + 1);
    //             }

    //             System.out.println("✅ Seat class changed successfully.");
    //         } else {
    //             System.out.println("❌ No available seats in new class.");
    //         }
    //     } else {
    //         System.out.println("⏳ Keeping the same seat class.");
    //     }

    //     // تحديث الرحلة في الملف (لو عندك الدالة دي)
    //     // updateFlightInFile(flight);
    // }

    public void generateReports () {
        System.out.println("---- Generate Report Menu ----");
        System.out.println("1. View All Flights");
        System.out.println("2. View Booked Seats");
        System.out.println("3. View Available Seats");
        System.out.println("4. Back to Main Menu");
    }

    @Override
    public String toString(){
        return agentID + "," + department + "," + commision + "," + super.toString();
    }
}

