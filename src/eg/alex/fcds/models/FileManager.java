package eg.alex.fcds.models;
import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

import eg.alex.fcds.models.shared.BookingStatus;
import eg.alex.fcds.models.shared.PaymentMethod;
import eg.alex.fcds.models.shared.PaymentStatus;
import eg.alex.fcds.models.shared.SeatClass;
import eg.alex.fcds.models.shared.SecurityLevel;
import eg.alex.fcds.models.shared.UserStatus;

public class FileManager {
    private static final String BASE = "../storage/";

    private static final String ADMIN_FILE = BASE + "administrators.txt";
    private static final String AGENT_FILE = BASE + "agents.txt";
    private static final String CUSTOMER_FILE = BASE + "customers.txt";

    private static final String FLIGHT_FILE = BASE + "flight.txt";
    private static final String BOOKING_FILE = BASE + "booking.txt";
    private static final String PAYMENTS_FILE = BASE + "payments.txt";
    private static final String PASSENGER_FILE = BASE + "passenger.txt";
    private static final String SEAT_SELECTION_FILE = BASE + "seat_selection.txt";

    private static final String SETTINGS_FILE = BASE + "settings.txt";
    private static final String LOG_FILE = BASE + "logs.txt";

    public static void saveAdmin(Administrator administrator) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_FILE, true))) {
            writer.write(administrator.toString());
            writer.newLine();
            System.out.println("✅ Administrator saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving administrator: " + e.getMessage());
        }
    }

    public static void saveAdminBatch(List<Administrator> admins, boolean rewrite) 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_FILE, !rewrite))) {
            for(Administrator admin : admins) {
                writer.write(admin.toString());
                writer.newLine();
            }
            System.out.println("✅ Administrators saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving administrators: " + e.getMessage());
        }
    }

    public static List<Administrator> loadAdmins() {
        List<Administrator> admins = new ArrayList<>();
        File file = new File(ADMIN_FILE);

        if (!file.exists()) {
            return admins;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                UUID adminId = UUID.fromString(data[0]);
                SecurityLevel securityLevel = SecurityLevel.valueOf(data[1]);
                UUID userId = UUID.fromString(data[2]);
                String username = data[3];
                String password = data[4];
                String name = data[5];
                String email = data[6];
                String contact = data[7];
                UserStatus status = UserStatus.valueOf(data[8]);

                admins.add(new Administrator(adminId, securityLevel, userId, username, password, name, email, contact, status));
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading administrator file: " + e.getMessage());
        }

        return admins;
    }




    public static void saveAgent(Agent agent) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AGENT_FILE, true))) {
            writer.write(agent.toString());
            writer.newLine();
            System.out.println("✅ Agent saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving agent: " + e.getMessage());
        }
    }

    public static void saveAgentBatch(List<Agent> agents, boolean rewrite) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(AGENT_FILE, !rewrite))) {
            for(Agent agent : agents) {
                writer.write(agent.toString());
                writer.newLine();
            }
            System.out.println("✅ Agent saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving agent: " + e.getMessage());
        }
    }


    public static List<Agent> loadAgents() {
        List<Agent> agents = new ArrayList<>();
        File file = new File(AGENT_FILE);

        if (!file.exists()) {
            return agents;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                UUID agentId = UUID.fromString(data[0]);
                String depart = data[1];
                double commision = Double.parseDouble(data[2]);
                UUID userId = UUID.fromString(data[3]);
                String username = data[4];
                String password = data[5];
                String name = data[6];
                String email = data[7];
                String contactInfo = data[8];
                UserStatus status = UserStatus.valueOf(data[9]);

                agents.add(new Agent(agentId, depart, commision, userId,  username,  password,  name,  email,  contactInfo, status));
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading agent file: " + e.getMessage());
        }

        return agents;
    }



    public static void saveCustomer(Customer customer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_FILE, true))) {
            writer.write(customer.toString());
            writer.newLine();
            System.out.println("✅ Customer saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving customer: " + e.getMessage());
        }
    }

    public static void saveCustomerBatch(List<Customer> customers, boolean rewrite) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CUSTOMER_FILE, !rewrite))) {
            for(Customer customer : customers) {
                writer.write(customer.toString());
                writer.newLine();
            }
            System.out.println("✅ Customers saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving customers: " + e.getMessage());
        }
    }


    public static List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();
        File file = new File(CUSTOMER_FILE);

        if (!file.exists()) {
            return customers;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                UUID customerId = UUID.fromString(data[0]);
                String address = data[1];
                String preferance = data[2];
                UUID userId = UUID.fromString(data[3]);
                String username = data[4];
                String password = data[5];
                String name = data[6];
                String email = data[7];
                String contactInfo = data[8];
                UserStatus status = UserStatus.valueOf(data[9]);

                customers.add(new Customer(customerId, address, preferance, userId,  username,  password,  name,  email,  contactInfo, status, null));
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading agent file: " + e.getMessage());
        }

        return customers;
    }


    public static void saveBooking(Booking booking) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKING_FILE, true))) {
            writer.write(booking.toString());
            writer.newLine();
            System.out.println("✅ Booking saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving booking: " + e.getMessage());
        }
    }

    public static void saveBookingBatch(List<Booking> bookings, boolean rewrite) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BOOKING_FILE, !rewrite))) {
            for(Booking booking : bookings) {
                writer.write(booking.toString());
                writer.newLine();
            }
            System.out.println("✅ Bookings saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving bookings: " + e.getMessage());
        }
    }

    public static List<Booking> loadBookings() {
        List<Booking> bookings = new ArrayList<>();
        File file = new File(BOOKING_FILE);

        if (!file.exists()) {
            return bookings;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String bookingReference = data[0];
                BookingStatus status = BookingStatus.valueOf(data[1]);
                PaymentStatus paymentStatus = PaymentStatus.valueOf(data[2]);
                UUID customerId = UUID.fromString(data[3]);
                String flightNumber = data[4];

                bookings.add(new Booking(bookingReference, status, paymentStatus, customerId, flightNumber, null, null, null, null));
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading booking file: " + e.getMessage());
        }

        return bookings;
    }


    public static void saveFlight(Flight flight) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FLIGHT_FILE, true))) {
            writer.write(flight.toString());
            writer.newLine();
            System.out.println("✅ Flight saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving flight: " + e.getMessage());
        }
    }

    public static void saveFlightBatch(List<Flight> flights, boolean rewrite) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FLIGHT_FILE, !rewrite))) {
            for(Flight flight : flights) {
                writer.write(flight.toString());
                writer.newLine();
            }
            System.out.println("✅ Flights saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving flights: " + e.getMessage());
        }
    }

    public static List<Flight> loadFlights() {
        List<Flight> flights = new ArrayList<>();
        File file = new File(FLIGHT_FILE);

        if (!file.exists()) {
            return flights;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String flightNumber = data[0];
                String airline = data[1];
                String origin = data[2];
                String destination = data[3];
                LocalDateTime departureTime = LocalDateTime.parse(data[4]);
                LocalDateTime arrivalTime = LocalDateTime.parse(data[5]);
                double economyPrice = Double.parseDouble(data[6]);
                int economyAvailable = Integer.parseInt(data[7]);
                int economyBooked = Integer.parseInt(data[8]);
                double businessPrice = Double.parseDouble(data[9]);
                int businessAvailable = Integer.parseInt(data[10]);
                int businessBooked = Integer.parseInt(data[11]);
                double firstClassPrice = Double.parseDouble(data[12]);
                int firstClassAvailable = Integer.parseInt(data[13]);
                int firstClassBooked = Integer.parseInt(data[14]);

                flights.add(new Flight(flightNumber, airline, origin, destination, departureTime, arrivalTime, 
                economyAvailable, economyBooked, economyPrice, businessAvailable, businessBooked, businessPrice, firstClassAvailable, firstClassBooked, firstClassPrice));
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading flights file: " + e.getMessage());
        }

        return flights;
    }


    public static void savePayment(Payment payment) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PAYMENTS_FILE, true))) {
            writer.write(payment.toString());
            writer.newLine();
            System.out.println("✅ Payment saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving payment: " + e.getMessage());
        }
    }

    public static void savePaymentBatch(List<Payment> payments, boolean rewrite) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PAYMENTS_FILE, !rewrite))) {
            for(Payment payment : payments) {
                writer.write(payment.toString());
                writer.newLine();
            }
            System.out.println("✅ Payments saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving payments: " + e.getMessage());
        }
    }

    public static List<Payment> loadPayments() {
        List<Payment> payments = new ArrayList<>();
        File file = new File(PAYMENTS_FILE);

        if (!file.exists()) {
            return payments;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String paymentId = data[0];
                String bookingReference = data[1];
                double amount = Double.parseDouble(data[2]);
                PaymentMethod method = PaymentMethod.valueOf(data[3]);
                PaymentStatus status = PaymentStatus.valueOf(data[4]);
                LocalDateTime transactionDate = LocalDateTime.parse(data[5]);

                payments.add(new Payment(paymentId, bookingReference, amount, method,  status, transactionDate));
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading payments file: " + e.getMessage());
        }

        return payments;
    }


    public static void savePassenger(Passenger passenger) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PASSENGER_FILE, true))) {
            writer.write(passenger.toString());
            writer.newLine();
            System.out.println("✅ Passanger saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving passanger: " + e.getMessage());
        }
    }

    public static void savePassengerBatch(List<Passenger> passangers, boolean rewrite) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PASSENGER_FILE, !rewrite))) {
            for(Passenger passanger : passangers) {
                writer.write(passanger.toString());
                writer.newLine();
            }
            System.out.println("✅ Passengers saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving passangers: " + e.getMessage());
        }
    }

    public static List<Passenger> loadPassengers() {
        List<Passenger> passengers = new ArrayList<>();
        File file = new File(PASSENGER_FILE);

        if (!file.exists()) {
            return passengers;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                UUID passengerId = UUID.fromString(data[0]);
                String bookingReferanceNumber = data[1];
                String name = data[2];
                String passportNumber = data[3];
                LocalDateTime dateOfbirth = LocalDateTime.parse(data[4]);
                String specialRequests = data[5];

                passengers.add(new Passenger(passengerId, bookingReferanceNumber, name, passportNumber, dateOfbirth,  specialRequests));
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading passenger file: " + e.getMessage());
        }

        return passengers;
    }

    public static void saveSeatSelection(SeatSelection seatSelection) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SEAT_SELECTION_FILE, true))) {
            writer.write(seatSelection.toString());
            writer.newLine();
            System.out.println("✅ Seat saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving seat: " + e.getMessage());
        }
    }

    public static void saveSeatSelectionBatch(List<SeatSelection> seatSelections, boolean rewrite) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(SEAT_SELECTION_FILE, !rewrite))) {
            for(SeatSelection selecttion : seatSelections) {
                writer.write(selecttion.toString());
                writer.newLine();
            }
            System.out.println("✅ Seats saved successfully.");
        } catch (IOException e) {
            System.out.println("❌ Error saving seats: " + e.getMessage());
        }
    }

    public static List<SeatSelection> loadSeatSelections() {
        List<SeatSelection> seatSelections = new ArrayList<>();
        File file = new File(SEAT_SELECTION_FILE);

        if (!file.exists()) {
            return seatSelections;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String bookingReferanceNumber = data[0];
                UUID passengerId = UUID.fromString(data[1]);
                SeatClass seatClass = SeatClass.valueOf(data[2]);

                seatSelections.add(new SeatSelection(bookingReferanceNumber, passengerId, seatClass));
            }
        } catch (IOException e) {
            System.out.println("❌ Error reading seat selections file: " + e.getMessage());
        }

        return seatSelections;
    }


    public static void updateSystemSettings(Map<String, String> updates) 
    {
        Properties props = new Properties();

        try {
            File file = new File(SETTINGS_FILE);
            if (file.exists()) {
                try (FileReader reader = new FileReader(file)) {
                    props.load(reader);
                }
            }

            // Apply updates from the map
            for (Map.Entry<String, String> entry : updates.entrySet()) {
                props.setProperty(entry.getKey(), entry.getValue());
            }

            // Save updated properties back to the file
            try (FileWriter writer = new FileWriter(file)) {
                props.store(writer, "App Settings");
            }

            System.out.println("Updated settings: " + updates);
        } catch (IOException e) {
            System.err.println("Failed to update settings: " + e.getMessage());
        }
    }

    public static void readLogs() {
        File file = new File(LOG_FILE);

        if (!file.exists()) {
            System.out.println("Log file does not exist.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            System.out.println("----- Logs -----");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("----------------");
        } catch (IOException e) {
            System.err.println("Error reading logs: " + e.getMessage());
        }
    }
}




