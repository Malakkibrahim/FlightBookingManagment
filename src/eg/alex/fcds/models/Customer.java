package eg.alex.fcds.models;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

import eg.alex.fcds.models.shared.Role;
import eg.alex.fcds.models.shared.UserStatus;

public class Customer extends User {
    private UUID customerId;
    private String address;
    private String prefrence;
    private List<Booking> bookingHistory;

    public Customer(UUID customerId, String address, String prefrence, UUID userId, String username, 
    String password, String name, String email, String contactinfo, UserStatus status, List<Booking> bookingHistory) {
        super(userId, username, password, name, email, contactinfo, Role.CUSTOMER, status);
        this.customerId = customerId;
        this.address = address;
        this.prefrence = prefrence;
        if(bookingHistory != null)
            this.bookingHistory = new ArrayList<>(bookingHistory);
    }

    public Customer(String username, String password, String name, String email, String contactinfo,
    String address, String prefrence) {
        super(username, password, name, email, contactinfo, Role.CUSTOMER);
        this.customerId = UUID.randomUUID();
        this.address = address;
        this.prefrence = prefrence;
        this.bookingHistory = new ArrayList<>();
    }

    public UUID getCustomerId()
    {
        return this.customerId;
    }

    public String getAddress() {
        return address;
    }

    public String getPrefrence()
    {
        return this.prefrence;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPreferancr(String prefrence) {
        this.prefrence = prefrence;
    }

    public void showMenu(){
        System.out.println("\n--- Customer Dashboard ---");
        System.out.println("Welcome, " + getName() + "!");
        System.out.println("\nChoose an action:");
        System.out.println("1. Search Flights");
        System.out.println("2. View My Bookings");
        System.out.println("3. Cancel Booking");
        System.out.println("4. Logout");
    }

    public Flight searcFlights() {
        return null;
    }

    public void addBooking(Booking booking)
    {
        this.bookingHistory.add(booking);
    }

    public Booking createBooking(Flight flight) {
        Booking booking = new Booking(this.customerId, flight.getFlightNumber(), this, flight, null, null);
        this.bookingHistory.add(booking);
        return booking;
    }

    public void viewBookings() {
        if(this.bookingHistory.isEmpty()) {
            System.out.println("You have no bookings yet!");
            return;
        }

        System.out.println("\n--- Your Bookings ---");
        for (Booking booking : this.bookingHistory) {
            System.out.println(booking);
        }
    }

    public void cancelBooking(String bookingReference) {
        for(Booking booking : this.bookingHistory)
        {
            if(booking.getBookingReferenceNumber().equals(bookingReference)) {
                booking.cancel();
            }
        }
    }

    @Override
    public String toString() {
        return customerId + "," + address + "," + prefrence + "," + super.toString();
    }
}

