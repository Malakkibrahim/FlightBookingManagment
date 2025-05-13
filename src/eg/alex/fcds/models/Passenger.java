package eg.alex.fcds.models;
import java.time.LocalDateTime;
import java.util.*;

public class Passenger {
    private UUID passengerId;
    private String name;
    private String passportNumber;
    private LocalDateTime dateOfbirth;
    private String specialRequests;
    private String bookingReferenceNumber;

    public Passenger(UUID passengerId, String bookingReferenceNumber, String name, String passportNumber, LocalDateTime dateOfbirth, String specialRequests) {
        this(bookingReferenceNumber, name, passportNumber, dateOfbirth, specialRequests);
        this.passengerId = passengerId;
    }

    public Passenger(String bookingReferenceNumber, String name, String passportNumber, LocalDateTime dateOfbirth, String specialRequests) {
        this.passengerId = UUID.randomUUID();
        this.bookingReferenceNumber = bookingReferenceNumber;
        this.name = name;
        this.passportNumber = passportNumber;
        this.dateOfbirth = dateOfbirth;
        this.specialRequests = specialRequests;
    }

    public UUID getPassengerId() {
        return passengerId;
    }

    public String getName() {
        return name;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public LocalDateTime getDateOfbirth() {
        return dateOfbirth;
    }

    public String getSpecialRequests() {
        return specialRequests;
    }

    public String getBookingReferenceNumber() {
        return bookingReferenceNumber;
    }

    @Override
    public String toString() {
        return passengerId + "," + bookingReferenceNumber + "," + name + "," + passportNumber + "," + dateOfbirth + "," + specialRequests;
    }
}