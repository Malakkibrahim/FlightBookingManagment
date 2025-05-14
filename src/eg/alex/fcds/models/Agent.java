package eg.alex.fcds.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

import javax.management.OperationsException;

import eg.alex.fcds.BookingSystem;
import eg.alex.fcds.models.shared.*;
import eg.alex.fcds.view.Console;

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
        List<String> mainMenu = new ArrayList<>() { 
            {
                add("Manage Flights");
                add("Create Booking for Customer");
                add("Modify Booking");
                add("Generate Reports");
            }
        };
        int choice = Console.displayMenu("Agent Menu", mainMenu);
        
        switch (choice) {
            case 1:
                this.manageFlights();
                break;

            default:
                break;
        }
    }

    public void manageFlights() {
        List<String> flightManagementMenu = new ArrayList<>() {{
            add("Add New Flight");
            add("Edit Existing Flight");
            add("Remove Flight");
            add("View All Flights");
            add("Back to Menu");
        }};
        int choice = Console.displayMenu("Manage Flights", flightManagementMenu);

        switch (choice) {
            case 1:
                this.createFlight();
                break;
            case 2:
            try {
                this.editFlight();;
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
                break;
            case 3:
                try {
                    this.removeFlight();
                } catch(Exception e) {
                    System.out.println(e.getMessage());
                }
                break;
            case 4:
                this.viewFlights();
                break;
            case 5:
                this.showMenu();
                break;
            default:
                break;
        }
    }

    // TODO refactor
    public void createFlight() {
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

        

        System.out.println("\n=== Flight Data Summary ===");
        System.out.printf("%-18s: %s%n", "Airline", airline);
        System.out.printf("%-18s: %s%n", "Origin", origin);
        System.out.printf("%-18s: %s%n", "Destination", destination);
        System.out.printf("%-18s: %s%n", "Departure", departureTime.format(formatter));
        System.out.printf("%-18s: %s%n", "Arrival", arrivalTime.format(formatter));
        System.out.printf("%-18s: %d available, %d booked, $%.2f each%n", "Economy Seats", availableEconomySeats,
                bookedEconomySeats, economyPrice);
        System.out.printf("%-18s: %d available, %d booked, $%.2f each%n", "Business Seats", availableBusinessSeats,
                bookedBusinessSeats, businessPrice);
        System.out.printf("%-18s: %d available, %d booked, $%.2f each%n", "First Class Seats", availableFirstClassSeats,
                bookedFirstClassSeats, firstClassPrice);

        Flight flight = new Flight(airline, origin, destination, departureTime, arrivalTime,
                availableEconomySeats, bookedEconomySeats, economyPrice,
                availableBusinessSeats, bookedBusinessSeats, businessPrice,
                availableFirstClassSeats, bookedFirstClassSeats, firstClassPrice);

        this.app.saveFlight(flight);
    }

    public void editFlight() throws OperationsException{
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter flight number to edit: ");
        String flightNumber = scanner.nextLine();

        Flight flight = this.app.getFlightByNumber(flightNumber);
        if (flight == null) {
            System.out.println("No flight found with this flight number.");
            this.showMenu();
        }

        System.out.println("=== Update Flight Details ===");

        // Departure Time
        LocalDateTime newDepTime = null;
        while (newDepTime == null) {
            String newDepTimeStr = scanner.nextLine();
            System.out.print("Enter new departure time (yyyy-MM-dd HH:mm) (current: " + flight.getDepartureTime() + "): ");
            try {
                newDepTime = LocalDateTime.parse(newDepTimeStr,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                flight.setDepartureTime(newDepTime);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format");
            }
        }

        // Arrival Time
        LocalDateTime newArrTime = null;
        while (newArrTime == null) {
            String newArrTimeStr = scanner.nextLine();
            System.out.print("Enter new arrival time (yyyy-MM-dd HH:mm) (current: " + flight.getArrivalTime() + "): ");
            try {
                newArrTime = LocalDateTime.parse(newArrTimeStr,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                flight.setArrivalTime(newArrTime);
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format");
            }
        }

        // Economy class
        System.out.println("Enter economy class updated information");
        Double eprice = null;
        while (eprice == null) {
            try {
                System.out.println("Enter new Economy seats price (current: " + flight.getEconomyFilghtSeatsPrice());
                eprice = scanner.nextDouble();
            } catch (Exception e) {
                System.out.println("Please enter a valid price format");
            } 
        }

        System.out.println("Enter economy class updated seats availability");
        Integer eavailability = null;
        while (eavailability == null) {
            try {
                System.out.println("Enter new Economy seats availability (current: " + flight.getEconomyFilghtSeatsAvailability());
                eavailability = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a valid number");
            } 
        }

        // Business class
        System.out.println("Enter Business class updated information");
        Double bprice = null;
        while (bprice == null) {
            try {
                System.out.println("Enter new Business seats price (current: " + flight.getBusinessFlightSeatsPrice());
                bprice = scanner.nextDouble();
            } catch (Exception e) {
                System.out.println("Please enter a valid price format");
            } 
        }

        System.out.println("Enter Business class updated seats availability");
        Integer bavailability = null;
        while (bavailability == null) {
            try {
                System.out.println("Enter new Business seats availability (current: " + flight.getBusinessFlightSeatsAvailability());
                bavailability = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a valid number");
            } 
        }

        // FirstClass class
        System.out.println("Enter FirstClass class updated information");
        Double fprice = null;
        while (fprice == null) {
            try {
                System.out.println("Enter new FirstClass seats price (current: " + flight.getFirstClassFilghtSeatsPrice());
                fprice = scanner.nextDouble();
            } catch (Exception e) {
                System.out.println("Please enter a valid price format");
            } 
        }

        System.out.println("Enter FirstClass class updated seats availability");
        Integer favailability = null;
        while (favailability == null) {
            try {
                System.out.println("Enter new FirstClass seats availability (current: " + flight.getFirstClassFilghtSeatsAvailability());
                favailability = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Please enter a valid number");
            } 
        }
        
        flight.update(newDepTime, newArrTime, eprice, eavailability, bprice, bavailability, fprice, favailability);
        this.app.updateFlight();
    }

    public void removeFlight() throws OperationsException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter flight number to edit: ");
        String flightNumber = scanner.nextLine();

        Flight flight = this.app.getFlightByNumber(flightNumber);
        if (flight == null) {
            System.out.println("No flight found with this flight number.");
            
            this.showMenu();
        }
        
        this.app.removeFlight(flightNumber);
    }

    public void viewFlights() {
        this.app.printFlights();
    }


    @Override
    public String toString() {
        return agentID + "," + department + "," + commision + "," + super.toString();
    }
}
