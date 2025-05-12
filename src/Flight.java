import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

    public Flight(String flightNumber, String airline, String origin, String destination, 
    LocalDateTime departureTime, LocalDateTime arrivalTime) {
        this.flightNumber = flightNumber;
        this.airline = airline;
        this.origin = origin;
        this.destination = destination;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
    }

    public Flight(String flightNumber, String airline, String origin, String destination, 
    LocalDateTime departureTime, LocalDateTime arrivalTime,
    int availableEconomySeats, int bookedEconomySeats, double economyPrice, 
    int availableBusinessSeats, int bookedBusinessSeats, double businessPrice,
    int availableFirstClassSeats,  int bookedFirstClassSeats,  double firstClassPrice) {
        this(flightNumber, airline, origin, destination, departureTime, arrivalTime);
        this.economyFilghtSeats = new FlightSeat(SeatClass.Economy, economyPrice, availableEconomySeats, bookedEconomySeats);
        businessFlightSeats = new FlightSeat(SeatClass.Business, businessPrice, availableBusinessSeats, bookedBusinessSeats);
        this.firstClassFilghtSeats = new FlightSeat(SeatClass.FirstClass, firstClassPrice, availableFirstClassSeats, bookedFirstClassSeats);
    }

    public void setFlightNumber(String flightNumber) {
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
        this.economyFilghtSeats = new FlightSeat(SeatClass.Economy, price, available, booked);
    }

    public void setBusinessClassSeats(double price, int available, int booked)
    {
        businessFlightSeats = new FlightSeat(SeatClass.Business, price, available, booked);
    }

    public void setFirstClassSeats(double price, int available, int booked)
    {
        this.firstClassFilghtSeats = new FlightSeat(SeatClass.FirstClass, price, available, booked);
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

    public FlightSeat getEconomyFilghtSeats() {
        return economyFilghtSeats;
    }

    public FlightSeat getbusinessFlightSeats() {
        return businessFlightSeats;
    }

    public FlightSeat getFirstClassFilghtSeats() {
        return firstClassFilghtSeats;
    }

    public double calculatePrice(SeatClass seatClass, int quantity) {
        switch (seatClass) {
            case Economy:
                return economyFilghtSeats.getPrice() * quantity;
            case Business:
                return businessFlightSeats.getPrice() * quantity;
            case FirstClass:
                return firstClassFilghtSeats.getPrice() * quantity;
            default:
                throw new IllegalArgumentException("This seat type is not supported");
        }
    }

    // public static Flight FromFileString(String line) {
    //     String[] parts = line.split(",");
    //     if (parts.length == 16) {
    //         Flight flight = new Flight();
    //         flight.setflightNumber(parts[0]);
    //         flight.setSeatClass(parts[1]);
    //         flight.setairline(parts[2]);
    //         flight.setorigin(parts[3]);
    //         flight.setdestination(parts[4]);
    //         flight.setdepartureTime(LocalDateTime.parse(parts[5]));
    //         flight.setarrivalTime(LocalDateTime.parse(parts[6]));
    //         flight.setAvailableEconomySeats(Integer.parseInt(parts[7]));
    //         flight.setAvailableBusinessSeats(Integer.parseInt(parts[8]));
    //         flight.setAvailableFirstClassSeats(Integer.parseInt(parts[9]));
    //         flight.setBookedEconomySeats(Integer.parseInt(parts[10]));
    //         flight.setBookedBusinessSeats(Integer.parseInt(parts[11]));
    //         flight.setBookedFirstClassSeats(Integer.parseInt(parts[12]));
    //         flight.setEconomyPrice(Double.parseDouble(parts[13]));
    //         flight.setBusinessPrice(Double.parseDouble(parts[14]));
    //         flight.setFirstClassPrice(Double.parseDouble(parts[15]));
    //         return flight;
    //     } else {
    //         System.err.println("Error: Expected 16 parts in line but found " + parts.length + ": " + line);
    //         return null;
    //     }
    // }

    public boolean checkAvailability(SeatClass seatClass, int quantity) {
        switch (seatClass) {
            case Economy:
                return economyFilghtSeats.getAvailable() >= quantity;
            case Business:
                return businessFlightSeats.getAvailable() >= quantity;
            case FirstClass:
                return firstClassFilghtSeats.getAvailable() >= quantity;
            default:
                throw new IllegalArgumentException("This seat type is not supported");
        }
    }

    public void reserveSeat(SeatClass seatClass) {
        this.reserveSeats(seatClass,1);
    }

    public void reserveSeats(SeatClass seatClass, int quantity) {
        if (!checkAvailability(seatClass, quantity)) {
            throw new IllegalStateException("Not enough seats available");
        }

        switch (seatClass) {
            case Economy:
                economyFilghtSeats.book(quantity);
                break;
            case Business:
                businessFlightSeats.book(quantity);
                break;
            case FirstClass:
                firstClassFilghtSeats.book(quantity);
                break;
            default:
                throw new IllegalArgumentException("This seat type is not supported");
        }
    }

    public void updateSeat(SeatClass seatClass, double price, int available, int booked)
    {
        switch (seatClass) {
            case Economy:
                economyFilghtSeats.updateSeat(price, available, booked);
                break;
            case Business:
                businessFlightSeats.updateSeat(price, available, booked);
                break;
            case FirstClass:
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

