import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import shared.*;

public class Flight {
    private String flightNumber;
    private String airline;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private FlightSeat economyFilghtSeats;
    private FlightSeat businessFlightSeats;
    private FlightSeat firstClassFilghtSeats;
    List<Flight> flights = new ArrayList<>();
List<Booking> bookings = new ArrayList<>();


    public Flight(String airline, String origin, String destination, 
    LocalDateTime departureTime, LocalDateTime arrivalTime,
    int availableEconomySeats, int bookedEconomySeats, double economyPrice, 
    int availableBusinessSeats, int bookedBusinessSeats, double businessPrice,
    int availableFirstClassSeats,  int bookedFirstClassSeats,  double firstClassPrice) {
        this.flightNumber = this.generateFlightNumber();
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.economyFilghtSeats = new FlightSeat(SeatClass.ECONOMY, economyPrice, availableEconomySeats, bookedEconomySeats);
        this.businessFlightSeats = new FlightSeat(SeatClass.BUSINESS, businessPrice, availableBusinessSeats, bookedBusinessSeats);
        this.firstClassFilghtSeats = new FlightSeat(SeatClass.FIRST_CLASS, firstClassPrice, availableFirstClassSeats, bookedFirstClassSeats);
    }

    public Flight(String flightNumber, String airline, String origin, String destination, 
    LocalDateTime departureTime, LocalDateTime arrivalTime,
    int availableEconomySeats, int bookedEconomySeats, double economyPrice, 
    int availableBusinessSeats, int bookedBusinessSeats, double businessPrice,
    int availableFirstClassSeats,  int bookedFirstClassSeats,  double firstClassPrice) {
        this(airline, origin, destination, departureTime, arrivalTime,availableEconomySeats, bookedEconomySeats,
        economyPrice, availableBusinessSeats, bookedBusinessSeats, businessPrice, availableFirstClassSeats, bookedFirstClassSeats, firstClassPrice);
        this.flightNumber = flightNumber;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        this.departureTime = departureTime;
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public void setEconomyClassSeats(double price, int available, int booked)
    {
        this.economyFilghtSeats = new FlightSeat(SeatClass.ECONOMY, price, available, booked);
    }

    public void setBusinessClassSeats(double price, int available, int booked)
    {
        businessFlightSeats = new FlightSeat(SeatClass.BUSINESS, price, available, booked);
    }

    public void setFirstClassSeats(double price, int available, int booked)
    {
        this.firstClassFilghtSeats = new FlightSeat(SeatClass.FIRST_CLASS, price, available, booked);
    }

    public String getAirline() {
        return airline;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public String getDestination() {
        return destination;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getOrigin() {
        return origin;
    }

    public double getEconomyFilghtSeatsPrice() {
        return economyFilghtSeats.getPrice();
    }

    public double getbusinessFlightSeatsPrice() {
        return businessFlightSeats.getPrice();
    }

    public double getFirstClassFilghtSeatsPrice() {
        return firstClassFilghtSeats.getPrice();
    }

    public double calculatePrice(SeatClass seatClass, int quantity) {
        switch (seatClass) {
            case ECONOMY:
                return economyFilghtSeats.getPrice() * quantity;
            case BUSINESS:
                return businessFlightSeats.getPrice() * quantity;
            case FIRST_CLASS:
                return firstClassFilghtSeats.getPrice() * quantity;
            default:
                throw new IllegalArgumentException("This seat type is not supported");
        }
    }

    private String generateFlightNumber() {
        return "F" + UUID.randomUUID().toString().substring(0, 4).toUpperCase();
    }

    private boolean checkAvailability(SeatClass seatClass, int quantity) {
        switch (seatClass) {
            case ECONOMY:
                return economyFilghtSeats.getAvailable() >= quantity;
            case BUSINESS:
                return businessFlightSeats.getAvailable() >= quantity;
            case FIRST_CLASS:
                return firstClassFilghtSeats.getAvailable() >= quantity;
            default:
                return false;
        }
    }

   public Flight findFlightByNumber(String flightNumber) {
    for (Flight flight : flights) {
        if (flight.getFlightNumber().equalsIgnoreCase(flightNumber)) {
            return flight;
        }
    }
    return null;
}



    public void reserveSeat(SeatClass seatClass) {
        this.reserveSeats(seatClass,1);
    }

    public void reserveSeats(SeatClass seatClass, int quantity) {
        if (!checkAvailability(seatClass, quantity)) {
            throw new IllegalStateException("Not enough seats available");
        }

        switch (seatClass) {
            case ECONOMY:
                economyFilghtSeats.book(quantity);
                break;
            case BUSINESS:
                businessFlightSeats.book(quantity);
                break;
            case FIRST_CLASS:
                firstClassFilghtSeats.book(quantity);
                break;
            default:
                throw new IllegalArgumentException("This seat type is not supported");
        }
    }

    public void increaseAvailability(SeatClass seatClass, int quantity)
    {
        switch (seatClass) {
            case ECONOMY:
                economyFilghtSeats.cancel(quantity);
                break;
            case BUSINESS:
                businessFlightSeats.cancel(quantity);
                break;
            case FIRST_CLASS:
                firstClassFilghtSeats.cancel(quantity);
                break;
            default:
                throw new IllegalArgumentException("This seat type is not supported");
        }
    }

    public void updateSeat(SeatClass seatClass, double price, int available, int booked)
    {
        switch (seatClass) {
            case ECONOMY:
                economyFilghtSeats.updateSeat(price, available, booked);
                break;
            case BUSINESS:
                businessFlightSeats.updateSeat(price, available, booked);
                break;
            case FIRST_CLASS:
                firstClassFilghtSeats.updateSeat(price, available, booked);
                break;
            default:
                throw new IllegalArgumentException("This seat type is not supported");
        }
    }

    @Override
    public String toString() {
        return flightNumber + "," +
                airline + "," +
                origin + "," +
                destination + "," +
                departureTime + "," +
                arrivalTime + "," +
                economyFilghtSeats.getPrice() + "," +
                economyFilghtSeats.getAvailable() + "," +
                economyFilghtSeats.getBooked() + "," +
                businessFlightSeats.getPrice() + "," +
                businessFlightSeats.getAvailable() + "," +
                businessFlightSeats.getBooked() + "," +
                firstClassFilghtSeats.getPrice()  + "," +
                firstClassFilghtSeats.getAvailable() + "," +
                firstClassFilghtSeats.getBooked();
    }
}

