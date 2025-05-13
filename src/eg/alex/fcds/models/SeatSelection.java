package eg.alex.fcds.models;
import java.util.UUID;

import eg.alex.fcds.models.shared.*;

public class SeatSelection {
  private String bookingReferanceNumber;
  private UUID passengerId;
  private SeatClass seatClass;

  public SeatSelection(String bookingReferanceNumber, UUID passengerId, SeatClass seatClass) {
    this.bookingReferanceNumber = bookingReferanceNumber;
    this.passengerId = passengerId;
    this.seatClass = seatClass;
  }

  public String getBookingReferanceNumber() {
    return bookingReferanceNumber;
  }

  public UUID getPassengerId() {
    return passengerId;
  }

  public SeatClass getSeatClass() {
    return seatClass;
  }

  public void updateSelection(SeatClass seatClass)
  {
    this.seatClass = seatClass;
  }

  @Override
  public String toString()
  {
    return bookingReferanceNumber + "," + passengerId + "," + seatClass;
  }
}