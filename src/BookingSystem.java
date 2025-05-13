import java.util.*;

import payment.*;
import shared.Utils;
import shared.PaymentMethod;
import shared.PaymentStatus;
import java.time.LocalDateTime;

public class BookingSystem {
    private List<Administrator> admins;
    private List<Agent> agents;
    private List<Customer> customers;
    private List<Flight> flights;
    private List<Booking> bookings;
    private List<Payment> payments;
    private List<Passenger> passengers;
    private List<SeatSelection> seats;
    private Scanner scanner = new Scanner(System.in);

    public BookingSystem() {
        admins = FileManager.loadAdmins();
        agents = FileManager.loadAgents();
        customers = FileManager.loadCustomers();
        // flights = FileManager.loadFlights();
        // bookings = FileManager.loadBookings();
        // payments = FileManager.loadPayments();
        // passengers = FileManager.loadPassengers();
        // seats = FileManager.loadSeatSelections();

        // for(Booking booking : bookings) {
        //     for(Customer customer : customers) {
        //         if(booking.getCustomerId() == customer.getCustomerId()) {
        //             customer.addBooking(booking);
        //             booking.setCustomer(customer);
        //         }
        //     }

        //     for(Flight flight : flights)
        //     {
        //         if(booking.getFlightNumber().equalsIgnoreCase(flight.getFlightNumber())) {
        //             booking.setFlight(flight);
        //         }
        //     }

        //     for(Passenger passenger : passengers) {
        //         if(booking.getBookingReferenceNumber().equalsIgnoreCase(passenger.getBookingReferenceNumber())) {
        //             booking.addPassenger(passenger);
        //         }

        //         for(SeatSelection seat : seats) {
        //             if(seat.getBookingReferanceNumber().equalsIgnoreCase(booking.getBookingReferenceNumber()) && 
        //             seat.getPassengerId().equals(passenger.getPassengerId())) {
        //                 booking.addSeatSelection(seat);
        //             }
        //         }
        //     }
        // }
        Login();
    }

    public void Login() {
        System.out.println("=== User Login ===");
        System.out.print("Please enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Please enter your password: ");
        String password = Utils.hashPassword(scanner.nextLine());
        
        User user = null;
        for(Administrator admin : admins) {
            if(admin.username.equals(username) && admin.password.equals(password))
                user = admin;
        }
        
        if(user == null) {
            for(Agent agent : agents) {
                if(agent.username.equals(username) && agent.password.equals(password))
                    user = agent;
            }
        }

        if(user == null) {
            for(Customer customer : customers) {
                if(customer.username.equals(username) && customer.password.equals(password))
                    user = customer;
            }
        }

        if(user == null)
            throw new SecurityException("You Entered Invalid Credentials, please check and try again!");
        user.showMenu();
    }

    public List<Flight> searchFlights(String origin, String destination, LocalDateTime date) {
        List<Flight> result = new ArrayList<>();
        for(Flight flight : this.flights) {
            if(flight.getOrigin().equalsIgnoreCase(origin) && 
            flight.getDestination().equalsIgnoreCase(destination) &&
            flight.getDepartureTime().equals(date)) {
                result.add(flight);
            }
        }
        return result;
    }

    public void createBooking(Booking booking) {
        this.bookings.add(booking);
        FileManager.saveBooking(booking);
    }

    public void processPayment(Booking booking) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Payment Processing System ===");
        System.out.println("Select payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Bank Transfer");
        System.out.print("Enter choice (1 or 2): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); 

        Payment newPayment = null;
        PaymentDetails paymentDetails = null;

        switch (choice) {
            case 1:
                // Credit Card Payment
                System.out.print("Enter card holder name: ");
                String ccHolder = scanner.nextLine();
                System.out.print("Enter card number (16 digits): ");
                String ccNumber = scanner.nextLine();
                System.out.print("Enter expiry date (MM/YY): ");
                String ccExpiry = scanner.nextLine();
                System.out.print("Enter CVV: ");
                String ccCVV = scanner.nextLine();
                System.out.print("Enter payment amount: ");
                double ccAmount = scanner.nextDouble();

                newPayment = new Payment(booking.getBookingReferenceNumber(), ccAmount, PaymentMethod.CREDITCARD, PaymentStatus.SUSPENDED, LocalDateTime.now());
                paymentDetails = new CreditCardPaymentDetails(
                        ccAmount, ccNumber, ccHolder, ccExpiry, ccCVV);
                break;

            case 2:
                // Bank Transfer Payment
                System.out.print("Enter account holder name: ");
                String btHolder = scanner.nextLine();
                System.out.print("Enter bank account number: ");
                String btAccount = scanner.nextLine();
                System.out.print("Enter bank code (8 characters): ");
                String btBankCode = scanner.nextLine();
                System.out.print("Enter payment reference: ");
                String btReference = scanner.nextLine();
                System.out.print("Enter payment amount: ");
                double btAmount = scanner.nextDouble();

                newPayment = new Payment(booking.getBookingReferenceNumber(), btAmount, PaymentMethod.BANK_TRANSFFER, PaymentStatus.SUSPENDED, LocalDateTime.now());
                paymentDetails = new BankTransferPaymentDetails(
                        btAmount, btAccount, btHolder, btBankCode, btReference);
                    break;

            default:
                System.out.println("Invalid choice. Exiting.");
                scanner.close();
                return;
        }

        newPayment.processPayment(paymentDetails);
        if(newPayment.getStatus() == PaymentStatus.SUCCESS) {
            booking.confirmBooking();
            this.saveBooking(booking, newPayment);
            generateTicket(booking, newPayment);
        }
        else
            System.out.println("Booking not connfiirmed due to failed payment transaction");

        scanner.close();
    }

    public void generateTicket(Booking booking, Payment payment) {
        
    }

    private void saveBooking(Booking booking, Payment payment) {
        for(Passenger passenger : booking.getPassengers()) 
            this.passengers.add(passenger);
        for(SeatSelection seat : seats)
            this.seats.add(seat);
        this.bookings.add(booking);
        this.payments.add(payment);

        FileManager.savePassengerBatch(booking.getPassengers(), false);
        FileManager.saveSeatSelectionBatch(booking.getSeatSelections(), false);
        FileManager.saveBooking(booking);
        FileManager.savePayment(payment);
    }
}