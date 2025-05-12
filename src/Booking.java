import java.util.*;

import shared.BookingStatus;
import shared.PaymentStatus;
import shared.SeatClass;

import java.time.LocalDateTime;

public class Booking {
    private String bookingReferenceNumber;
    private BookingStatus status;
    private PaymentStatus paymentStatus;
    private UUID customerId;
    private String flightNumber;
    private Customer customer;
    private Flight flight;
    private List<Passenger> passengers;
    private List<SeatSelection> seatSelections;

    public Booking(String bookingReference, BookingStatus status, PaymentStatus paymentStatus, UUID customerId, String flightNumber,
            Customer customer, Flight flight, List<Passenger> passengers, List<SeatSelection> seatSelections) {
        this(customerId, flightNumber, customer, flight, passengers, seatSelections);
        this.bookingReferenceNumber = bookingReference;
        this.status = status;
        this.paymentStatus = paymentStatus;
    }

    public Booking(UUID customerId, String flightNumber, Customer customer, Flight flight, List<Passenger> passengers, List<SeatSelection> seatSelections) {
        this.bookingReferenceNumber = generateBookingReference();
        this.status = BookingStatus.RESERVED;
        this.paymentStatus = PaymentStatus.SUSPENDED;
        this.customerId = customerId;
        this.flightNumber = flightNumber;
        this.customer = customer;
        this.flight = flight;
        this.passengers = passengers == null ? new ArrayList<>() : passengers;
        this.seatSelections = seatSelections == null ? new ArrayList<>() : seatSelections;
    }

    public String getBookingReferenceNumber() {
        return bookingReferenceNumber;
    }

    public UUID getCustomerId() {
        return this.customerId;
    }

    public String getFlightNumber()
    {
        return flightNumber;
    }

    public List<Passenger> getPassengers() {
        return passengers;
    }

    public List<SeatSelection> getSeatSelections() {
        return seatSelections;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public PaymentStatus getPaymentStatus() {
        return paymentStatus;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public void setPassengers(List<Passenger> passengers) {
        this.passengers = passengers;
    }

    public void setStatus(BookingStatus status) {
        this.status = status;
    }

    public void setPaymentStatus(PaymentStatus paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    private String generateBookingReference() {
        return "BK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public double calculateTotalPrice() {
        double total = 0.0;

        for (SeatSelection selection : seatSelections) {
            switch (selection.getSeatClass()) {
                case ECONOMY:
                    total += flight.getEconomyFilghtSeatsPrice();
                    break;
                case BUSINESS:
                    total += flight.getEconomyFilghtSeatsPrice();
                    break;
                case FIRST_CLASS:
                    total += flight.getFirstClassFilghtSeatsPrice();
                    break;
            }
        }
        return total;
    }

    public void addPassenger(Passenger passenger) {
        this.passengers.add(passenger);
    }

    public void addSeatSelection(SeatSelection seat) {
        this.seatSelections.add(seat);
    }

    public void createPassenger(String name, String passportNumber, LocalDateTime dateOfbirth, String specialRequests, SeatClass seatClass) {
        Passenger passenger = new Passenger(name, this.bookingReferenceNumber, passportNumber, dateOfbirth, specialRequests);
        this.passengers.add(passenger);
        this.seatSelections.add(new SeatSelection(this.bookingReferenceNumber, passenger.getPassengerId(), seatClass));
    }

    public void confirmBooking() {
        this.paymentStatus = PaymentStatus.SUCCESS;
        this.status = BookingStatus.CONFIRMED;
    }

    public void cancel() {
        this.status = BookingStatus.CANCELLED;
        this.paymentStatus = PaymentStatus.REFUNDED;
        
        int economySeatsCount = 0, businessSeatsCount = 0, firstClassSeatsCount = 0;
        for(SeatSelection selection : this.seatSelections) {
            SeatClass seatClass = selection.getSeatClass();
            if(seatClass == SeatClass.ECONOMY)
                economySeatsCount += 1;
            else if(seatClass == SeatClass.BUSINESS)
                businessSeatsCount += 1;
            else if(seatClass == SeatClass.FIRST_CLASS)
                firstClassSeatsCount += 1;
        }
        this.flight.increaseAvailability(SeatClass.ECONOMY, economySeatsCount);
        this.flight.increaseAvailability(SeatClass.BUSINESS, businessSeatsCount);
        this.flight.increaseAvailability(SeatClass.FIRST_CLASS, firstClassSeatsCount);
    }

    public String generateItinerary()
    {
        String itinerary = "Booking Reference:"+bookingReferenceNumber+"\n"+"Customer:"+customer.getName()+"/n"+
                "Flight:"+flight.getFlightNumber() + flight.getOrigin() + "to" +flight.getDestination() + "/n" +
                "Arrival:"+flight.getArrivalTime()+"/n"+"Passengers:/n";
        for(Passenger passenger : passengers){
            itinerary += "" + passenger.getName() + " (Passport: " + passenger.getPassportNumber() + ")\n";
        }
        itinerary += "Status:" + status + "/n"+"Payment" + paymentStatus;
        return itinerary;
    }

    @Override
    public String toString() {
        return bookingReferenceNumber + "," + status + "," + paymentStatus + "," + customerId + "," + flightNumber;
    }
}

