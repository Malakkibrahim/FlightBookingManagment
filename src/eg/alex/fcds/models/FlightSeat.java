package eg.alex.fcds.models;
import eg.alex.fcds.models.shared.*;

public class FlightSeat {
  private SeatClass seatClass;
  private double price;
  private int available;
  private int booked;

  public FlightSeat(SeatClass seatClass, double price, int available, int booked)
  {
    this.seatClass = seatClass;
    this.price = price;
    this.available = available;
    this.booked =  booked; 
  }

  public SeatClass getSeatClass() {
    return seatClass;
  }
  public void setBasePrice(double price){
    this.price = price;
  }

  public double getPrice() {
    return price;
  }

  public int getAvailable() {
    return available;
  }

  public int getBooked() {
    return booked;
  }

  public void book(int quantity)
  {
    this.available -= quantity;
    this.booked += quantity;
  } 

  public void cancel(int quantity) 
  {
    this.available += quantity;
    this.booked -= quantity;
  }

  public void updateSeat(double price, int available, int booked)
  {
    this.price = price;
    this.available = available;
    this.booked = booked;
  }

  @Override
  public String toString() {
    return seatClass + "," + price + "," + available + "," + booked;
  }
}
